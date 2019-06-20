package EelsAndEscalator;

import Enums.EECellType;
import GameEnv.Cell;
import GameEnv.Piece;

public class EelsAndEscalatorsCell extends Cell {

    private EECellType eeCellType;
    private int [] connected;

    public EelsAndEscalatorsCell(int x, int y, Piece p, EECellType eeCellType, int [] connected) {
        super(x, y, p);

        this.eeCellType = eeCellType;
        this.connected = connected;
    }

    public EECellType getEeCellType() {
        return this.eeCellType;
    }
    public int [] getConnected() { return this.connected; }
}
