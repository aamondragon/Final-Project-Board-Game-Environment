package GameEnv;

import javax.swing.JPanel;

import Othello.OthelloGameState;

public abstract class Driver {

	public Driver() {}
	public abstract void drawBoard(JPanel canvas, GameState gs);
	public abstract void handleClick(JPanel canvas, GameState gs, double x, double y);

}

