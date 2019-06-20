package EelsAndEscalator;

import Enums.EECellType;
import GameEnv.Board;
import GameEnv.Piece;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class EelsAndEscalatorBoard extends Board {


    private HashMap<Integer, List<Integer>> eelMap = new HashMap<Integer, List<Integer>>(){{ put(17, Arrays.asList(9,6));
        put(54, Arrays.asList(6,6)); put(62, Arrays.asList(8,1)); put(64, Arrays.asList(4,0)); put(87, Arrays.asList(6,4));
        put(93, Arrays.asList(2,7)); put(95, Arrays.asList(2, 5)); put(98, Arrays.asList(2, 1));}};
    private HashMap<Integer, List<Integer>> escalatorMap = new HashMap<Integer, List<Integer>>() {{ put(2, Arrays.asList(6,3));
        put(4, Arrays.asList(8,6)); put(9, Arrays.asList(6,9)); put(21, Arrays.asList(5,1)); put(28, Arrays.asList(1,3));
        put(51, Arrays.asList(3,6)); put(72, Arrays.asList(0,9)); put(80, Arrays.asList(0,1));}};

    public EelsAndEscalatorBoard(int width, int height) {
        super(width, height);

        fillBoard();
    }

    @Override
    protected void fillBoard() {
        int counter = 100;
        this.board[0][0] = new EelsAndEscalatorsCell(0, 0, null, EECellType.END, null);
        for (int i = 0; i < width; i++) {
            if (i%2==0) {
                for (int j = 0; j < height; j++) {
                    if (eelMap.containsKey(counter)) {
                        int [] endCell = {eelMap.get(counter).get(0), eelMap.get(counter).get(1)};
                        this.board[i][j] = new EelsAndEscalatorsCell(i, j, new Piece("N", null), EECellType.EEL, endCell);
                    } else if (escalatorMap.containsKey(counter)) {
                        int [] endCell = {escalatorMap.get(counter).get(0), escalatorMap.get(counter).get(1)};
                        this.board[i][j] = new EelsAndEscalatorsCell(i, j,  new Piece("N", null), EECellType.ESCALATOR, endCell);
                    } else {
                        this.board[i][j] = new EelsAndEscalatorsCell(i,j,  new Piece("N", null), EECellType.EMPTY, null);
                    }
                    counter--;
                }
                counter-=9;
            }
            else {
                for (int j = 0; j < height; j++) {
                    if (eelMap.containsKey(counter)) {
                        int[] endCell = {eelMap.get(counter).get(0), eelMap.get(counter).get(1)};
                        this.board[i][j] = new EelsAndEscalatorsCell(i, j,  new Piece("N", null), EECellType.EEL, endCell);
                    } else if (escalatorMap.containsKey(counter)) {
                        int[] endCell = {escalatorMap.get(counter).get(0), escalatorMap.get(counter).get(1)};
                        this.board[i][j] = new EelsAndEscalatorsCell(i, j,  new Piece("N", null), EECellType.ESCALATOR, endCell);
                    } else {
                        this.board[i][j] = new EelsAndEscalatorsCell(i, j,  new Piece("N", null), EECellType.EMPTY, null);
                    }
                    counter++;
                }
                counter-=11;
            }
        }
    }
}
