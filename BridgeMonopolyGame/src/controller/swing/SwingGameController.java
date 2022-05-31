package controller.swing;

import model.data.Direction;
import model.domain.rule.BridgeMonopolyGame;
import org.jetbrains.annotations.NotNull;
import view.swing.control.CombDirView;
import view.swing.control.DiceView;
import view.swing.control.SelectStayView;
import view.swing.display.MainFrame;
import view.swing.display.MapView;
import view.swing.display.PlayerContainerView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class SwingGameController extends BridgeMonopolyGame {

    private final MainFrame mainFrame;

    private MapView mapView;

    private PlayerContainerView playerContainerView;

    public SwingGameController(final MainFrame view) {
        this.mainFrame = view;
    }

    @Override
    protected Callable<Boolean> selectUseDefaultMap() {
        Callable<Boolean> call = () -> {
            String option[] = new String[]{"맵 파일을 직접 선택", "기본 맵을 사용"};
            int answer = JOptionPane.showOptionDialog(null, "맵 파일을 불러올 방법을 선택해주세요.", "맵 선택", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);
            if (answer == 0)
                return true;
            return false;
        };
        return call;
    }

    @Override
    protected Callable<String> enterMapFile() {
        Callable<String> call = () -> JOptionPane.showInputDialog("사용할 맵 파일 이름을 입력해주세요.");
        return call;
    }

    @Override
    protected void displayNotFoundMap() {
        JOptionPane.showMessageDialog(null, "파일이 존재하지 않습니다.", "File not found", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected Callable<Integer> enterNumberOfPlayers() {

        Callable<Integer> call = () -> {
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
        };

        return call;
    }

    @Override
    protected void initDisplay() {
        mapView = new MapView(board.getAbsoluteMap());
        playerContainerView = new PlayerContainerView(turn.getPlayers());
        mainFrame.add(mapView, BorderLayout.WEST);
        mainFrame.add(playerContainerView, BorderLayout.SOUTH);
        updateMainFrame();
    }

    @Override
    protected void refresh() {
        playerContainerView.setTurnOwner(turn.getTurnOwner());
        playerContainerView.updatePlayerStatus(turn.getPlayers());
    }

    @Override
    protected Callable<Boolean> selectStay(int playerId) {

        WaitCall<Boolean> call = new WaitCall() {
            @Override
            public Boolean call() throws Exception {
                SelectStayView selectView = new SelectStayView(turn.getTurnOwner().getId());
                mainFrame.add(selectView, BorderLayout.EAST);
                updateMainFrame();

                selectView.getBtnStay().addActionListener(e -> {
                    mainFrame.remove(selectView);
                    updateMainFrame();
                    signalResult(true);
                });

                selectView.getBtnMove().addActionListener(e -> {
                    mainFrame.remove(selectView);
                    updateMainFrame();
                    signalResult(false);
                });

                waitResult();
                return (Boolean) this.result;
            }
        };

        return call;
    }

    @Override
    protected Callable<Integer> rollDice() {

        WaitCall<Integer> call = new WaitCall<Integer>() {
            @Override
            public Integer call() throws Exception {
                DiceView diceView = new DiceView();
                mainFrame.add(diceView, BorderLayout.EAST);
                updateMainFrame();

                diceView.getButton().addActionListener(e -> {
                    int diceResult = diceView.getDiceResult();
                    mainFrame.remove(diceView);
                    updateMainFrame();
                    signalResult(diceResult);
                });

                waitResult();
                return result;
            }
        };

        return call;
    }

    @Override
    protected @NotNull Callable<ArrayList<Direction>> enterDirection(int diceResult, int penalty) {

        WaitCall<ArrayList<Direction>> call = new WaitCall<ArrayList<Direction>>() {
            @Override
            public ArrayList<Direction> call() throws Exception {
                CombDirView dirView = new CombDirView(diceResult);
                mainFrame.add(dirView);
                updateMainFrame();

                dirView.getButton(CombDirView.ButtonType.UP).addActionListener(e -> {
                    if (dirView.addSelectDir(Direction.UP)) {
                        mainFrame.remove(dirView);
                        updateMainFrame();
                        signalResult(dirView.getSelectedDirs());
                    }
                });

                dirView.getButton(CombDirView.ButtonType.DOWN).addActionListener(e -> {
                    if (dirView.addSelectDir(Direction.DOWN)) {
                        mainFrame.remove(dirView);
                        updateMainFrame();
                        signalResult(dirView.getSelectedDirs());
                    }
                });

                dirView.getButton(CombDirView.ButtonType.LEFT).addActionListener(e -> {
                    if (dirView.addSelectDir(Direction.LEFT)) {
                        mainFrame.remove(dirView);
                        updateMainFrame();
                        signalResult(dirView.getSelectedDirs());
                    }
                });

                dirView.getButton(CombDirView.ButtonType.RIGHT).addActionListener(e -> {
                    if (dirView.addSelectDir(Direction.RIGHT)) {
                        mainFrame.remove(dirView);
                        updateMainFrame();
                        signalResult(dirView.getSelectedDirs());
                    }
                });

                waitResult();
                return result;
            }
        };

        return call;
    }

    @Override
    protected void alertInvalidMove() {
        JOptionPane.showMessageDialog(null, "해당 방향으로 이동할 수 없습니다..", "Can not move", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected void displayWinner() {

    }

    private void updateMainFrame() {
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
