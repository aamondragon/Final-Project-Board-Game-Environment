package EelsAndEscalator;

import Enums.EECellType;
import Exceptions.InvalidCellException;
import GameEnv.*;

import java.util.Random;


public class EelsAndEscalatorGameState extends GameState implements Dice {

    private Random randomGenerator;
    private int[] playerOneLocation;
    private int[] playerTwoLocation;
    private int roll;

    public EelsAndEscalatorGameState (Player playerOne, Player playerTwo) throws InvalidCellException {
        this.initializeBoard();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.turn = playerOne;

        randomGenerator = new Random();
        playerOneLocation = new int[]{9, 0};
        playerTwoLocation = new int[]{9, 0};

        this.board.updateCell(9,0, new EelsAndEscalatorsCell(9, 0, new Piece("1", playerOne),
                EECellType.EMPTY, null) );
        this.board.updateCell(9,0, new EelsAndEscalatorsCell(9, 0, new Piece("2", playerTwo),
                EECellType.EMPTY, null) );
    }

    @Override
    public void initializeBoard() {
        int width = 10;
        int height = 10;

        this.board = new EelsAndEscalatorBoard(width, height);
    }

    @Override
    public void makeMove(Move mv) throws InvalidCellException {
        //GUI -> rollDice() -> create new Counter move object -> pass object as param
        //In Controller call isValidMove if true -> call makeMove
        // To win must roll exact #
        // If roll number > exact move up then back
        // Land on Escalator/Eel go to that sot
        if (this.turn == this.playerOne) {
            //need 2 update cells, one to remove data of current position & one to add the new one
            makeMoveHelper(mv, this.playerOne, playerOneLocation, "1");
        }
        else {
            //need 2 update cells, one to remove data of current position & one to add the new one
            makeMoveHelper(mv, this.playerTwo, playerTwoLocation, "2");
        }
        switchTurn();
    }

    private void makeMoveHelper(Move mv, Player player, int[] playerLocation, String type) throws InvalidCellException {
        int [] newPosition = getPositionFromMove(((CounterMove) mv).getSteps(), playerLocation);
        EelsAndEscalatorsCell newCell = (EelsAndEscalatorsCell) this.board.getCell(newPosition[0], newPosition[1]);
        //If user lands on Eels or Escalator cell
        if (playerLocation[0] == 0 && playerLocation[1] <= 6) {
            if (Math.abs(playerLocation[1] - ((CounterMove) mv).getSteps()) == 0) {
                board.updateCell(0, 0, new EelsAndEscalatorsCell(0, 0, new Piece(type, player),
                        EECellType.END, null));
                clearCell(board.getCell(playerLocation[0], playerLocation[1]));
                playerLocation[0] = 0;
                playerLocation[1] = 0;
                this.gameOver = true;
            } else if (newPosition[0] == 0 && newPosition[1] == 2 || newPosition[1] == 5){
                int newMove = Math.abs(playerLocation[1] - ((CounterMove) mv).getSteps());
                EelsAndEscalatorsCell nCell = (EelsAndEscalatorsCell) this.board.getCell(0, newMove);
                board.updateCell(nCell.getConnected()[0], nCell.getConnected()[1],
                        new EelsAndEscalatorsCell(nCell.getConnected()[0], nCell.getConnected()[1],
                                new Piece(type, player), EECellType.EMPTY, null));
                clearCell(board.getCell(playerLocation[0], playerLocation[1]));
                playerLocation[0] = nCell.getConnected()[0];
                playerLocation[1] = nCell.getConnected()[1];
            } else {
                int newMove = Math.abs(playerLocation[1] - ((CounterMove) mv).getSteps());
                EelsAndEscalatorsCell nCell = (EelsAndEscalatorsCell) this.board.getCell(0, newMove);
                board.updateCell(0, newMove, new EelsAndEscalatorsCell(0, newMove,
                        new Piece(type, player), nCell.getEeCellType(), nCell.getConnected()));
                if (playerLocation[1] != newMove) {
                    clearCell(board.getCell(playerLocation[0], playerLocation[1]));
                }
                playerLocation[0] = 0;
                playerLocation[1] = newMove;
            }
        }else if (eECellType(this.board.getCell(newPosition[0], newPosition[1]))==EECellType.EEL ||
                eECellType(this.board.getCell(newPosition[0], newPosition[1]))==EECellType.ESCALATOR) {
            board.updateCell(newCell.getConnected()[0], newCell.getConnected()[1],
                    new EelsAndEscalatorsCell(newCell.getConnected()[0], newCell.getConnected()[1],
                            new Piece(type, player), EECellType.EMPTY, null));
            clearCell(board.getCell(playerLocation[0], playerLocation[1]));
            playerLocation[0] = newCell.getConnected()[0];
            playerLocation[1] = newCell.getConnected()[1];
        } else if(eECellType(board.getCell(newPosition[0], newPosition[1])) == EECellType.EMPTY) {
            board.updateCell(newCell.getX(), newCell.getY(), new EelsAndEscalatorsCell(newCell.getX(), newCell.getY(),
                    new Piece(type, player), EECellType.EMPTY, null));
            clearCell(board.getCell(playerLocation[0], playerLocation[1]));
            playerLocation[0] = newPosition[0];
            playerLocation[1] = newPosition[1];
            //if user is at last 6 spots (0, 6)
        }
    }

