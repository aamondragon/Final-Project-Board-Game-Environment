package GameEnv;

public class CoordMove extends Move{

    private int x_coord;
    private int y_coord;

    public CoordMove(int x_coord, int y_coord) {
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    public int getX_coord() {
        return x_coord;
    }

    public int getY_coord() {
        return y_coord;
    }

}
