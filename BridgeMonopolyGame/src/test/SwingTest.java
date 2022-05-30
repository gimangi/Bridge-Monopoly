package test;

import controller.swing.SwingGameController;
import view.swing.MainFrame;

public class SwingTest {

    public static void main(String args[]) {
        MainFrame view = new MainFrame();
        SwingGameController controller = new SwingGameController(view);
        controller.run();


    }
}
