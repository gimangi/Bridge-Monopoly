import controller.cli.CLIGameController;
import model.domain.rule.BridgeMonopolyGame;

public class CLIRunner {
    public static void main(String args[]) {
        CLIGameController controller = new CLIGameController();
        BridgeMonopolyGame game = new BridgeMonopolyGame(controller);
        game.run();
    }
}
