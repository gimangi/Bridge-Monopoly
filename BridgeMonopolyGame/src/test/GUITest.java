package test;

import controller.gui.GUIGameController;
import view.gui.display.MainFrame;

public class GUITest {

    public static void main(String args[]) {
        MainFrame view = new MainFrame();
        GUIGameController controller = new GUIGameController(view);
        controller.run();

    }
}
