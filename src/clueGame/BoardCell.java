package clueGame;

import java.util.Set;
import java.util.HashSet;

public class BoardCell {
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	private Boolean isOccupied;
	private Boolean isRoom;

	//On BoardCell initialize, set default case  and initialize adjList
	BoardCell(){
		super();
		this.roomLabel = false;
		this.roomCenter = false;
		this.isOccupied = false;
		this.isRoom = false;
		this.secretPassage = '0';
		adjList = new HashSet<BoardCell>();
	}
	
	//Set cell row, col
	public void setRowCol(int row, int col) {
		this.row = row;
		this.col = col;
	}
	//Getter and setter for cell initial
	public void setInitial(char c) {
		this.initial = c;
	}
	public char getInitial() {
		return initial;
	}
	//Add adjacentcies for target finding
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	//Return adjList for testing and target finding
	public Set<BoardCell> getAdj(){
		return adjList;
	}
	//Getter and Setter for Door Direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public void setDoorDirection(DoorDirection dd) {
		this.doorDirection = dd;
	}
	//Checker for doorway status
	public Boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}
	//Getter and setter for cell label
	public boolean isLabel() {
		return roomLabel;
	}
	public void setLabel() {
		this.roomLabel = true;
	}
	//Getter and setter for room center
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public void setRoomCenter() {
		this.roomCenter = true;
	}
	//Getter and setter for secret passage
	public char getSecretPassage() {
		return secretPassage;
	}
	public void setSecretPassage(char sp) {
		this.secretPassage = sp;
	}
	//Getter and setter for occupied
	public Boolean getOccupied() {
		return isOccupied;
	}
	public void setOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	//Getter and setter for room status
	public Boolean getRoom() {
		return isRoom;
	}
	public void setRoom() {
		this.isRoom = true;
	}
}
