package clueGame;

public class Room {
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Boolean isTraversable;
	private char hasSecretPassage;
	

	Room(){
		super();
	}
	//When initializing Room, set room name to string and default cases
	public Room(String string) {
		this.name = string;
		this.isTraversable = false;
		this.hasSecretPassage = '0';
	}
	//Setter for name
	public String getName() {
		return name;
	}
	//Getter and setter for labelCell
	public BoardCell getLabelCell() {
		return labelCell;
	}
	public void setLabelCell(BoardCell bc) {
		this.labelCell = bc;
	}
	//Getter and setter for centerCell
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public void setCenterCell(BoardCell bc) {
		this.centerCell = bc;
	}
	//Getter and setter for isTraversable
	public Boolean getTraversable() {
		return isTraversable;
	}
	public void setTraversable() {
		this.isTraversable = true;
	}
	//Getter and setter for secretPassage
	public char getHasSecretPassage() {
		return hasSecretPassage;
	}
	public void setHasSecretPassage(char hasSecretPassage) {
		this.hasSecretPassage = hasSecretPassage;
	}
}
