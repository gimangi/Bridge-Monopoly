package model.domain.cell;

import model.data.Direction;
import model.data.RelativePosition;

import java.util.HashMap;

public abstract class Cell {

    protected final RelativePosition mPosition;

    protected final HashMap<Direction, Cell> mAdjacentSet = new HashMap();

    protected Cell(RelativePosition position) {
        this.mPosition = position;
    }

    protected boolean isAdjacentDir(Direction dir) {
        return this.mAdjacentSet.get(dir) != null;
    }

    public abstract boolean isMovableDir(Direction dir);

    public HashMap<Direction, Cell> getAdjacentCell() {
        return this.mAdjacentSet;
    }

    public void putAdjacentCell(Direction dir, Cell cell) {
        this.mAdjacentSet.put(dir, cell);
    }
}
