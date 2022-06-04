package model.exception;

import model.domain.cell.BridgeCell;

public class BridgeNotFoundException extends Exception {

    private final BridgeCell cell;

    public BridgeNotFoundException(BridgeCell cell) {
        this.cell = cell;
    }

    public BridgeCell getCell() {
        return this.cell;
    }

    @Override
    public String toString() {
        return "The bridge cell to connect to does not exist in cell " + cell.toString();
    }
}
