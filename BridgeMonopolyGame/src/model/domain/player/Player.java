package model.domain.player;

import model.data.Direction;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Player {

    private static int numOfPlayers = 0;

    private final int id;

    private int point = 0;

    private final Piece piece;

    private boolean isEnd = false;

    private Player(int id, Piece piece) {
        this.id = id;
        this.piece = piece;
    }

    public static @NotNull Player newInstance(Cell startCell) {
        Player player = new Player(++numOfPlayers, new Piece(startCell));
        return player;
    }

    public static void clear() {
        numOfPlayers = 0;
    }

    public int getId() {
        return this.id;
    }

    public boolean move(@NotNull final ArrayList<Direction> dir) {
        boolean res =  piece.move(dir);

        // moved
        if (res) {
            Cell curCell = piece.getCurCell();
            if (curCell instanceof ItemCell && ((ItemCell) curCell).getItemType() == ItemCell.ItemType.END)
                this.isEnd = true;
        }

        return res;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

}
