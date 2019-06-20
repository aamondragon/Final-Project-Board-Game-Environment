package Othello;
import Exceptions.InvalidCellException;
import GameEnv.Board;
import GameEnv.Cell;
import GameEnv.Piece;
import GameEnv.Player;


public class OthelloBoard extends Board{

	public OthelloBoard(int x, int y) {
		super(x, y);
		fillBoard();
	}

	protected void fillBoard() {
		for(int i = 0; i < this.getWidth(); ++i) {
			for(int j = 0; j < this.getHeight(); ++j) {
				this.board[i][j] = new Cell(i, j, null);
			}
		}
	}
}
