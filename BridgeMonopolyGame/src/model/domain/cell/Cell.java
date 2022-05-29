package model.domain.cell;

import model.data.Direction;
import model.data.MoveType;
import model.data.RelativePosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Cell {

    protected final RelativePosition position;

    // Cells connected in the forward direction
    protected final HashMap<Direction, Cell> forwardSet = new HashMap<>();

    // Cells connected in the backward direction
    protected final HashMap<Direction, Cell> adjacentSet = new HashMap();

    protected Cell(RelativePosition position) {
        this.position = position;
    }

    protected boolean isAdjacentDir(Direction dir) {
        return this.adjacentSet.get(dir) != null;
    }

    protected boolean isForwardDir(Direction dir) {
        return this.forwardSet.get(dir) != null;
    }

    public abstract boolean isMovableDir(Direction dir, MoveType moveType);

    public abstract Cell getAdjacentCell(Direction dir);

    public void putAdjacentCell(Direction dir, Cell cell) {
        this.adjacentSet.put(dir, cell);
    }

    public void putForwardCell(Direction dir, Cell cell) {
        this.forwardSet.put(dir, cell);
    }

    public RelativePosition getPosition() {
        return this.position;
    }

}
