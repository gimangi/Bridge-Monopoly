package model.domain.cell;

import model.data.Direction;
import model.data.RelativePosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Cell {

    protected final RelativePosition position;

    protected final HashMap<Direction, Cell> adjacentSet = new HashMap();

    protected Cell(RelativePosition position) {
        this.position = position;
    }

    protected boolean isAdjacentDir(Direction dir) {
        return this.adjacentSet.get(dir) != null;
    }

    public abstract boolean isMovableDir(Direction dir);

    public abstract Cell getAdjacentCell(Direction dir);

    public HashMap<Direction, Cell> getAdjacentSet() {
        return this.adjacentSet;
    }

    public void putAdjacentCell(Direction dir, Cell cell) {
        this.adjacentSet.put(dir, cell);
    }

    public RelativePosition getPosition() {
        return this.position;
    }
}
