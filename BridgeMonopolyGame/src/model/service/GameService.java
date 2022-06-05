package model.service;

import model.data.Direction;
import model.domain.map.Map;
import model.domain.rule.Turn;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public interface GameService {

    /*
        Decide whether to use the default map.
     */
    Callable<Boolean> selectUseDefaultMap();
    /*
        Select map file name.
     */
    Callable<String> enterMapFile();

    /*
        It outputs that the input file does not exist and proceeds to the default map.
     */
    void displayNotFoundMap();

    /*
        Input the number of players to play the game.
     */
    Callable<Integer> enterNumberOfPlayers();


    /*
        Initializes the elements of the screen.
     */
    void initDisplay(Map map, Turn turn);

    void refresh(Map map, Turn turn);

    /*
        The player chooses whether to rest or roll the dice for that turn.
     */
    Callable<Boolean> selectStay(int playerId);

    /*
        The dice are rolled based on the player's input.
     */
    Callable<Integer> rollDice();

    /*
        Display that there are no movable scales
     */
    void displayMoveValueZero(int diceResult, int penalty, int deduct);

    /*
        Displays the result of the dice and receives the input direction to move.
     */
    Callable<ArrayList<Direction>> enterDirection(int diceResult, int penalty, int deduct);

    /*
        If player are on a bridge cell, player can choose whether to move to the bridge or not.
     */
    Callable<Boolean> selectMoveBridge(int diceResult, int penalty, int deduct);


    /*
        Alert that the direction cannot be moved.
     */
    void alertInvalidMove();

    /*
        Display that you cannot move backwards
     */
    void displayCanNotMoveBack();


    /*
        Display the winner
     */
    void displayWinner(Turn turn);

}
