package view.swing.display;

import model.domain.cell.Cell;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel {

    private final Cell[][] map;

    private final GridBagLayout layout;

    public MapView(final Cell[][] map) {
        this.map = map;

        layout = new GridBagLayout();
        setLayout(layout);

        addCell();
    }

    private void addCell() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                CellView cellView = new CellView(map[i][j]);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = j;
                constraints.gridy = i;
                add(cellView, constraints);
            }
        }
        revalidate();
        repaint();
    }

}
