package model.domain.player;

public class Player {

    private static int numOfPlayers = 0;

    private final int id;

    private Piece piece;

    private Player(int id) {
        this.id = id;
    }

    public static Player newInstance() {
        Player player = new Player(++numOfPlayers);
        player.piece = new Piece();
        return player;
    }

    public static void clear() {
        numOfPlayers = 0;
    }

    public int getId() {
        return this.id;
    }

}
