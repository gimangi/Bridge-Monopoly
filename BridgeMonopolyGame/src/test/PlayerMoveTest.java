package test;

import model.domain.map.Map;
import model.domain.map.MapReader;
import model.exception.BridgeNotFoundException;
import model.exception.InvalidInputException;

import java.io.IOException;

public class PlayerMoveTest {
    public static void main(String args[]) {
        try {
            // use default map
            MapReader reader = new MapReader();
            Map map = reader.getMap();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BridgeNotFoundException e) {

        } catch (InvalidInputException e) {

        }
    }
}
