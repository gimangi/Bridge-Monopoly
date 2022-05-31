package model.domain.cell;

import model.data.Direction;
import model.data.MoveType;
import model.data.RelativePosition;
import org.jetbrains.annotations.Nullable;

public class ItemCell extends Cell {

    private ItemType itemType;

    public ItemCell(RelativePosition position, ItemType itemType) {
        super(position);
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    @Override
    public boolean isMovableDir(Direction dir, MoveType moveType) {
        if (itemType == ItemType.END)
            return true;
        if (moveType == MoveType.ADJACENT)
            return isAdjacentDir(dir);
        // forward move
        else
            return isForwardDir(dir);
    }

    @Override
    public @Nullable Cell getAdjacentCell(Direction dir) {
        if (itemType == ItemType.END)
            return this;
        return this.adjacentSet.get(dir);
    }

    public int getPoint() {
        switch (itemType) {
            case HAMMER:
                return 2;
            case SAW:
                return 3;
            case PHILIPS_DRIVER:
                return 1;
            default:
                return 0;
        }
    }

    public enum ItemType {
        START, END,                 // base cell
        EMPTY,                      // empty cell
        HAMMER, SAW, PHILIPS_DRIVER // item cell
    }
}
