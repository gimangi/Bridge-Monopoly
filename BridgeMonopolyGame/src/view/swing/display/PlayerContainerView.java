package view.swing.display;

import model.domain.player.Player;

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

}
