//Kale Cassidy and Tessa Haws
package tests;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	private TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() {
		TestBoardCell cell = board.getCell(1, 1);
		Set<TestBoardCell> testList = cell.getAdjList();
		assert(!testList.contains(board.getCell(0, 0)));
		assert(testList.contains(board.getCell(1, 0)));
		assert(testList.contains(board.getCell(0, 1)));
		assert(testList.contains(board.getCell(2, 1)));
		assert(testList.contains(board.getCell(1, 2)));
	}
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assert(6 == targets.size());
		assert(targets.contains(board.getCell(0, 0)));
		assert(targets.contains(board.getCell(1, 0)));
		assert(targets.contains(board.getCell(2, 0)));
		assert(targets.contains(board.getCell(1, 1)));
		assert(targets.contains(board.getCell(0, 2)));
		assert(!targets.contains(board.getCell(2, 1)));
	}
	@Test
	public void testTargetsRoom() {
		board.getCell(2, 0).setRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assert(6 == targets.size());
		assert(targets.contains(board.getCell(2, 0)));
	}
	@Test
	public void testTargetsOccupied() {
		board.getCell(1, 0).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assert(4 == targets.size());
		assert(targets.contains(board.getCell(1, 1)));
	}
	@Test
	public void testTargetsMixed() {
		board.getCell(1, 0).setOccupied(true);
		board.getCell(1, 1).setRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assert(4 == targets.size());
		assert(targets.contains(board.getCell(1, 1)));
	}
}
