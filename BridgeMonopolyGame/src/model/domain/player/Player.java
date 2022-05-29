package model.domain.player;

public class Player {

    private static int numOfPlayers = 0;

    private final int id;

    private final Piece piece;

    private boolean isEnd = false;

    private Player(int id, Piece piece) {
        this.id = id;
        this.piece = piece;
    }

    public static Player newInstance() {
        Player player = new Player(++numOfPlayers, new Piece());
        return player;
    }

    public static void clear() {
        numOfPlayers = 0;
    }

    public int getId() {
        return this.id;
    }

    

    public boolean isEnd() {
        return this.isEnd;
    }

}
