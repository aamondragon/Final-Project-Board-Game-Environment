package GameEnv;

import Exceptions.InvalidCellException;

public abstract class Board {
	protected Cell[][] board;
	protected int width;
	protected int height;
	
	public Board(int width, int height) {
		this.board = new Cell[width][height];
		this.width = width;
		this.height = height;
	}

	protected abstract void fillBoard();

	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public boolean isValidCell(int x, int y) {
		if(x < 0 || y < 0 || x>=width || y>=height) return false;
		return true;
	}
	
	public void updateCell(int x, int y, Cell newCell) throws InvalidCellException {
		if(!isValidCell(x, y)) throw new InvalidCellException();
		this.board[x][y] = newCell;
	}
	public Cell getCell(int x, int y) throws InvalidCellException {
		if(!isValidCell(x, y)) throw new InvalidCellException();
		return this.board[x][y];
	}
}
