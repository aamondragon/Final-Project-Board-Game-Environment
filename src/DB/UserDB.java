package DB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class UserDB {

	private File userDB;
	private ArrayList<User> db;

	public UserDB() {
		this.userDB = new File("UserDB.txt");
		this.db = new ArrayList<User>();
		try {
			this.userDB.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] getUserNames() {
		String[] list = new String[this.db.size()];
		for(int i = 0; i < list.length; ++i) {
			list[i] = db.get(i).getName();
		}
		return list;
	}

	public void loadDB() {

		this.db = new ArrayList<User>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(this.userDB));
			String st;
			while((st = br.readLine()) != null) {

				String[] segments = st.split("=>");

				User usr = new User(segments[0], new HashMap<String, GameLog>());

				for(int i = 1; i < segments.length; ++i) {
					String[] gameS = segments[i].split(",");

					String gameName = gameS[0];
					GameLog gl = new GameLog(Integer.parseInt(gameS[1]));

					usr.getStatus().put(gameName, gl);
				}
				this.db.add(usr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveDB() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.userDB));
			for(User usr : this.db) {
				String line = usr.toString();
				writer.write(line + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String printDB(String forGame){
		String result = new String("Leaderboard for " + forGame + "\n====================\n");
		for(User usr : this.db){
			result += ("# " + usr.getName() + "\n");
			if(usr.getStatus().containsKey(forGame)){
				result += "    => " + usr.getStatus().get(forGame).getWon() + " games won\n";
			} else {
				result += "    => No games played...\n";
			}
		}
		return result;
	}

	public User findUser(String name) {
		for(User usr : this.db) {
			if(usr.getName().equals(name)) {
				return usr;
			}
		}
		return null;
	}

	public void addUser(String name) throws Exception {
		if(findUser(name) != null) {
			throw new Exception();
		}
		User usr = new User(name, new HashMap<String, GameLog>());
		this.db.add(usr);
		saveDB();
	}
}
