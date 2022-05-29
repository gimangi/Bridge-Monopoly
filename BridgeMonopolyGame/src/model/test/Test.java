package model.test;

import model.data.Direction;
import model.domain.cell.Cell;
import model.domain.map.Board;
import model.domain.map.MapDecoder;
import model.domain.player.Player;

import java.util.ArrayList;
import java.util.List;

import static model.data.Direction.*;

public class Test {
    public static void main(String args[]) {
        try {
            MapDecoder decoder = new MapDecoder();
            Board board = decoder.getBoard();
            Cell startCell = board.getStartCell();

            testMapMove(startCell);

            board.createAbsoluteMap();
            board.printAbsoluteMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testMapMove(Cell startCell) {
        ArrayList<Direction> dirs = new ArrayList<>();
        dirs.addAll(List.of(new Direction[]{RIGHT, RIGHT, DOWN, RIGHT, RIGHT}));

        Player player = Player.newInstance(startCell);

        System.out.println(player.move(dirs));
        System.out.println(player.move(dirs));
    }
}
