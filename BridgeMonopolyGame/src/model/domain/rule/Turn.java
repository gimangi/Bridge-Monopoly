package model.domain.rule;

import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Turn {

    private boolean allowMoveBack = true;

    private boolean allPlayerEnd = false;

    private int turnCount = 0;

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

        int idx = players.indexOf(turnOwner);
        int nextIdx;

        // not end player
        while (true) {
            nextIdx = (idx + 1) % players.size();
            if (!players.get(nextIdx).isEnd())
                break;
        }
        // count turn
        if (nextIdx == 0)
            turnCount++;
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

}
