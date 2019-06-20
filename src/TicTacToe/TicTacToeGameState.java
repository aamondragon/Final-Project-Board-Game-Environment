package TicTacToe;

import Exceptions.InvalidCellException;
import Exceptions.InvalidMoveException;
import GameEnv.*;


public class TicTacToeGameState extends GameState {

    private int freeCells;

    public TicTacToeGameState(Player playerOne, Player playerTwo) {
        this.initializeBoard();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.turn = playerOne;
        this.gameOver = false;
        this.freeCells = 9;
    }

    @Override
    protected void initializeBoard(){
        int width = 3;
        int height = 3;

        this.board = new TicTacToeBoard(width, height);

    }

    @Override
    public void makeMove(Move mv) throws InvalidMoveException {

        if(gameOver) return;

        if(!isValidMove(mv)) throw new InvalidMoveException();

        CoordMove move = (CoordMove) mv;

        try {
            if (this.turn == this.playerOne) {
                board.updateCell(move.getX_coord(), move.getY_coord(), new Cell(move.getX_coord(), move.getY_coord(), new Piece("X", this.playerOne)));
            } else {
                board.updateCell(move.getX_coord(), move.getY_coord(), new Cell(move.getX_coord(), move.getY_coord(), new Piece("O", this.playerTwo)));
            }

            --this.freeCells;

            switchTurn();
        }catch(InvalidCellException e) {
            //should never be thrown?
        }
    }

    @Override
    public boolean isGameOver() {
        if (this.gameOver || this.boardFull() || getWinner() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidMove(Move mv) {
        CoordMove move = (CoordMove) mv;

        if(!board.isValidCell(move.getX_coord(), move.getY_coord())) return false;

        try {
            if (!this.board.getCell(move.getX_coord(), move.getY_coord()).getData().getType().equals("N"))
                return false;
        }catch(InvalidCellException ice) {
            ice.printStackTrace();
        }
        return true;
    }

    @Override
    protected void switchTurn() {
        if(this.turn == this.playerOne) this.turn = this.playerTwo;
        else this.turn = this.playerOne;
    }

    @Override
    public Player getWinner() {

        try {
            //columns and rows
            for (int i = 0; i < this.board.getWidth(); ++i) {


                if (this.board.getCell(i, 0).getData().getType().equals("X") && this.board.getCell(i, 1).getData().getType().equals("X") && this.board.getCell(i, 2).getData().getType().equals("X") ||
                        this.board.getCell(i, 0).getData().getType().equals("O") && this.board.getCell(i, 1).getData().getType().equals("O") && this.board.getCell(i, 2).getData().getType().equals("O")) {

                    this.gameOver = true;
                    return this.board.getCell(i, 0).getData().getOwner();
                } else if (this.board.getCell(0, i).getData().getType().equals("X") && this.board.getCell(1, i).getData().getType().equals("X") && this.board.getCell(2, i).getData().getType().equals("X") ||
                        this.board.getCell(0, i).getData().getType().equals("O") && this.board.getCell(1, i).getData().getType().equals("O") && this.board.getCell(2, i).getData().getType().equals("O")) {

                    this.gameOver = true;
                    return this.board.getCell(0, i).getData().getOwner();
                }
            }
            //diagonals
            if (this.board.getCell(0, 0).getData().getType().equals("X") && this.board.getCell(1, 1).getData().getType().equals("X") && this.board.getCell(2, 2).getData().getType().equals("X") ||
                    this.board.getCell(0, 0).getData().getType().equals("O") && this.board.getCell(1, 1).getData().getType().equals("O") && this.board.getCell(2, 2).getData().getType().equals("O")) {

                this.gameOver = true;
                return this.board.getCell(0, 0).getData().getOwner();
            } else if (this.board.getCell(2, 0).getData().getType().equals("X") && this.board.getCell(1, 1).getData().getType().equals("X") && this.board.getCell(0, 2).getData().getType().equals("X") ||
                    this.board.getCell(2, 0).getData().getType().equals("O") && this.board.getCell(1, 1).getData().getType().equals("O") && this.board.getCell(0, 2).getData().getType().equals("O")) {

                this.gameOver = true;
                return this.board.getCell(2, 0).getData().getOwner();
            }
        }catch(InvalidCellException ice) {
            ice.printStackTrace();
        }


        return (boardFull()) ? new Player("NONE") : null;
    }

    public boolean boardFull() {
        //Used to check in game over for tie game
        return freeCells == 0;
    }

}
