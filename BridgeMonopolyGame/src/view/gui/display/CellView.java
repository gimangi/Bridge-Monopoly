package view.gui.display;

import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CellView extends JPanel {

    public final static int PIECE_WIDTH = 15;
    public final static int PIECE_HEIGHT = 15;

    public final static int CELL_WIDTH = 50;
    public final static int CELL_HEIGHT = 50;

    private final static String DIR_RES = "." + File.separator + "res" + File.separator;

    private final static String IC_CELL_START = DIR_RES + "ic_cell_start.png";
    private final static String IC_CELL_END = DIR_RES + "ic_cell_end.png";
    private final static String IC_CELL_EMPTY = DIR_RES + "ic_cell_empty.png";
    private final static String IC_CELL_HAMMER = DIR_RES + "ic_cell_hammer.png";
    private final static String IC_CELL_SAW = DIR_RES + "ic_cell_saw.png";
    private final static String IC_CELL_DRIVER = DIR_RES + "ic_cell_driver.png";
    private final static String IC_CELL_BRIDGE_START = DIR_RES + "ic_cell_bridge_start.png";
    private final static String IC_BRIDGE = DIR_RES + "ic_bridge.png";
    private final static String IC_EMPTY = DIR_RES + "ic_empty.png";

    private final ArrayList<PieceView> pieceViewList = new ArrayList();

    private final Cell cell;

    public CellView(Cell cell) {
        this.cell = cell;
        setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        setLayout(new GridLayout(2, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        String resource = "";

        if (cell == null)
            resource = IC_EMPTY;
        else if (cell instanceof ItemCell) {
            switch (((ItemCell) cell).getItemType()) {
                case START:
                    resource = IC_CELL_START;
                    break;
                case END:
                    resource = IC_CELL_END;
                    break;
                case EMPTY:
                    resource = IC_CELL_EMPTY;
                    break;
                case HAMMER:
                    resource = IC_CELL_HAMMER;
                    break;
                case SAW:
                    resource = IC_CELL_SAW;
                    break;
                case PHILIPS_DRIVER:
                    resource = IC_CELL_DRIVER;
            }
        }
        else if (cell instanceof BridgeCell) {
            switch (((BridgeCell) cell).getBridgeType()) {
                case START:
                    resource = IC_CELL_BRIDGE_START;
                    break;
                case END:
                    resource = IC_CELL_EMPTY;
                    break;
                case BRIDGE:
                    resource = IC_BRIDGE;
            }
        }

        ImageIcon imageIcon = new ImageIcon(resource);
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);

        g.drawImage(imageIcon.getImage(), 0, 0,   null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void putPiece(@NotNull Player player) {
        PieceView pieceView = new PieceView(player.getId(), PIECE_WIDTH, PIECE_HEIGHT);
        pieceViewList.add(pieceView);
        add(pieceView);
        updateUI();
    }

    public void clearPiece() {
        removeAll();
    }

    public Cell getCell() {
        return this.cell;
    }
}