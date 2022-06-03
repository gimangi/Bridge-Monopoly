package model.domain.rule;

import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Turn {

    private boolean allowMoveBack = true;

    private final ArrayList<Player> players;

    private final Queue<Player> remnantQueue = new LinkedList<>();

    public Turn(@NotNull List<Player> players) {
        this.players = new ArrayList(players);
        this.remnantQueue.addAll(players);
    }

    public boolean proceedTurn() {

        if (remnantQueue.isEmpty())
            return false;
        
        Player prevPlayer = remnantQueue.poll();
        if (!prevPlayer.isEnd())
            remnantQueue.add(prevPlayer);
        else
            allowMoveBack = false;
        
        return true;
    }

    public @Nullable Player getTurnOwner() {
        if (remnantQueue.isEmpty())
            return null;
        return remnantQueue.peek();
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
