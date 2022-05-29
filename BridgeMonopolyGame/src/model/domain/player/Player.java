package model.domain.player;

import model.domain.cell.Cell;
import org.jetbrains.annotations.NotNull;

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

    public Piece getPiece() {
        return this.piece;
    }


    public boolean isEnd() {
        return this.isEnd;
    }

}
