package view.gui.display;

import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerContainerView extends JPanel {

    private final ArrayList<Player> players = new ArrayList();

    private final ArrayList<PlayerView> playerViews = new ArrayList<>();

    public PlayerContainerView(ArrayList<Player> players) {
        this.players.addAll(players);

        setLayout(new FlowLayout(FlowLayout.LEADING , 100, 0));

        for (Player player : players) {
            PlayerView view = new PlayerView(player.getId());
            this.playerViews.add(view);
            add(view);
        }
    }

    public void setTurnOwner(Player player) {
        for (PlayerView view : playerViews) {
            view.setNormalColor();

            if (view.getPlayerId() == player.getId())
                view.setTurnColor();
        }
    }

    public void updatePlayerStatus(@NotNull ArrayList<Player> players) {
        this.players.clear();
        for (Player player : players) {
            this.players.add(player);
        }
        updateStatus();
    }

    private void updateStatus() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < playerViews.size(); j++) {
                if (players.get(i).getId() == playerViews.get(i).getPlayerId()) {
                    playerViews.get(i).setPoint(players.get(i).getPoint());
                    playerViews.get(i).setPenalty(players.get(i).getPenalty());
                }
            }
        }
    }

}
