package model.domain.cell;

import model.data.RelativePosition;

import java.util.HashMap;

public abstract class Cell {

    protected RelativePosition position;

    protected final HashMap<Direction, Cell> adjacentSet = new HashMap();

    protected boolean isAdjacentDir(Direction dir) {
        return this.adjacentSet.get(dir) != null;
    }

    public abstract boolean isMovableDir(Direction dir);

    public HashMap<Direction, Cell> getAdjacentCell() {
        return this.adjacentSet;
    }

    enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }
}
