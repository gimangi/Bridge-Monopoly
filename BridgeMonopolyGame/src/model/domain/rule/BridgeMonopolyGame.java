package model.domain.rule;

import model.data.Direction;
import model.data.MoveResult;
import model.data.MoveType;
import model.domain.map.Board;
import model.domain.map.MapDecoder;
import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public abstract class BridgeMonopolyGame {

    private @Nullable MapDecoder mapDecoder;

    private Board board;

    private int numOfPlayers;

    private Turn turn;


    /*
        Decide whether to use the default map.
     */
    protected abstract boolean selectUseDefaultMap();
    /*
        Select map file name.
     */
    protected abstract String enterMapFile();

    /*
        It outputs that the input file does not exist and proceeds to the default map.
     */
    protected abstract void displayNotFoundMap();

    /*
        Input the number of players to play the game.
     */
    protected abstract int enterNumberOfPlayers();

    /*
        The player chooses whether to rest or roll the dice for that turn.
     */
    protected abstract boolean selectStay(int playerId);

    /*
        The dice are rolled based on the player's input.
     */
    protected abstract int rollDice();

    /*
        Displays the result of the dice and receives the input direction to move.
     */
    protected abstract @NotNull ArrayList<Direction> enterDirection(int diceResult, int penalty);

    /*
        Alert that the direction cannot be moved.
     */
    protected abstract void alertInvalidMove();

    /*
        Show turn changes
     */
    protected abstract void displayResult();

    /*
        Display the winner
     */
    protected abstract void displayWinner();

    public void run() {

        while (mapDecoder == null) {
            try {
                if (selectUseDefaultMap())
                    mapDecoder = new MapDecoder(enterMapFile());
                else
                    mapDecoder = new MapDecoder();

            } catch (IOException e) {
                displayNotFoundMap();
            }
        }

        // create map by absolute position
        board.createAbsoluteMap();

        // initialize players
        Player.clear();
        numOfPlayers = enterNumberOfPlayers();
        ArrayList<Player> playerList = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            playerList.add(Player.newInstance(board.getStartCell()));
        }

        // initialize turn
        turn = new Turn(playerList);

        // run turn
        Player owner;
        while ((owner = turn.getTurnOwner()) != null) {
            boolean stay = selectStay(owner.getId());

            // stay turn
            if (stay) {
                owner.addPenalty(-1);
            }
            // move turn
            else {
                // roll dice
                int diceResult = rollDice();
                // combine direction
                ArrayList<Direction> dirs;

                while (true) {
                    dirs = enterDirection(diceResult, owner.getPenalty());
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
            displayResult();
            turn.proceedTurn();
        }
        displayWinner();
    }

}
