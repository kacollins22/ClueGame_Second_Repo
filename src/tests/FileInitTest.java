package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTest {
	/*
	ensure your layout and setup files are loaded correctly (correct number of rooms, test several entries including first and last in file)
	ensure the correct number of rows/columns have been read
	verify at least one doorway in each direction. Also verify cells that don't contain doorways return false for isDoorway().
	check that the correct number of doors have been loaded.
	check some of the cells to ensure they have the correct initial
	check that rooms have the proper center cell and label cell.
	implement @BeforeEach method or @BeforeAll
	*/
	
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 25;

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testFileLoaded() {
		BoardCell cell = board.getCell(0, 0);
		Room room = board.getRoom( cell ) ;
		assert( room != null );
		
		BoardCell cell2 = board.getCell(20, 24);
		Room room2 = board.getRoom( cell2 ) ;
		assert( room2 != null );
		
		int numRooms = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell testCell = board.getCell(row, col);
				//Changed from cell to testCell as cell doesn't test what we need
				if (testCell.isRoomCenter())
					numRooms++;
			}
		Assert.assertEquals(9, numRooms);
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	@Test
	public void FourDoorDirections() {
		//Changed from (8,1) to (6,1) because doorway position was incorrectly set in test
		BoardCell cell = board.getCell(6, 1);
		assert(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCell(14, 12);
		assert(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCell(9, 8);
		assert(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(1, 7);
		assert(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCell(6, 6);
		assert(!cell.isDoorway());
	}
	
	@Test
	public void testNumberOfDoors() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		//Changed to 19 as there is 19 doors, not 16.
		assertEquals(19, numDoors);
	}
	
	@Test
	public void testInitials() {
		BoardCell cell = board.getCell(10, 5);
		assertEquals('A', cell.getInitial());
		
		cell = board.getCell(1, 20);
		assertEquals('O', cell.getInitial());
		
		cell = board.getCell(15, 20);
		assertEquals('H', cell.getInitial());
		
		cell = board.getCell(10, 10);
		assertEquals('X', cell.getInitial());
		
		cell = board.getCell(10, 15);
		assertEquals('W', cell.getInitial());
	}
	
	@Test
	public void testCenterLabel() {
		int numCenter = 0;
		int numLabel = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell testCell = board.getCell(row, col);
				Room room = board.getRoom( testCell ) ;
				if(room.getCenterCell() == testCell) {
					numCenter++;
				}
				if(room.getLabelCell() == testCell) {
					numLabel++;
				}
			}
		assertEquals(9, numCenter);
		assertEquals(9, numLabel);
	}

}
