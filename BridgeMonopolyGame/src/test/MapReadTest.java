package test;

import model.domain.map.Map;
import model.domain.map.MapReader;

public class MapReadTest {

    private static final String[] TEST_MAPS = { "default.map", "another.map", "maze.map", "snake.map" };

    public static void main(String args[]) {

        int cnt = 0;
        for (String name : TEST_MAPS) {
            try {
                MapReader reader = new MapReader(name);
                Map map = reader.getMap();

                map.createAbsoluteMap();

                System.out.println("Map test pass " + ++cnt + " / " + TEST_MAPS.length);
            } catch (Exception e) {
                System.out.println("Map test failed at " + name);
                e.printStackTrace();
            }
        }

        if (cnt == TEST_MAPS.length)
            System.out.println("All tests passed successfully.");

    }

}
