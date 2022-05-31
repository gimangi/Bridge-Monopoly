package model.domain.rule;

import model.data.Direction;
import model.data.MoveResult;
import model.data.MoveType;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import model.domain.map.Board;
import model.domain.map.MapDecoder;
import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public abstract class BridgeMonopolyGame {

    private @Nullable MapDecoder mapDecoder;

    protected Board board;

    private int numOfPlayers;

    protected Turn turn;

    private int endPlayers = 0;


    /*
        Decide whether to use the default map.
     */
    protected abstract Callable<Boolean> selectUseDefaultMap();
    /*
        Select map file name.
     */
    protected abstract Callable<String> enterMapFile();

    /*
        It outputs that the input file does not exist and proceeds to the default map.
     */
    protected abstract void displayNotFoundMap();

    /*
        Input the number of players to play the game.
     */
    protected abstract Callable<Integer> enterNumberOfPlayers();


    /*
        Initializes the elements of the screen.
     */
    protected abstract void initDisplay();

    protected abstract void refresh();

    /*
        The player chooses whether to rest or roll the dice for that turn.
     */
    protected abstract Callable<Boolean> selectStay(int playerId);

    /*
        The dice are rolled based on the player's input.
     */
    protected abstract Callable<Integer> rollDice();

    /*
        Displays the result of the dice and receives the input direction to move.
     */
    protected abstract @NotNull Callable<ArrayList<Direction>> enterDirection(int diceResult, int penalty);

    /*
        Alert that the direction cannot be moved.
     */
    protected abstract void alertInvalidMove();

    /*
        Display the winner
     */
    protected abstract void displayWinner();

    public void run() {

        try {

            while (mapDecoder == null) {
                try {
                    if (selectUseDefaultMap().call())
                        mapDecoder = new MapDecoder(enterMapFile().call());
                    else
                        mapDecoder = new MapDecoder();

                    board = mapDecoder.getBoard();
                } catch (IOException e) {
                    displayNotFoundMap();
                }
            }

            // create map by absolute position
            board.createAbsoluteMap();

            // initialize players
            Player.clear();
            numOfPlayers = enterNumberOfPlayers().call();
            ArrayList<Player> playerList = new ArrayList<>();
            for (int i = 0; i < numOfPlayers; i++) {
                playerList.add(Player.newInstance(board.getStartCell()));
            }

            // initialize turn
            turn = new Turn(playerList);

            initDisplay();

            // run turn
            Player owner;
            while ((owner = turn.getTurnOwner()) != null) {
                refresh();

                boolean stay = selectStay(owner.getId()).call();

                // stay turn
                if (stay) {
                    owner.addPenalty(-1);
                }
                // move turn
                else {
                    // roll dice
                    int diceResult = rollDice().call();
                    // combine direction
                    ArrayList<Direction> dirs;

                    while (true) {
                        dirs = enterDirection(diceResult, owner.getPenalty()).call();
                        MoveType moveType = MoveType.ADJACENT;

                        // not allow move back
                        if (!turn.getAllowMoveBack())
                            moveType = MoveType.FORWARD;

                        MoveResult moveResult = owner.move(dirs, moveType);
                        // move successfully
                        if (moveResult != MoveResult.FAIL) {

                            // get point
                            Cell curCell = owner.getPiecePosition();
                            if (curCell instanceof ItemCell) {
                                owner.addPoint(((ItemCell) curCell).getPoint());
                            }

                            // player end
                            if (owner.isEnd()) {
                                if (endPlayers == 0)
                                    owner.addPoint(7);
                                else if (endPlayers == 1)
                                    owner.addPoint(3);
                                else if (endPlayers == 2)
                                    owner.addPoint(1);
                                endPlayers++;
                            }

                            // get penalty
                            if (moveResult == MoveResult.SUCCESS_BRIDGED)
                                owner.addPenalty(1);
                            break;
                        }
                        // can not move
                        else
                            alertInvalidMove();
                    }

                }
                turn.proceedTurn();
            }
            displayWinner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract static class WaitCall<T> implements Callable<T> {
        protected T result;

        public void signalResult(T result) {
            synchronized (this) {
                this.result = result;
                notify();
            }
        }

        public void waitResult() {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
