//Kale Cassidy and Tessa Haws
package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell{
	
	private int row, column;
	private Set<TestBoardCell> adjList;
	private Boolean isRoom, isOccupied;
	
	TestBoardCell(int row, int column){
		this.row = row;
		this.column = column;
		this.isRoom = false;
		this.isOccupied = false;
		this.adjList = new HashSet<TestBoardCell>();
	}
	
	void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList(){
		return adjList;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	boolean getRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	boolean getOccupied() {
		return isOccupied;
	}
}
