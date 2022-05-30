package controller.swing;

import model.data.Direction;
import model.domain.rule.BridgeMonopolyGame;
import org.jetbrains.annotations.NotNull;
import view.swing.MainFrame;
import view.swing.MapView;
import view.swing.PlayerContainerView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingGameController extends BridgeMonopolyGame {

    private final MainFrame mainFrame;

    private MapView mapView;

    private PlayerContainerView playerView;

    public SwingGameController(final MainFrame view) {
        this.mainFrame = view;
    }

    @Override
    protected boolean selectUseDefaultMap() {
        String option[] = new String[]{"맵 파일을 직접 선택", "기본 맵을 사용"};
        int answer = JOptionPane.showOptionDialog(null, "맵 파일을 불러올 방법을 선택해주세요.", "맵 선택", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);
        if (answer == 0)
            return true;
        return false;
    }

    @Override
    protected String enterMapFile() {
        return JOptionPane.showInputDialog("사용할 맵 파일 이름을 입력해주세요.");
    }

    @Override
    protected void displayNotFoundMap() {
        JOptionPane.showMessageDialog(null, "파일이 존재하지 않습니다.", "File not found", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected int enterNumberOfPlayers() {

        while (true) {
            String input = JOptionPane.showInputDialog("플레이어 수를 입력해주세요. (2 ~ 4)");
            int res;
            try {
                res = Integer.parseInt(input);
                if (2 <= res && res <= 4)
                    return res;
                else
                    JOptionPane.showMessageDialog(null, "플레이어 수는 2 ~ 4의 범위에서 선택할 수 있습니다..", "Invalid number", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "숫자를 입력해주세요..", "Invalid number", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void initDisplay() {
        mapView = new MapView(board.getAbsoluteMap());
        playerView = new PlayerContainerView(turn.getPlayers());
        mainFrame.add(mapView, BorderLayout.WEST);
        mainFrame.add(playerView, BorderLayout.SOUTH);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    @Override
    protected void refresh() {


    }

    @Override
    protected boolean selectStay(int playerId) {
        return false;
    }

    @Override
    protected int rollDice() {
        return 0;
    }

    @Override
    protected @NotNull ArrayList<Direction> enterDirection(int diceResult, int penalty) {
        return null;
    }

    @Override
    protected void alertInvalidMove() {

    }

    @Override
    protected void displayWinner() {

    }
}
