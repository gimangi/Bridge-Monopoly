package model.domain.player;

import model.data.Direction;
import model.data.MoveResult;
import model.data.MoveType;
import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private Cell curCell;

    public Piece(Cell startCell) {
        this.curCell = startCell;
    }

    public MoveResult move(@NotNull final List<Direction> dir, MoveType moveType) {
        Cell cur = curCell;
        MoveResult res = MoveResult.SUCCESS;

        for (int i = 0; i < dir.size(); i++) {
            if (cur.isMovableDir(dir.get(i), moveType)) {
                Cell dest = cur.getAdjacentCell(dir.get(i));

                if (moveType == MoveType.BRIDGE && cur instanceof BridgeCell && dest instanceof BridgeCell) {
                    if (!(i+1 < dir.size()))
                        return MoveResult.FAIL;
                    if (!cur.isMovableDir(dir.get(i), MoveType.BRIDGE))
                        return MoveResult.FAIL;
                    i++;
                    res = MoveResult.SUCCESS_BRIDGED;
                }
                cur = dest;
            }
            else
                return MoveResult.FAIL;
        }

        // move successfully
        this.curCell = cur;
        return res;
    }

    public Cell getCurCell() {
        return curCell;
    }


}
