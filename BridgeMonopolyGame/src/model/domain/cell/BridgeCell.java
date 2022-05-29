package model.domain.cell;

import model.data.Direction;
import model.data.RelativePosition;
import org.jetbrains.annotations.Nullable;

import static model.domain.cell.BridgeCell.BridgeType.END;
import static model.domain.cell.BridgeCell.BridgeType.START;

public class BridgeCell extends Cell {

    private final BridgeType mBridgeType;

    private Cell mConnectedCell;

    public BridgeCell(RelativePosition position, BridgeType bridgeType) {
        super(position);
        this.mBridgeType = bridgeType;
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
        if (mBridgeType == START && dir == Direction.RIGHT)
            return true;
        if (mBridgeType == END && dir == Direction.LEFT)
            return true;
        return isAdjacentDir(dir);
    }

    @Override
    @Nullable
    public Cell getAdjacentCell(Direction dir) {
        if (this.mBridgeType == START && dir == Direction.RIGHT || this.mBridgeType == END && dir == Direction.LEFT) {
            return mConnectedCell;
        }
        return this.mAdjacentSet.get(dir);
    }

    public void setConnectedCell(Cell cell) {
        this.mConnectedCell = cell;
    }

    public enum BridgeType {
        START, END
    }
}
