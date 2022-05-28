package model.domain.cell;

public class ItemCell extends Cell {

    private ItemType item;

    public ItemCell(ItemType item) {
        this.item = item;
    }

    public ItemType getItem() {
        return this.item;
    }

    @Override
    public boolean isMovableDir(Direction dir) {
        return isAdjacentDir(dir);
    }

    enum ItemType {
        START, END,                 // base cell
        EMPTY,                      // empty cell
        HAMMER, SAW, PHILIPS_DRIVER // item cell
    }
}
