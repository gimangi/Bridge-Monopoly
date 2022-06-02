package model.domain.cell;

import model.data.Direction;
import model.data.MoveType;
import model.data.RelativePosition;
import org.jetbrains.annotations.Nullable;

import static model.data.MoveType.ADJACENT;
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
        switch (moveType) {
            case ADJACENT:
                return isAdjacentDir(dir);
            case FORWARD:
                return isForwardDir(dir);
            case BRIDGE:
                if (bridgeType == START && dir == Direction.RIGHT || bridgeType == END && dir == Direction.LEFT)
                    return true;
        }
        return false;
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
