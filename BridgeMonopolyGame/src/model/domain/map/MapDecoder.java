package model.domain.map;

import model.data.Direction;
import model.data.RelativePosition;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import model.domain.exception.BridgeNotFoundException;
import model.domain.exception.InvalidInputException;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapDecoder {

    private final static String DIR_MAPS = "maps";

    private final static String DEFAULT_FILE = "default.map";

    private final String mFileName;

    private final BufferedReader mReader;

    private final Board mBoard = new Board();

    // relative position to the start cell
    private RelativePosition mCurPos = new RelativePosition(0, 0);

    // bridge start cell not yet connected to end
    private final HashMap<Integer, BridgeCell> mOpenedBridge = new HashMap<>();

    public MapDecoder() throws IOException {
        this(DEFAULT_FILE);
    }

    public MapDecoder(final String fileName) throws IOException {
        this.mFileName = fileName;
        mReader = new BufferedReader(new FileReader("." + File.separator + DIR_MAPS + File.separator + fileName));

        int lineNum = 1;
        String curLine = "";
        String prevLine = "";
        Cell prevCell = null;
        ArrayList<Cell> cellList = new ArrayList<>();

        try {
            // read all line
            while ((curLine = mReader.readLine()) != null) {
                Cell cell = newCell(curLine, prevLine, prevCell);
                cellList.add(cell);
                prevLine = curLine;
                prevCell = cell;

                lineNum++;
            }
            this.mBoard.putCellList(cellList);

        } catch (InvalidInputException e) {
            System.out.println("Invalid input : " + e.getLine());
        } catch (BridgeNotFoundException e) {
            System.out.println("The bridge cell to connect to does not exist in line " + lineNum + " : " + curLine);
        }
    }

    public Board getBoard() {
        return this.mBoard;
    }


    private Cell newCell(String curLine, String prevLine, @Nullable Cell prevCell) throws InvalidInputException, BridgeNotFoundException {
        Cell res;
        String[] curOps = curLine.split(" ");
        String[] prevOps = prevLine.split(" ");

        switch (curOps[0]) {
            case "S":
                if (curOps.length == 2) {
                    res = new ItemCell(mCurPos, ItemCell.ItemType.START);
                    this.mBoard.setStartCell(res);
                }
                else
                    res = new ItemCell(mCurPos, ItemCell.ItemType.SAW);
                break;
            case "C":
                res = new ItemCell(mCurPos, ItemCell.ItemType.EMPTY);
                break;
            case "H":
                res = new ItemCell(mCurPos, ItemCell.ItemType.HAMMER);
                break;
            case "P":
                res = new ItemCell(mCurPos, ItemCell.ItemType.PHILIPS_DRIVER);
                break;
            case "E":
                res = new ItemCell(mCurPos, ItemCell.ItemType.END);
                break;
            case "B":
                res = new BridgeCell(mCurPos, BridgeCell.BridgeType.START);
                mOpenedBridge.put(mCurPos.getY(), (BridgeCell) res);
                break;
            case "b":
                res = new BridgeCell(mCurPos, BridgeCell.BridgeType.END);

                // link connect cell
                BridgeCell pairCell = mOpenedBridge.get(mCurPos.getY());
                if (pairCell == null)
                    throw new BridgeNotFoundException();
                pairCell.setConnectedCell(res);
                ((BridgeCell) res).setConnectedCell(pairCell);

                // close bridge cell
                mOpenedBridge.remove(mCurPos.getY());
                break;
            default:
                throw new InvalidInputException(curLine);
        }

        if (prevCell != null) {
            // Link path from previous cell to current cell
            prevCell.putAdjacentCell(getDirection(prevOps[prevOps.length - 1]), res);

            // Link path from current cell to previous cell
            if (curOps.length == 3) {
                res.putAdjacentCell(getDirection(curOps[1]), prevCell);
            }

        }

        // Change next relative position
        if (curOps.length > 1) {
            mCurPos = mCurPos.getMovedPosition(getDirection(curOps[curOps.length - 1]));
        }

        return res;
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
