package view.swing;

import model.domain.cell.Cell;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel {

    private final Cell[][] map;

    private final GridLayout layout;

    public MapView(final Cell[][] map) {
        this.map = map;

        int row = map.length;
        int col = map[0].length;

        layout = new GridLayout(row, col);
        layout.setHgap(0);
        layout.setVgap(0);

        setLayout(layout);

        display();
    }

    private void display() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                CellView cellView = new CellView(map[i][j]);
                add(cellView);
                System.out.println(cellView);

            }
        }
        revalidate();
        repaint();
    }

}
