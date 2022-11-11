//Kale Cassidy and Tessa Haws
package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	static final int ROWS = 4;
	static final int COLS = 4;
	
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard(){
		this.grid = new TestBoardCell[ROWS][COLS];
		this.targets = new HashSet<TestBoardCell>();
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		
		//Add existing spaces in grid to adjacentList if in bounds and cardinally adjacent
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if(i > 0) {
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if(i < ROWS - 1) {
					grid[i][j].addAdjacency(grid[i+1][j]);
				}
				if(j > 0) {
					grid[i][j].addAdjacency(grid[i][j-1]);
				}
				if(j < COLS - 1) {
					grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
	}
	
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
		visited = new HashSet<TestBoardCell>();
		
		//Can stay in Cell and have visited cell we are currently at
		targets.add(startCell);
		visited.add(startCell);
		//Set for cells we are currently checking for surrounding open spaces
		Set<TestBoardCell> currentCheck = new HashSet<TestBoardCell>();
		//Set for cells we are will check for surrounding open spaces
		Set<TestBoardCell> nextCheck = new HashSet<TestBoardCell>();
		
		//Add start cell to check first
		currentCheck.add(startCell);
		
		for(int i = 0; i < pathlength; i++) {
			for(TestBoardCell cell: currentCheck) {
				for(TestBoardCell adj: cell.getAdjList()) {
					if(!adj.getOccupied() && !visited.contains(adj)){
						//Add to target visited, and nextCheck if unoccupied and not already visited
						nextCheck.add(adj);
						targets.add(adj);
						visited.add(adj);
					}
				}
			}
			//This sets our current check to our next check and clears nextCheck to be filled
			currentCheck.clear();
			currentCheck.addAll(nextCheck);
			nextCheck.clear();
		}
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
