package GameEnv;

public class CounterMove extends Move {

    private int steps;

    public CounterMove(int steps) {
        this.steps = steps;
    }
    public int getSteps() {
        return this.steps;
    }
}
