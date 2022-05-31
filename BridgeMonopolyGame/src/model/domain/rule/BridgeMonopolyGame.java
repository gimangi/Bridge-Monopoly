package model.domain.rule;

import model.data.Direction;
import model.data.MoveResult;
import model.data.MoveType;
import model.domain.map.Board;
import model.domain.map.MapDecoder;
import model.domain.player.Player;
import model.exception.InvalidInputException;
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
                refresh();
                turn.proceedTurn();
            }
            displayWinner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
