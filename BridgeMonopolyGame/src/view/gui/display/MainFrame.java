package view.gui.display;

import javax.swing.*;

public class MainFrame extends JFrame {

    public static final String TITLE_NAME = "Bridge Monopoly Game";

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(TITLE_NAME);
        setSize(1200, 1000);
        setResizable(false);
        setVisible(true);


    }

}
