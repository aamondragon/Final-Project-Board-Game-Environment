package GameEnv;
public class Player {
    private String name = "";
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getPlayer() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
 }
