package Othello;
import java.util.ArrayList;

import Exceptions.InvalidCellException;
import Exceptions.InvalidMoveException;
import GameEnv.*;

public class OthelloGameState extends GameState{

	private static final CoordMove[] ALL_DIRECTIONS = new CoordMove[]{
            new CoordMove(1, 0),
            new CoordMove(1, 1),
            new CoordMove(0, 1),
            new CoordMove(-1, 1),
            new CoordMove(-1, 0),
            new CoordMove(-1, -1),
            new CoordMove(0, -1),
            new CoordMove(1, -1),
    };
	
	public OthelloGameState(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.turn = playerTwo;
		this.gameOver = false;
		this.initializeBoard();
	}

	@Override
	protected void initializeBoard() {
		this.board = new OthelloBoard(8, 8);
		
		try {
			this.board.getCell(3, 3).setData(new Piece("Black", this.playerOne));
			this.board.getCell(4, 3).setData(new Piece("White", this.playerTwo));
			this.board.getCell(3, 4).setData(new Piece("White", this.playerTwo));
			this.board.getCell(4, 4).setData(new Piece("Black", this.playerOne));
		} catch (InvalidCellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return this.gameOver;
	}
	
	@Override
	public void makeMove(Move mv) throws InvalidMoveException, InvalidCellException {
		// TODO Auto-generated method stub
		if(!(mv instanceof CoordMove)) {
			throw new InvalidMoveException();
		}
		if(!isValidMove(mv) || !canFlipPiece(((CoordMove) mv))) {
			throw new InvalidMoveException();
		}
		CoordMove cmv = ((CoordMove) mv);
		
		try {
			if(this.turn == this.playerOne) {
				this.board.getCell(cmv.getX_coord(), cmv.getY_coord()).setData(new Piece("Black", this.playerOne));
			} else {
				this.board.getCell(cmv.getX_coord(), cmv.getY_coord()).setData(new Piece("White", this.playerTwo));
			}
			
			for(CoordMove step : this.ALL_DIRECTIONS) {
				flipForDirection(cmv, step);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// TODO SWITCH TURN LOGIC
		this.switchTurn();
		if(!hasValidMove()) {
			this.switchTurn();
			if(!hasValidMove()) {
				this.playerOne.setScore(this.getScore(this.playerOne));
				this.playerTwo.setScore(this.getScore(this.playerTwo));
				this.gameOver = true;
			}
		}
		
	}
	
	@Override
	public int getScore(Player player) {
		int counter = 0;
		
		try {
			for(int x = 0; x < this.board.getWidth(); x++) {
				for(int y = 0; y < this.board.getHeight(); y++) {
					
					if (this.board.getCell(x, y).getData() == null) continue;
					
					if(this.board.getCell(x, y).getData().getOwner() == player) {
						counter++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return counter;
	}
	
	private boolean hasValidMove() {
		for(int x = 0; x < this.board.getWidth(); x++) {
			for(int y = 0; y < this.board.getHeight(); y++) {
				if(this.isValidMove(new CoordMove(x, y))) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void flipForDirection(CoordMove mv, CoordMove step) {
		boolean hasOtherPiece = false;
		boolean endsWithMine = false;
		
		ArrayList<CoordMove> flipList = new ArrayList<CoordMove>();
		
		for(int x = mv.getX_coord() + step.getX_coord(), y = mv.getY_coord() + step.getY_coord();
				isValidCell(x, y);
				x += step.getX_coord(), y += step.getY_coord()) {
			try {
				if(this.board.getCell(x, y).getData() == null) {
					break;
				} else if (this.board.getCell(x, y).getData().getOwner() != this.turn) {
					hasOtherPiece = true;
					flipList.add(new CoordMove(x, y));
				} else if (this.board.getCell(x, y).getData().getOwner() == this.turn) {
					endsWithMine = true;
					break;
				}
			} catch (InvalidCellException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			if(hasOtherPiece && endsWithMine) {
				for(CoordMove cmv : flipList) {
					if(this.turn == this.playerOne) {
						this.board.getCell(cmv.getX_coord(), cmv.getY_coord()).setData(new Piece("Black", this.playerOne));
					} else {
						this.board.getCell(cmv.getX_coord(), cmv.getY_coord()).setData(new Piece("White", this.playerTwo));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean canFlipPiece(CoordMove mv) {
		for(CoordMove step : this.ALL_DIRECTIONS) {
			if(canFlipForDirection(mv, step)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean canFlipForDirection(CoordMove mv, CoordMove step){
		boolean hasOtherPiece = false;
		boolean endsWithMine = false;
		for(int x = mv.getX_coord() + step.getX_coord(), y = mv.getY_coord() + step.getY_coord();
				isValidCell(x, y);
				x += step.getX_coord(), y += step.getY_coord()) {
			try {
				if(this.board.getCell(x, y).getData() == null) {
					break;
				} else if (this.board.getCell(x, y).getData().getOwner() != this.turn) {
					hasOtherPiece = true;
				} else if (this.board.getCell(x, y).getData().getOwner() == this.turn) {
					endsWithMine = true;
					break;
				}
			} catch (InvalidCellException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return hasOtherPiece && endsWithMine;
	}
	
	private boolean isValidCell(int x, int y) {
		return x>=0 && y>=0 && x<8 && y<8;
	}

	@Override
	public boolean isValidMove(Move mv) {
		if(!(mv instanceof CoordMove)) {
			return false;
		}
		
		int x = ((CoordMove) mv).getX_coord();
		int y = ((CoordMove) mv).getY_coord();
		
		if(x < 0 || y < 0 || x >=8 || y >= 8) {
			return false;
		}
		try {
			if(this.board.getCell(x, y).getData() != null) return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return canFlipPiece(((CoordMove) mv));
	}

}
