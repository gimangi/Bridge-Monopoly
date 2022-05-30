package view.swing;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PieceView extends JLabel {

    private static final String DIR_RES = "." + File.separator + "res" + File.separator;

    public PieceView(int playerId, int width, int height) {

        ImageIcon imageIcon = new ImageIcon(getIconName(playerId));
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);
        setIcon(imageIcon);
    }

    private @NotNull String getIconName(int playerId) {
        switch (playerId) {
            case 1:
                return DIR_RES + "ic_player_red.png";
            case 2:
                return DIR_RES + "ic_player_blue.png";
            case 3:
                return DIR_RES + "ic_player_green.png";
            default:
                return DIR_RES + "ic_player_purple.png";
        }
    }

}
