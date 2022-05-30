package model.domain.cell;

import model.data.Direction;
import model.data.MoveType;
import model.data.RelativePosition;
import org.jetbrains.annotations.Nullable;

import static model.domain.cell.BridgeCell.BridgeType.END;
import static model.domain.cell.BridgeCell.BridgeType.START;

public class BridgeCell extends Cell {

    private final BridgeType bridgeType;

    private Cell connectedCell;

    public BridgeCell(RelativePosition position, BridgeType bridgeType) {
        super(position);
        this.bridgeType = bridgeType;
    }

    @Override
    public boolean isMovableDir(Direction dir, MoveType moveType) {
        if (moveType == MoveType.ADJACENT) {
            if (bridgeType == START && dir == Direction.RIGHT)
                return true;
            if (bridgeType == END && dir == Direction.LEFT)
                return true;
            return isAdjacentDir(dir);
        }
        // forward move
        else {
            if (bridgeType == START && dir == Direction.RIGHT)
                return true;
            return isForwardDir(dir);
        }
    }

    @Override
    public @Nullable Cell getAdjacentCell(Direction dir) {
        if (this.bridgeType == START && dir == Direction.RIGHT || this.bridgeType == END && dir == Direction.LEFT) {
            return connectedCell;
        }
        return this.adjacentSet.get(dir);
    }

    public void setConnectedCell(Cell cell) {
        this.connectedCell = cell;
    }

    public BridgeType getBridgeType() {
        return this.bridgeType;
    }

    public enum BridgeType {
        START, END, BRIDGE
    }
}
