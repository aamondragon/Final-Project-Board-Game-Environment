package GameEnv;

import Exceptions.InvalidMoveException;
import Exceptions.InvalidCellException;

public abstract class GameState {
	protected Board board;
	protected Player turn;
	protected Player playerOne;
	protected Player playerTwo;
	protected boolean gameOver;
	
	public GameState() {
	}

	protected abstract void initializeBoard();
	public abstract void makeMove(Move mv) throws InvalidMoveException, InvalidCellException;
	public abstract boolean isGameOver();
	public abstract boolean isValidMove(Move mv) throws InvalidCellException;
	public Player getPlayer(int i) {
		if(i == 1) return this.playerOne;
		else return this.playerTwo;
	}
	protected void switchTurn() {
		if(this.turn == this.playerOne) this.turn = this.playerTwo;
		else this.turn = this.playerOne;
	}
	public Player getWinner() {
		return (playerOne.getScore() > playerTwo.getScore() ? playerOne : playerTwo);
	}
	public int getScore(Player player) {
		return player.getScore();
	}
	public Board getBoard() {
		return this.board;
	}
	public Player getTurn() {
		return this.turn;
	}
}
