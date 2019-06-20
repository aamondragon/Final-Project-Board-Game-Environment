package GameEnv;

import EelsAndEscalator.EelsAndEscalatorDriver;
import Enums.GameType;
import Memory.MemoryDriver;
import Othello.OthelloDriver;
import TicTacToe.TicTacToeDriver;

public class DriverFactory {

    public Driver createDriver(GameState state, GameType type) {
        switch (type) {
            case TIC_TAC_TOE:
            		return new TicTacToeDriver();
            case EELS_AND_ESCALATORS:
            		return new EelsAndEscalatorDriver();
            case MEMORY:
            		return new MemoryDriver();
            case OTHELLO:
            		return new OthelloDriver();
            default:
            		return null;
        }
    }
}
