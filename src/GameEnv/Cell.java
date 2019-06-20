package GameEnv;

public class Cell {
	protected Piece data;
	protected int x_coord;
	protected int y_coord;
	
	
	public Cell(int x, int y, Piece p){
		this.x_coord = x;
		this.y_coord = y;
		this.data = p;
	}
	
	public Piece getData() {
		return this.data;
	}
	public int getX() {
		return this.x_coord;
	}
	public int getY() {
		return this.y_coord;
	}
	
	public void setData(Piece p) {
		this.data = p;
	}
}
