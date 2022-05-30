package view.swing;

import model.domain.player.Player;

import javax.swing.*;
import java.util.ArrayList;

public class PlayerContainerView extends JPanel {

    private final ArrayList<Player> players = new ArrayList();

    private final ArrayList<PlayerView> playerViews = new ArrayList<>();

    public PlayerContainerView(ArrayList<Player> players) {
        this.players.addAll(players);

        for (Player player : players) {
            PlayerView view = new PlayerView(player.getId());
            this.playerViews.add(view);
            add(view);
        }
    }

}
