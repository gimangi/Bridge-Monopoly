package view.swing;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlayerView extends JPanel {

    public static final int ICON_WIDTH = 50;
    public static final int ICON_HEIGHT = 50;

    public boolean ownTurn = false;

    private final int playerId;

    public PlayerView(int playerId) {
        this.playerId = playerId;

        JLabel idField = new JLabel("Player "+playerId);
        idField.setFont(new Font("Serif", Font.BOLD, 20));
        PieceView pieceView = new PieceView(playerId, ICON_WIDTH, ICON_HEIGHT);

        add(pieceView);
        add(idField);

        updateUI();
    }

    public void updateTurn() {

    }


}
