package Memory;

import GameEnv.CoordMove;
import GameEnv.GameState;
import GameEnv.Move;

import java.util.ArrayList;

import Exceptions.InvalidCellException;
import GameEnv.Player;

public class MemoryGameState extends GameState {

    private ArrayList<MemoryCell> tempCell = new ArrayList<>();
    private int freeCell;
    
    public MemoryGameState(Player playerOne, Player playerTwo) {
        this.initializeBoard();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.turn = playerOne;
        this.gameOver = false;
        this.freeCell = 10;
    }

    @Override
    protected void initializeBoard() {
        int width = 5;
        int height = 4;
        this.board = new MemoryBoard(width, height);
    }

    @Override
    public void makeMove(Move mv) {
        MemoryCell temp = getMemCell((CoordMove) mv);
        if(temp != null) {
            tempCell.add(temp);
        }
        if(tempCell.size() == 2) {
            if(tempCell.get(0).getShapeType() == tempCell.get(1).getShapeType()) { //
                this.turn.setScore(getScore(this.turn) + 1);
                tempCell.clear();
                --this.freeCell;
            } else {
                tempCell.get(0).hideCell();
                tempCell.get(1).hideCell();
                tempCell.clear();
                switchTurn();
            }
        }
    }

    public MemoryCell getMemCell(CoordMove move) {
        try {
            MemoryCell mv = (MemoryCell) board.getCell(move.getX_coord(), move.getY_coord());
            if(isValidMove(move)) {
                mv.revealCell();
                return mv;
            }
        } catch (InvalidCellException e) {

        }
        return null;
    }

    @Override
    public boolean isGameOver() {
        if(playerOne.getScore() + playerTwo.getScore() == 10 || this.boardFull()) {
            return true;
        }
        return false;
    }

    @Override
    public Player getWinner() {
        if(playerOne.getScore() > playerTwo.getScore()) {
            return playerOne;
        } else if(playerOne.getScore() == 5 && playerTwo.getScore() == 5) {
            return (new Player("NONE"));
        } else {
            return playerTwo;
        }
     }

    @Override
    public boolean isValidMove(Move mv) {
        Boolean result = false;
        CoordMove coordMove = (CoordMove) mv;
        try {
            if(board.isValidCell(coordMove.getX_coord(), coordMove.getY_coord()) &&
                    ((MemoryCell) board.getCell(coordMove.getX_coord(), coordMove.getY_coord())).getHidden() == true) {
                result = true;
            }
        }
        catch(InvalidCellException e) {

        }
        return result;
    }

    public boolean boardFull() {
        return freeCell == 0;
    }

}
