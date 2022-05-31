package view.swing.display;

import model.domain.cell.Cell;
import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapView extends JPanel {

    private final Cell[][] map;

    private final ArrayList<CellView> cellViewList = new ArrayList();

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
                cellViewList.add(cellView);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = j;
                constraints.gridy = i;
                add(cellView, constraints);
            }
        }
        revalidate();
        repaint();
    }

    public void displayPiece(@NotNull Player player) {
        Cell cell = player.getPiecePosition();

        // put piece
        for (CellView cv : cellViewList) {
            if (cv.getCell() == cell)
                cv.putPiece(player);
        }
        updateUI();
    }

    public void clearPiece() {
        for (CellView cv : cellViewList) {
            cv.clearPiece();
        }
    }

}
