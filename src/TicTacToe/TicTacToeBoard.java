package TicTacToe;

import GameEnv.Board;
import GameEnv.Cell;
import GameEnv.Piece;

public class TicTacToeBoard extends Board {

    TicTacToeBoard(int width, int height) {
        super(width, height);
        fillBoard();
    }

    @Override
    protected void fillBoard() {
        for(int i = 0; i < this.width; ++i) {
            for(int j = 0; j < this.height; ++j) {
                this.board[i][j] = new Cell(i, j, new Piece("N", null));
            }
        }
    }

}
