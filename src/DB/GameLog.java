package DB;

public class GameLog{
	private int won;

	public GameLog(int w) {
		this.won = w;
	}

	public int getWon() {
		return this.won;
	}

	public void incWon() {
		this.won++;
	}
}