package controller.gui;

import model.data.Direction;
import model.domain.player.Player;
import model.domain.rule.BridgeMonopolyGame;
import org.jetbrains.annotations.NotNull;
import view.gui.interaction.CombDirView;
import view.gui.interaction.DiceView;
import view.gui.interaction.SelectStayView;
import view.gui.display.MainFrame;
import view.gui.display.MapView;
import view.gui.display.PlayerContainerView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class GUIGameController extends BridgeMonopolyGame {

    private final MainFrame mainFrame;

    private MapView mapView;

    private PlayerContainerView playerContainerView;

    public GUIGameController(final MainFrame view) {
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
                        JOptionPane.showMessageDialog(null, "플레이어 수는 2 ~ 4의 범위에서 선택할 수 있습니다.", "Invalid number", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "숫자를 입력해주세요.", "Invalid number", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        return call;
    }

    @Override
    protected void initDisplay() {
        mapView = new MapView(map.getAbsoluteMap());
        playerContainerView = new PlayerContainerView(turn.getPlayerList());
        mainFrame.add(mapView, BorderLayout.WEST);
        mainFrame.add(playerContainerView, BorderLayout.SOUTH);
        updateMainFrame();
    }

    @Override
    protected void refresh() {
        playerContainerView.setTurnOwner(turn.getTurnOwner());
        playerContainerView.updatePlayerStatus(turn.getPlayerList());

        mapView.clearPiece();

        for (Player player : turn.getPlayerList()) {
            mapView.displayPiece(player);
        }

        updateMainFrame();
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
    protected void displayMoveValueZero(int diceResult, int penalty, int deduct) {
        JOptionPane.showMessageDialog(null, "주사위 결과 : " + diceResult + " 패널티 : " + penalty + " 사용한 눈금 : " + deduct + "\n남은 눈금이 0이하입니다.", "Can not move", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected @NotNull Callable<ArrayList<Direction>> enterDirection(int diceResult, int penalty, int deduct) {

        WaitCall<ArrayList<Direction>> call = new WaitCall<ArrayList<Direction>>() {
            @Override
            public ArrayList<Direction> call() throws Exception {
                CombDirView dirView = new CombDirView(diceResult, penalty, deduct);
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
    protected Callable<Boolean> selectMoveBridge(int diceResult, int penalty, int deduct) {
        Callable<Boolean> call = () -> {
            CombDirView dirView = new CombDirView(diceResult, penalty, deduct);
            mainFrame.add(dirView);
            updateMainFrame();

            String option[] = new String[]{"다리로 이동", "그냥 진행"};
            int answer = JOptionPane.showOptionDialog(null, "다리로 이동하시겠습니까? (penalty가 증가합니다)", "다리 이동", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null);
            mainFrame.remove(dirView);
            if (answer == 0)
                return true;
            return false;
        };

        return call;
    }

    @Override
    protected void alertInvalidMove() {
        JOptionPane.showMessageDialog(null, "해당 방향으로 이동할 수 없습니다.", "Can not move", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected void displayCanNotMoveBack() {
        JLabel textLabel = new JLabel("이제부터 앞으로만 이동할 수 있습니다.");
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        mainFrame.add(textLabel, BorderLayout.NORTH);
    }

    @Override
    protected void displayWinner() {
        Player winner = turn.getWinner();
        JOptionPane.showMessageDialog(null, "게임이 종료되었습니다.\n승리 : Player" + winner.getId() + "\n획득한 점수 : " + winner.getPoint(), "Game end", JOptionPane.ERROR_MESSAGE);
    }

    private void updateMainFrame() {
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
