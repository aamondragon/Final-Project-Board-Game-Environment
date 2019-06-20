package GameEnv;

import EelsAndEscalator.EelsAndEscalatorGameState;
import Enums.GameType;
import Exceptions.InvalidCellException;
import Memory.MemoryGameState;
import Othello.OthelloGameState;
import TicTacToe.TicTacToeGameState;

public class GameStateFactory {

	public GameState createGameState(GameType type, String player1, String player2) throws InvalidCellException {
		switch (type) {
			case TIC_TAC_TOE:
				return new TicTacToeGameState(new Player(player1), new Player(player2));
			case EELS_AND_ESCALATORS:
				return new EelsAndEscalatorGameState(new Player(player1), new Player(player2));
			case MEMORY:
				return new MemoryGameState(new Player(player1), new Player(player2));
			case OTHELLO:
				return new OthelloGameState(new Player(player1), new Player(player2));
			default:
				return null;
		}
	}
}