    private void clearCell(Cell eECell) throws InvalidCellException {
        if (playerOneLocation[0] == playerTwoLocation[0] && playerOneLocation[1] == playerTwoLocation[1]) {
            if (this.turn == playerOne) {
                board.updateCell(playerTwoLocation[0], playerTwoLocation[1], new EelsAndEscalatorsCell(playerTwoLocation[0], playerTwoLocation[1],
                        new Piece("2", playerTwo), ((EelsAndEscalatorsCell) eECell).getEeCellType(), ((EelsAndEscalatorsCell) eECell).getConnected()));
            } else {
                board.updateCell(playerOneLocation[0], playerOneLocation[1], new EelsAndEscalatorsCell(playerOneLocation[0], playerOneLocation[1],
                        new Piece("1", playerOne), ((EelsAndEscalatorsCell) eECell).getEeCellType(), ((EelsAndEscalatorsCell) eECell).getConnected()));
            }
        }
        else {
            eECell.setData(new Piece("N", null));
        }
    }

    public boolean isValidMove(Move mv) throws InvalidCellException {
        // If roll lands on another player make roll again
        if (this.turn == this.playerOne) {
            int [] newPosition = getPositionFromMove(((CounterMove) mv).getSteps(), playerOneLocation);
            if (this.board.getCell(newPosition[0], newPosition[1]).getData().getOwner() != playerOne) {
                return false;
            }
        }
        else {
            int [] newPosition = getPositionFromMove(((CounterMove) mv).getSteps(), playerTwoLocation);
            if (this.board.getCell(newPosition[0], newPosition[1]).getData().getOwner() != playerTwo) {
                return false;
            }
        }
        return true;
    }

    private int [] getPositionFromMove(int steps, int [] playerLocation) {
        int row = 0;
        //Direction moving right
        if (playerLocation[0] == 0 && playerLocation[1] <= 6) {
            steps = Math.abs(playerLocation[1] - steps);
        }

        else if (playerLocation[0]%2!=0) {
            if (steps > 9 - playerLocation[1]) {
                row = playerLocation[0]-1;
                steps = 10 - (steps - (9 - playerLocation[1]));
            }
            else if (steps <= 9 - playerLocation[1]) {
                row = playerLocation[0];
                steps = playerLocation[1] + steps;
            }
        }
        //Direction moving left
        else {
            if (steps > playerLocation[1]) {
                row = playerLocation[0]-1;
                steps = steps - playerLocation[1] - 1;
            }
            else if (steps <= playerLocation[1]) {
                row = playerLocation[0];
                steps = playerLocation[1] - steps;
            }
        }
        return new int[]{row, steps};
    }

    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    @Override
    public Player getWinner() {
        try {
            return this.board.getCell(0,0).getData().getOwner();
        } catch (InvalidCellException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void switchTurn() {
        if(this.turn == this.playerOne) this.turn = this.playerTwo;
        else this.turn = this.playerOne;
    }

    public int rollDice() {
        this.roll = randomGenerator.nextInt(6) +1;
        return roll;
    }

    public int getRoll() {
        return this.roll;
    }

    public EECellType eECellType(Cell cell) {
        return ((EelsAndEscalatorsCell) cell).getEeCellType();
    }

    //used for testing
    public void printBoard() throws InvalidCellException {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board.getCell(i,j).getData().getType() + " ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

}