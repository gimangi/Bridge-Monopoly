package model.domain.rule;

import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Turn {

    private boolean allowMoveBack = true;

    private int turnCount = 0;

    private final ArrayList<Player> players;

    private Player turnOwner;

    public Turn(@NotNull ArrayList<Player> players) {
        this.players = players;

        if (!players.isEmpty())
            turnOwner = players.get(0);
    }

    public boolean nextTurn() {
        // Check that all turns are over
        boolean allPlayerEnd = true;
        for (Player p : players) {
            if (!p.isEnd())
                allPlayerEnd = false;
        }
        if (allPlayerEnd)
            return false;

        int idx = players.indexOf(turnOwner);
        int nextIdx = (idx + 1) % players.size();
        if (nextIdx == 0)
            turnCount++;
        turnOwner = players.get(nextIdx);
        return true;
    }

    public Player getTurnOwner() {
        return this.turnOwner;
    }

}
