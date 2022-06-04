package model.domain.map;

import model.data.Direction;
import model.data.RelativePosition;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import model.exception.BridgeNotFoundException;
import model.exception.InvalidInputException;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MapReader {

    private final static String DIR_MAPS = "maps";

    private final static String DEFAULT_FILE = "default.map";

    private final BufferedReader reader;

    private final Map map;

    private Cell startCell;

    // relative position to the start cell
    private RelativePosition curPos = new RelativePosition(0, 0);

    // bridge start cell not yet connected to end
    private final ArrayList<BridgeCell> openedBridge = new ArrayList<>();

    public MapReader() throws IOException, InvalidInputException, BridgeNotFoundException {
        this(DEFAULT_FILE);
    }

    public MapReader(final String fileName) throws IOException, InvalidInputException, BridgeNotFoundException {
        reader = new BufferedReader(new FileReader("." + File.separator + DIR_MAPS + File.separator + fileName));

        String curLine = "";
        String prevLine = "";
        Cell prevCell = null;
        ArrayList<Cell> cellList = new ArrayList<>();

        // read all line
        while ((curLine = reader.readLine()) != null) {
            Cell cell = newCell(curLine, prevLine, prevCell);
            cellList.add(cell);
            prevLine = curLine;
            prevCell = cell;
        }
        this.map = new Map(cellList);
        this.map.setStartCell(startCell);

        linkBridge(cellList);
    }

    public Map getMap() {
        return this.map;
    }


    private Cell newCell(String curLine, String prevLine, @Nullable Cell prevCell) throws InvalidInputException {
        Cell res;
        String[] curOps = curLine.split(" ");
        String[] prevOps = prevLine.split(" ");

        switch (curOps[0]) {
            case "S":
                if (curOps.length == 2) {
                    res = new ItemCell(curPos, ItemCell.ItemType.START);
                    this.startCell = res;
                }
                else
                    res = new ItemCell(curPos, ItemCell.ItemType.SAW);
                break;
            case "C":
                res = new ItemCell(curPos, ItemCell.ItemType.EMPTY);
                break;
            case "H":
                res = new ItemCell(curPos, ItemCell.ItemType.HAMMER);
                break;
            case "P":
                res = new ItemCell(curPos, ItemCell.ItemType.PHILIPS_DRIVER);
                break;
            case "E":
                res = new ItemCell(curPos, ItemCell.ItemType.END);
                break;
            case "B":
                res = new BridgeCell(curPos, BridgeCell.BridgeType.START);
                openedBridge.add((BridgeCell) res);
                break;
            case "b":
                res = new BridgeCell(curPos, BridgeCell.BridgeType.END);
                break;
            default:
                throw new InvalidInputException(curLine);
        }

        if (prevCell != null) {
            // Link path from previous cell to current cell
            Direction dir = getDirection(prevOps[prevOps.length - 1]);
            prevCell.putAdjacentCell(dir, res);
            prevCell.putForwardCell(dir, res);

            // Link path from current cell to previous cell
            if (curOps.length == 3) {
                res.putAdjacentCell(getDirection(curOps[1]), prevCell);
            }

        }

        // Change next relative position
        if (curOps.length > 1) {
            curPos = curPos.getMovedPosition(getDirection(curOps[curOps.length - 1]));
        }

        return res;
    }

    private void linkBridge(ArrayList<Cell> cellList) throws BridgeNotFoundException {
        for (Cell c : cellList) {
            if (c instanceof BridgeCell && ((BridgeCell) c).getBridgeType() == BridgeCell.BridgeType.END) {

                boolean close = false;

                for (Iterator<BridgeCell> iter = openedBridge.iterator(); iter.hasNext();) {
                    BridgeCell opened = iter.next();

                    RelativePosition cPos = c.getPosition();
                    RelativePosition oPos = opened.getPosition();
                    if (cPos.getY() == oPos.getY() && Math.abs(cPos.getX() - oPos.getX()) == 2) {
                        ((BridgeCell) c).setConnectedCell(opened);
                        opened.setConnectedCell(c);
                        iter.remove();
                        close = true;
                    }
                }

                if (!close)
                    throw new BridgeNotFoundException((BridgeCell) c);
            }
        }
    }

    private static Direction getDirection(String input) throws InvalidInputException {
        switch (input) {
            case "L":
                return Direction.LEFT;
            case "R":
                return Direction.RIGHT;
            case "U":
                return Direction.UP;
            case "D":
                return Direction.DOWN;
        }
        throw new InvalidInputException(input);
    }

}
