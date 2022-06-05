import controller.gui.GUIGameController;
import model.domain.rule.BridgeMonopolyGame;
import view.gui.display.MainFrame;

public class GUIRunner {

    public static void main(String args[]) {
        MainFrame view = new MainFrame();
        GUIGameController controller = new GUIGameController(view);
        BridgeMonopolyGame game = new BridgeMonopolyGame(controller);
        game.run();

    }
}
