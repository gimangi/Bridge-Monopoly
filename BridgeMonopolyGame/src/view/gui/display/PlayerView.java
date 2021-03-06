package view.gui.display;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {

    public static final int ICON_WIDTH = 50;
    public static final int ICON_HEIGHT = 50;

    private final int playerId;

    private final JLabel idLabel;

    private final JLabel pointLabel = new JLabel();

    private final JLabel penaltyLabel = new JLabel();

    public PlayerView(int playerId) {
        this.playerId = playerId;

        idLabel = new JLabel("Player "+playerId);
        idLabel.setFont(new Font("Serif", Font.BOLD, 20));

        JLabel pointText = new JLabel("point");
        JLabel penaltyText = new JLabel("penalty");

        GridLayout layout = new GridLayout(3, 2);
        layout.setVgap(0);
        layout.setHgap(0);

        setLayout(layout);

        PieceView pieceView = new PieceView(playerId, ICON_WIDTH, ICON_HEIGHT);

        add(pieceView);
        add(idLabel);
        add(pointText);
        add(pointLabel);
        add(penaltyText);
        add(penaltyLabel);

        updateUI();
    }

    public void setTurnColor() {
        idLabel.setForeground(Color.RED);
        updateUI();
    }

    public void setNormalColor() {
        idLabel.setForeground(Color.BLACK);
        updateUI();
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPoint(int point) {
        pointLabel.setText(""+point);
        updateUI();
    }

    public void setPenalty(int penalty) {
        penaltyLabel.setText(""+penalty);
        updateUI();
    }


}
