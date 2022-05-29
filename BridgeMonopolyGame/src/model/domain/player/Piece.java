package model.domain.player;

import model.data.Direction;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Piece {

    private Cell curCell;

    public Piece(Cell startCell) {
        this.curCell = startCell;
    }

    public boolean move(@NotNull final ArrayList<Direction> dir) {
        Cell cur = curCell;

        for (int i = 0; i < dir.size(); i++) {
            if (cur.isMovableDir(dir.get(i))) {
                Cell dest = cur.getAdjacentCell(dir.get(i));

                if (cur instanceof BridgeCell && dest instanceof BridgeCell) {
                    if (!(i+1 < dir.size()))
                        return false;
                    if (dir.get(i) != Direction.RIGHT)
                        return false;
                    i++;
                }
                cur = dest;
            }
            else
                return false;
        }

        // move successfully
        this.curCell = cur;
        return true;
    }

    public Cell getCurCell() {
        return curCell;
    }
}
