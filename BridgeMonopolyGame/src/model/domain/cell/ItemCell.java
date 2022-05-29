package model.domain.cell;

import model.data.Direction;
import model.data.RelativePosition;
import org.jetbrains.annotations.Nullable;

public class ItemCell extends Cell {

    private ItemType mItemType;

    public ItemCell(RelativePosition position, ItemType itemType) {
        super(position);
        this.mItemType = itemType;
    }

    public ItemType getItemType() {
        return this.mItemType;
    }

    @Override
    public boolean isMovableDir(Direction dir) {
        return isAdjacentDir(dir);
    }

    @Nullable
    @Override
    public Cell getAdjacentCell(Direction dir) {
        return this.mAdjacentSet.get(dir);
    }

    public enum ItemType {
        START, END,                 // base cell
        EMPTY,                      // empty cell
        HAMMER, SAW, PHILIPS_DRIVER // item cell
    }
}
