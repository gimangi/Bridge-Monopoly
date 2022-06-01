package model.domain.rule;

import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Turn {

    private boolean allowMoveBack = true;

    private boolean allPlayerEnd = false;

    private final ArrayList<Player> players;

    private Player turnOwner;

    public Turn(@NotNull ArrayList<Player> players) {
        this.players = players;

        if (!players.isEmpty())
            turnOwner = players.get(0);
    }

    public boolean proceedTurn() {
        // Check that all turns are over
        boolean allPlayerEnd = true;
        for (Player p : players) {
            if (p.isEnd())
                this.allowMoveBack = false;

            if (!p.isEnd())
                allPlayerEnd = false;
        }
        if (allPlayerEnd) {
            this.allPlayerEnd = true;
            return false;
        }

        int nextIdx = players.indexOf(turnOwner);

        // not end player
        while (true) {
            nextIdx = (nextIdx + 1) % players.size();
            if (!players.get(nextIdx).isEnd())
                break;
        }
        turnOwner = players.get(nextIdx);
        return true;
    }

    public @Nullable Player getTurnOwner() {
        if (this.allPlayerEnd)
            return null;
        return this.turnOwner;
    }

    public boolean getAllowMoveBack() {
        return this.allowMoveBack;
    }

    public ArrayList<Player> getPlayerList() {
        return this.players;
    }

    public @NotNull Player getWinner() {
        int maxScore = -1;
        Player winner = null;

        for (Player p : players) {
            if (maxScore < p.getPoint()) {
                maxScore = p.getPoint();
                winner = p;
            }
        }

        return winner;
    }

}
