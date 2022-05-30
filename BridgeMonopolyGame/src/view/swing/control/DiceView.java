package view.swing.control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class DiceView extends JPanel {

    private static final String IMG_DICE = "." + File.separator + "res" + File.separator + "img_dice.png";

    private int diceResult = 0;

    public DiceView() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(200, 300));

        ImageIcon img = new ImageIcon(IMG_DICE);
        JButton btn = new JButton(img);
        btn.setPreferredSize(new Dimension(200, 200));
        JLabel text = new JLabel("주사위를 굴리세요.");
        btn.setBorderPainted(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                random.setSeed(System.currentTimeMillis());

                diceResult = random.nextInt(6) + 1;
                remove(btn);
                remove(text);
                updateUI();
            }
        });

        add(btn);
        add(text);
    }

    public int getDiceResult() {
        return this.diceResult;
    }

}
