package model.domain.cell;

import model.data.Direction;
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

    /*

    public static BridgeCell startBridge() {
        BridgeCell bridgeCell = new BridgeCell(START);
        opened.add(bridgeCell);
        return bridgeCell;
    }

    public static BridgeCell endBridge() throws BridgeNotFoundException {
        BridgeCell bridgeCell = new BridgeCell(END);

        for (BridgeCell c : opened) {
            if (c.bridgeType == START && c.position.getY() == bridgeCell.position.getY()) {
                // link bridge cell
                c.connectedCell = bridgeCell;
                bridgeCell.connectedCell = c;

                opened.remove(c);
                return bridgeCell;
            }
        }

        throw new BridgeNotFoundException();
    }


     */

    @Override
    public boolean isMovableDir(Direction dir) {
        if (bridgeType == START && dir == Direction.RIGHT)
            return true;
        if (bridgeType == END && dir == Direction.LEFT)
            return true;
        return isAdjacentDir(dir);
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

    public enum BridgeType {
        START, END
    }
}
