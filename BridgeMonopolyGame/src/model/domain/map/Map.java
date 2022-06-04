package model.domain.map;

import model.data.RelativePosition;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import java.util.ArrayList;
import java.util.List;

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

    public Map(final List<Cell> cellList) {
        this.cellList.addAll(cellList);

        createAbsoluteMap();
    }

    private void createAbsoluteMap() {
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

}
