package DB;

import java.util.HashMap;


public class User{
	private String name;
	private HashMap<String, GameLog> status;

	public User(String n, HashMap<String, GameLog> s) {
		this.name = n;
		this.status = s;
	}

	public String getName() {
		return this.name;
	}

	public HashMap<String, GameLog> getStatus(){
		return this.status;
	}

	public void incGameWon(String game) {
		if(this.status.get(game) == null) {
			this.status.put(game, new GameLog(0));
		}
		this.status.get(game).incWon();
	}

	public String toString() {
		String line = this.getName();

		for(String key : this.getStatus().keySet()) {
			GameLog gl = this.getStatus().get(key);
			line = line + "=>" + key + "," + gl.getWon();
		}
		return line;
	}
}