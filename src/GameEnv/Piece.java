package GameEnv;

public class Piece {
	private String type;
	private Player owner;
	
	public Piece(String type, Player owner){
		this.type = type;
		this.owner = owner;
	}
	
	public String getType() {
		return this.type;
	}
	public Player getOwner() {
		return this.owner;
	}
}
