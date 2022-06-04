package test;

import model.data.Direction;
import model.data.MoveType;
import model.data.RelativePosition;
import model.domain.map.Map;
import model.domain.map.MapReader;
import model.domain.player.Player;

import java.util.List;

import static model.data.Direction.*;

public class PlayerMoveTest {

    private static final Direction[][] moveDirs = {
            {RIGHT, RIGHT, DOWN, DOWN},
            {UP, UP, DOWN},
            {DOWN, DOWN, DOWN, DOWN, DOWN},
            {UP, DOWN, RIGHT, RIGHT}
    };

    private static final RelativePosition[] correctPos = {
        createPos(2, -2),
        createPos(2, -1),
        createPos(2, -6),
        createPos(4, -6)
    };

    private static final Direction[] gotoBridge = {
            RIGHT, RIGHT, DOWN
    };

    public static void main(String args[]) {

        try {
            // use default map
            MapReader reader = new MapReader();
            Map map = reader.getMap();

            Player movePlayer = Player.newInstance(map.getStartCell());
            Player bridgePlayer = Player.newInstance(map.getStartCell());

            int cnt = 0;
            for (int i = 0; i < moveDirs.length; i++) {
                movePlayer.move(List.of(moveDirs[i]), MoveType.ADJACENT);
                if (movePlayer.getPiecePosition().getPosition().equals(correctPos[i]))
                    System.out.println("Player move test pass " + ++cnt + " / " + moveDirs.length);
                else {
                    System.out.println("Fail move test in case " + (i+1));
                }
            }

            bridgePlayer.move(List.of(gotoBridge), MoveType.ADJACENT);

            Direction[] crossBridge = {
                    RIGHT, RIGHT
            };
            bridgePlayer.move(List.of(crossBridge), MoveType.BRIDGE);

            if (bridgePlayer.getPiecePosition().getPosition().equals(
                    createPos(4, -1))
            )
                System.out.println("Bridge move test pass");
            else
                System.out.println("Fail bridge move test");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static RelativePosition createPos(int x, int y) {
        return new RelativePosition(x, y);
    }
}
