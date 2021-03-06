package view.gui.interaction;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Random;

public class DiceView extends JPanel {

    private static final String IMG_DICE = "img_dice.png";

    private int diceResult = 0;

    private final JButton diceBtn;

    public DiceView() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(200, 300));

        URL url = getClass().getClassLoader().getResource(IMG_DICE);
        ImageIcon img = new ImageIcon(url);
        diceBtn = new JButton(img);
        diceBtn.setPreferredSize(new Dimension(200, 200));
        JLabel text = new JLabel("주사위를 굴리세요.");
        diceBtn.setBorderPainted(false);

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        diceResult = random.nextInt(6) + 1;

        add(diceBtn);
        add(text);
    }

    public int getDiceResult() {
        return this.diceResult;
    }

    public JButton getButton() {
        return this.diceBtn;
    }

}
