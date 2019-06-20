package Memory;

import Enums.ShapeType;
import GameEnv.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MemoryBoard extends Board {

    private Random randomGenerator;
    private ArrayList<ShapeType> availableShapes = new ArrayList<>(Arrays.asList(
            ShapeType.RED_JOSH, ShapeType.RED_JOSH,
            ShapeType.YELLOW_JOSH, ShapeType.YELLOW_JOSH,
            ShapeType.PINK_JOSH, ShapeType.PINK_JOSH,
            ShapeType.PURPLE_JOSH, ShapeType.PURPLE_JOSH,
            ShapeType.GREEN_JOSH, ShapeType.GREEN_JOSH,
            ShapeType.DARK_GREEN_JOSH, ShapeType.DARK_GREEN_JOSH,
            ShapeType.ORANGE_JOSH, ShapeType.ORANGE_JOSH,
            ShapeType.LIGHT_BLUE_JOSH, ShapeType.LIGHT_BLUE_JOSH,
            ShapeType.BLUE_JOSH, ShapeType.BLUE_JOSH,
            ShapeType.GREY_JOSH, ShapeType.GREY_JOSH));

    public MemoryBoard(int width, int height) {
        super(width, height);
        randomGenerator = new Random();
        fillBoard();
    }

    @Override
    protected void fillBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int index = 0;
                index = randomGenerator.nextInt(availableShapes.size());
                ShapeType shapeType = availableShapes.get(index);
                this.board[i][j] = new MemoryCell(i,j, null, shapeType);
                availableShapes.remove(index);
            }
        }
    }
}
