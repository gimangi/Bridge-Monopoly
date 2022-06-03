package model.domain.map;

import model.data.RelativePosition;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class Map {

    private final static int POS_INF = 23456789;
    private final static int NEG_INF = -23456789;

    private Cell startCell;

    private final ArrayList<Cell> cellList = new ArrayList<>();

    /*
        Used when displaying in gui and cli
        (0, 0) is the leftmost and bottommost position
     */
    private Cell[][] absoluteMap;

    public Cell getStartCell() {
        return this.startCell;
    }

    public void setStartCell(Cell cell) {
        this.startCell = cell;
    }

    public void putCellList(@NotNull ArrayList<Cell> list) {
        this.cellList.addAll(list);
    }

    public void createAbsoluteMap() {
        if (cellList.isEmpty())
            return;

        int minX = POS_INF, minY = POS_INF;
        int maxX = NEG_INF, maxY = NEG_INF;

        // get arrange of relative position
        for (Cell cell : cellList) {
            int x = cell.getPosition().getX();
            int y = cell.getPosition().getY();

            if (x > maxX)
                maxX = x;
            if (x < minX)
                minX = x;
            if (y > maxY)
                maxY = y;
            if (y < minY)
                minY = y;
        }

        absoluteMap = new Cell[maxY - minY + 1][maxX - minX + 1];

        // load to absolute map
        for (Cell cell : cellList) {
            RelativePosition pos = cell.getPosition();
            absoluteMap[maxY - pos.getY()][pos.getX() - minX] = cell;
        }

        // generate bridge path
        for (int y = 0; y < absoluteMap.length; y++) {
            for (int x = 0; x < absoluteMap[y].length; x++) {
                // bridge path information
                if (absoluteMap[y][x] instanceof BridgeCell && ((BridgeCell) absoluteMap[y][x]).getBridgeType() == BridgeCell.BridgeType.START) {
                    int lookX = x + 1;
                    while (lookX < absoluteMap[y].length && absoluteMap[y][lookX] == null) {
                        absoluteMap[y][lookX] = new BridgeCell(null, BridgeCell.BridgeType.BRIDGE);
                        lookX++;
                    }
                }
            }
        }


    }

    public Cell[][] getAbsoluteMap() {
        return this.absoluteMap;
    }

    // for debug
    public void printAbsoluteMap() {
        if (absoluteMap == null)
            return;

        for (int i = 0; i < absoluteMap.length; i++) {
            for (int j = 0; j < absoluteMap[i].length; j++) {
                if (absoluteMap[i][j] == null)
                    System.out.print("X");
                else
                    System.out.print("O");
            }
            System.out.println();
        }
    }
}
