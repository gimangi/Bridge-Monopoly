package view.gui.display;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PieceView extends JLabel {

    public PieceView(int playerId, int width, int height) {

        URL url = getClass().getClassLoader().getResource(getIconName(playerId));
        ImageIcon imageIcon = new ImageIcon(url);
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);
        setIcon(imageIcon);
    }

    private @NotNull String getIconName(int playerId) {
        switch (playerId) {
            case 1:
                return "ic_player_red.png";
            case 2:
                return "ic_player_blue.png";
            case 3:
                return "ic_player_green.png";
            default:
                return "ic_player_purple.png";
        }
    }

}
