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
import model.service.GameService;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BridgeMonopolyGame {

    private final GameService service;

    private @Nullable MapReader mapDecoder;

    protected Map map;

    private int numOfPlayers;

    protected Turn turn;

    private int endPlayers = 0;

    private static final ArrayList<Direction> bridgeMoveRightDirs = new ArrayList<>(Arrays.asList(Direction.RIGHT, Direction.RIGHT));

    private static final ArrayList<Direction> bridgeMoveLeftDirs = new ArrayList<>(Arrays.asList(Direction.LEFT, Direction.LEFT));

    public BridgeMonopolyGame(GameService service) {
        this.service = service;
    }


    public void run() {

        try {

            while (mapDecoder == null) {
                try {
                    if (service.selectUseDefaultMap().call())
                        mapDecoder = new MapReader(service.enterMapFile().call());
                    else
                        mapDecoder = new MapReader();

                    map = mapDecoder.getMap();
                } catch (IOException e) {
                    service.displayNotFoundMap();
                } catch (InvalidInputException e) {
                    e.print();
                } catch (BridgeNotFoundException e) {
                    e.print();
                }
            }

            // create map by absolute position

            // initialize players
            Player.clear();
            numOfPlayers = service.enterNumberOfPlayers().call();
            ArrayList<Player> playerList = new ArrayList<>();
            for (int i = 0; i < numOfPlayers; i++) {
                playerList.add(Player.newInstance(map.getStartCell()));
            }

            // initialize turn
            turn = new Turn(playerList);

            service.initDisplay(map, turn);

            // run turn
            Player owner;
            while (numOfPlayers - endPlayers > 1) {
                owner = turn.getTurnOwner();
                service.refresh(map, turn);

                boolean stay = service.selectStay(owner.getId()).call();

                // stay turn
                if (stay) {
                    owner.addPenalty(-1);
                }
                // move turn
                else {
                    // roll dice
                    int diceResult = service.rollDice().call();
                    // combine direction
                    ArrayList<Direction> dirs;

                    int deduct = 0;

                    while (true) {

                        if (diceResult - owner.getPenalty() - deduct < 1) {
                            service.displayMoveValueZero(diceResult, owner.getPenalty(), deduct);
                            break;
                        }

                        Cell curCell = owner.getPiecePosition();
                        MoveResult moveResult = null;

                        // select bridge move
                        if (curCell instanceof BridgeCell && (diceResult - owner.getPenalty() - deduct) >= 2) {
                            if (curCell.isMovableDir(Direction.RIGHT, MoveType.BRIDGE)) {
                                if (service.selectMoveBridge(diceResult, owner.getPenalty(), deduct).call()) {
                                    moveResult = owner.move(bridgeMoveRightDirs, MoveType.BRIDGE);
                                    deduct += 2;
                                    service.refresh(map, turn);
                                }
                            } else if (curCell.isMovableDir(Direction.LEFT, MoveType.BRIDGE) && turn.getAllowMoveBack()) {
                                if (service.selectMoveBridge(diceResult, owner.getPenalty(), deduct).call()) {
                                    moveResult = owner.move(bridgeMoveLeftDirs, MoveType.BRIDGE);
                                    deduct += 2;
                                    service.refresh(map, turn);
                                }
                            }

                        }

                        // not bridge moved
                        if (moveResult == null) {
                            dirs = service.enterDirection(diceResult, owner.getPenalty(), deduct).call();
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
                                    service.displayCanNotMoveBack();
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
                            service.alertInvalidMove();

                    }

                }
                turn.proceedTurn();
            }
            service.refresh(map, turn);
            service.displayWinner(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
