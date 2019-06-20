package Memory;

import Enums.ShapeType;
import GameEnv.Cell;
import GameEnv.Piece;

public class MemoryCell extends Cell {

    private ShapeType shapeType;
    private boolean hidden;

    public MemoryCell(int x, int y, Piece p, ShapeType shapeType) {
        super(x, y, p);
        this.shapeType = shapeType;
        this.hidden = true;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void revealCell() {
        this.hidden = false;
    }

    public void hideCell() {
        this.hidden = true;
    }

}
