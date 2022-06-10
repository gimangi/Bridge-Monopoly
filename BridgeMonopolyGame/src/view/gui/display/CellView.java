package view.gui.display;

import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class CellView extends JPanel {

    public final static int PIECE_WIDTH = 15;
    public final static int PIECE_HEIGHT = 15;

    public final static int CELL_WIDTH = 50;
    public final static int CELL_HEIGHT = 50;

    private final static String IC_CELL_START = "ic_cell_start.png";
    private final static String IC_CELL_END = "ic_cell_end.png";
    private final static String IC_CELL_EMPTY = "ic_cell_empty.png";
    private final static String IC_CELL_HAMMER = "ic_cell_hammer.png";
    private final static String IC_CELL_SAW = "ic_cell_saw.png";
    private final static String IC_CELL_DRIVER = "ic_cell_driver.png";
    private final static String IC_CELL_BRIDGE_START = "ic_cell_bridge_start.png";
    private final static String IC_BRIDGE = "ic_bridge.png";
    private final static String IC_EMPTY = "ic_empty.png";

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

        ImageIcon imageIcon = new ImageIcon(getURL(resource));
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);

        g.drawImage(imageIcon.getImage(), 0, 0,   null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void putPiece(@NotNull int id) {
        PieceView pieceView = new PieceView(id, PIECE_WIDTH, PIECE_HEIGHT);
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

    private URL getURL(String fileName) {
        return this.getClass().getClassLoader().getResource(fileName);
    }
}
