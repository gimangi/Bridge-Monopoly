package model.domain.cell;

import model.domain.exception.BridgeNotFoundException;

import java.util.ArrayList;

import static model.domain.cell.BridgeCell.BridgeType.END;
import static model.domain.cell.BridgeCell.BridgeType.START;

public class BridgeCell extends Cell {

    private final BridgeType bridgeType;

    private Cell connectedCell;

    private BridgeCell(BridgeType bridgeType) {
        this.bridgeType = bridgeType;
    }

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

    @Override
    public boolean isMovableDir(Direction dir) {
        if (bridgeType == START && dir == Direction.RIGHT)
            return true;
        if (bridgeType == END && dir == Direction.LEFT)
            return true;
        return isAdjacentDir(dir);
    }

    public static void clear() {
        opened.clear();
    }

    enum BridgeType {
        START, END
    }
}
