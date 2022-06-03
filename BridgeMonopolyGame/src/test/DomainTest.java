package test;

import model.data.Direction;
import model.domain.cell.Cell;
import model.domain.map.Map;
import model.domain.map.MapDecoder;
import model.domain.player.Player;

import java.util.ArrayList;
import java.util.List;

import static model.data.Direction.*;
import static model.data.MoveType.ADJACENT;
import static model.data.MoveType.FORWARD;

public class DomainTest {
    public static void main(String args[]) {
        try {
            MapDecoder decoder = new MapDecoder();
            Map map = decoder.getBoard();
            Cell startCell = map.getStartCell();

            testMapMove(startCell);

            map.createAbsoluteMap();
            map.printAbsoluteMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testMapMove(Cell startCell) {
        ArrayList<Direction> dirs = new ArrayList<>();
        dirs.addAll(List.of(new Direction[]{RIGHT, RIGHT, DOWN, RIGHT, RIGHT}));

        Player player = Player.newInstance(startCell);

        System.out.println(player.move(dirs, ADJACENT));
        System.out.println(player.move(dirs, FORWARD));
    }
}
