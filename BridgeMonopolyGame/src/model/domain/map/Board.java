package model.domain.map;

import model.data.RelativePosition;
import model.domain.cell.Cell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Board {

    private final static int POS_INF = 23456789;
    private final static int NEG_INF = -23456789;

    private Cell mStartCell;

    private final ArrayList<Cell> mCellList = new ArrayList<>();

    /*
        Used when displaying in gui and cli
        (0, 0) is the leftmost and bottommost position
     */
    private Cell[][] mAbsoluteMap;

    public Cell getStartCell() {
        return this.mStartCell;
    }

    public void setStartCell(Cell cell) {
        this.mStartCell = cell;
    }

    public void putCellList(@NotNull ArrayList<Cell> list) {
        this.mCellList.addAll(list);
    }

    public void createAbsoluteMap() {
        if (mCellList.isEmpty())
            return;

        int minX = POS_INF, minY = POS_INF;
        int maxX = NEG_INF, maxY = NEG_INF;

        // get arrange of relative position
        for (Cell cell : mCellList) {
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

        mAbsoluteMap = new Cell[maxY - minY + 1][maxX - minX + 1];

        // load to absolute map
        for (Cell cell : mCellList) {
            RelativePosition pos = cell.getPosition();
            mAbsoluteMap[pos.getY() - minY][pos.getX() - minX] = cell;
        }
    }

    public void printAbsoluteMap() {
        if (mAbsoluteMap == null)
            return;

        for (int i = mAbsoluteMap.length - 1; i >= 0; i--) {
            for (int j = 0; j < mAbsoluteMap[i].length; j++) {
                if (mAbsoluteMap[i][j] == null)
                    System.out.print("X");
                else
                    System.out.print("O");
            }
            System.out.println();
        }
    }
}
