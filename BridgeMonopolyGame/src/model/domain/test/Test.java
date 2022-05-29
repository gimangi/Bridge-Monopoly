package model.domain.test;

import model.data.Direction;
import model.domain.cell.Cell;
import model.domain.map.Board;
import model.domain.map.MapDecoder;

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
        System.out.println(
                startCell.getAdjacentCell(Direction.RIGHT)
                        .getAdjacentCell(Direction.RIGHT)
                        .getAdjacentCell(Direction.DOWN)
                        .getAdjacentCell(Direction.RIGHT)
                        .getPosition()
        );
    }
}
