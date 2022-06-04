package model.domain.rule;

import model.data.Direction;
import model.data.MoveResult;
import model.data.MoveType;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import model.domain.map.Map;
import model.domain.map.MapReader;
import model.domain.player.Player;
import model.exception.BridgeNotFoundException;
import model.exception.InvalidInputException;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

public abstract class BridgeMonopolyGame {

    private @Nullable MapReader mapDecoder;

    protected Map map;

    private int numOfPlayers;

    protected Turn turn;

    private int endPlayers = 0;

    private static final ArrayList<Direction> bridgeMoveRightDirs = new ArrayList<>(Arrays.asList(Direction.RIGHT, Direction.RIGHT));

    private static final ArrayList<Direction> bridgeMoveLeftDirs = new ArrayList<>(Arrays.asList(Direction.LEFT, Direction.LEFT));

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
        Display that there are no movable scales
     */
    protected abstract void displayMoveValueZero(int diceResult, int penalty, int deduct);

    /*
        Displays the result of the dice and receives the input direction to move.
     */
    protected abstract Callable<ArrayList<Direction>> enterDirection(int diceResult, int penalty, int deduct);

    /*
        If player are on a bridge cell, player can choose whether to move to the bridge or not.
     */
    protected abstract Callable<Boolean> selectMoveBridge(int diceResult, int penalty, int deduct);


    /*
        Alert that the direction cannot be moved.
     */
    protected abstract void alertInvalidMove();

    /*
        Display that you cannot move backwards
     */
    protected abstract void displayCanNotMoveBack();


    /*
        Display the winner
     */
    protected abstract void displayWinner();

    public void run() {

        try {

            while (mapDecoder == null) {
                try {
                    if (selectUseDefaultMap().call())
                        mapDecoder = new MapReader(enterMapFile().call());
                    else
                        mapDecoder = new MapReader();

                    map = mapDecoder.getMap();
                } catch (IOException e) {
                    displayNotFoundMap();
                } catch (InvalidInputException e) {
                    System.out.println(e);
                } catch (BridgeNotFoundException e) {
                    System.out.println(e);
                }
            }

            // create map by absolute position

            // initialize players
            Player.clear();
            numOfPlayers = enterNumberOfPlayers().call();
            ArrayList<Player> playerList = new ArrayList<>();
            for (int i = 0; i < numOfPlayers; i++) {
                playerList.add(Player.newInstance(map.getStartCell()));
            }

            // initialize turn
            turn = new Turn(playerList);

            initDisplay();

            // run turn
            Player owner;
            while (numOfPlayers - endPlayers > 1) {
                owner = turn.getTurnOwner();
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

                    int deduct = 0;

                    while (true) {

                        if (diceResult - owner.getPenalty() - deduct < 1) {
                            displayMoveValueZero(diceResult, owner.getPenalty(), deduct);
                            break;
                        }

                        Cell curCell = owner.getPiecePosition();
                        MoveResult moveResult = null;

                        // select bridge move
                        if (curCell instanceof BridgeCell && (diceResult - owner.getPenalty() - deduct) >= 2) {
                            if (curCell.isMovableDir(Direction.RIGHT, MoveType.BRIDGE)) {
                                if (selectMoveBridge(diceResult, owner.getPenalty(), deduct).call()) {
                                    moveResult = owner.move(bridgeMoveRightDirs, MoveType.BRIDGE);
                                    deduct += 2;
                                    refresh();
                                }
                            } else if (curCell.isMovableDir(Direction.LEFT, MoveType.BRIDGE) && turn.getAllowMoveBack()) {
                                if (selectMoveBridge(diceResult, owner.getPenalty(), deduct).call()) {
                                    moveResult = owner.move(bridgeMoveLeftDirs, MoveType.BRIDGE);
                                    deduct += 2;
                                    refresh();
                                }
                            }

                        }

                        // not bridge moved
                        if (moveResult == null) {
                            dirs = enterDirection(diceResult, owner.getPenalty(), deduct).call();
                            MoveType moveType = MoveType.ADJACENT;

                            // not allow move back
                            if (!turn.getAllowMoveBack())
                                moveType = MoveType.FORWARD;

                            moveResult = owner.move(dirs, moveType);
                        }

                        // move successfully
                        if (moveResult != MoveResult.FAIL) {
                            curCell = owner.getPiecePosition();

                            // get point
                            if (curCell instanceof ItemCell) {
                                owner.addPoint(((ItemCell) curCell).getPoint());
                            }

                            // player end
                            if (owner.isEnd()) {
                                if (endPlayers == 0) {
                                    displayCanNotMoveBack();
                                    owner.addPoint(7);
                                }
                                else if (endPlayers == 1)
                                    owner.addPoint(3);
                                else if (endPlayers == 2)
                                    owner.addPoint(1);
                                endPlayers++;
                            }

                            // get penalty
                            if (moveResult == MoveResult.SUCCESS_BRIDGED)
                                owner.addPenalty(1);
                            else
                                break;
                        }
                        // can not move
                        else
                            alertInvalidMove();

                    }

                }
                turn.proceedTurn();
            }
            refresh();
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
