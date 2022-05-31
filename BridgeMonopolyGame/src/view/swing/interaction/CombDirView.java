package view.swing.interaction;

import model.data.Direction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CombDirView extends JPanel {

    private final int diceResult;

    private final int penalty;

    private final JButton[] btnList = new JButton[4];

    private final JLabel labelDiceValue = new JLabel();

    private final JLabel labelResult = new JLabel();

    private final ArrayList<Direction> selectedDirs = new ArrayList();

    private static final String[] btnLabel = {"Up", "Down", "Left", "Right"};

    public CombDirView(int diceResult, int penalty) {
        this.diceResult = diceResult;
        this.penalty = penalty;

        setLayout(new GridBagLayout());

        labelDiceValue.setText("주사위 결과 : " + diceResult + ", penalty : " + penalty + ", 이동 가능 눈금 : " + (diceResult - penalty));
        GridBagConstraints labelCons = getConstraint(1, 1);
        labelCons.gridwidth = 3;
        add(labelDiceValue, labelCons);

        for (int i = 0; i < 4; i++) {
            btnList[i] = new JButton(btnLabel[i]);
            GridBagConstraints cons;
            switch (btnLabel[i]) {
                case "Up":
                    cons = getConstraint(2, 2);
                    break;
                case "Down":
                    cons = getConstraint(3, 2);
                    break;
                case "Left":
                    cons = getConstraint(3, 1);
                    break;
                default:
                    cons = getConstraint(3, 3);
            }

            add(btnList[i], cons);
        }

        GridBagConstraints resultCons = getConstraint(4, 1);
        resultCons.gridwidth = 3;
        add(labelResult, resultCons);

    }

    private @NotNull GridBagConstraints getConstraint(int y, int x) {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridy = y;
        cons.gridx = x;
        return cons;
    }

    public boolean addSelectDir(Direction dir) {
        selectedDirs.add(dir);
        String curText = labelResult.getText();
        switch (dir) {
            case LEFT:
                curText += "L";
                break;
            case RIGHT:
                curText += "R";
                break;
            case UP:
                curText += "U";
                break;
            case DOWN:
                curText += "D";
        }
        labelResult.setText(curText);
        updateUI();

        if (selectedDirs.size() >= diceResult - penalty)
            return true;
        return false;
    }

    public JButton getButton(ButtonType type) {
        switch (type) {
            case UP:
                return btnList[0];
            case DOWN:
                return btnList[1];
            case LEFT:
                return btnList[2];
            case RIGHT:
                return btnList[3];
            default:
                return null;
        }
    }

    public enum ButtonType {
        UP, DOWN, LEFT, RIGHT
    }

    public ArrayList<Direction> getSelectedDirs() {
        return this.selectedDirs;
    }
}
