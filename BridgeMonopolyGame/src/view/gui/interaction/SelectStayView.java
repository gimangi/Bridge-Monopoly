package view.gui.interaction;

import javax.swing.*;
import java.awt.*;

public class SelectStayView extends JPanel {

    private final JButton btnStay;
    private final JButton btnMove;

    private boolean selected;

    public SelectStayView(int playerId) {
        setLayout(new FlowLayout());
        setSize(new Dimension(100, 400));

        JLabel text = new JLabel("Player " + playerId + " 님, 턴을 쉬어갈지, 주사위를 굴릴지 결정하세요.");
        btnStay = new JButton("쉬어가기");
        btnMove = new JButton("주사위 굴리기");

        add(text);
        add(btnStay);
        add(btnMove);
    }

    public boolean getSelected() {
        return this.selected;
    }

    public JButton getBtnStay() {
        return this.btnStay;
    }

    public JButton getBtnMove() {
        return this.btnMove;
    }


}
