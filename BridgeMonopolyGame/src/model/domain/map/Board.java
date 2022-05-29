package model.domain.map;

import model.domain.cell.Cell;

public class Board {

    private Cell mStartCell;



    public Cell getStartCell() {
        return this.mStartCell;
    }

    public void setStartCell(Cell cell) {
        this.mStartCell = cell;
    }
}
