package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			board.initialize();
		}
		
		@Test
		public void testAdjacenciesRooms() {
			//Test Study Space
			Set<BoardCell> testList = board.getAdjList(3, 9);
			assertEquals(2, testList.size());
			assert(testList.contains(board.getCell(1, 7)));
			assert(testList.contains(board.getCell(1, 11)));
			//Test Lecture Hall 2
			testList = board.getAdjList(16, 21);
			assertEquals(3,testList.size());
			assert(testList.contains(board.getCell(19, 16)));
			assert(testList.contains(board.getCell(11, 18)));
			assert(testList.contains(board.getCell(11, 23)));
			//Test Lab with secret passage
			testList = board.getAdjList(10, 3);
			assertEquals(4, testList.size());
			assert(testList.contains(board.getCell(7, 1)));
			assert(testList.contains(board.getCell(9, 8)));
			assert(testList.contains(board.getCell(10, 8)));
			assert(testList.contains(board.getCell(8, 21)));
			//Test that non-center room spaces are not adjacent to any
			testList = board.getAdjList(10, 5);
			assertEquals(0, testList.size());
			
			testList = board.getAdjList(12, 17);
			assertEquals(0, testList.size());
		}
		
		@Test
		public void testAdjacencyWalkways(){
			//Test Open Walkway
			Set<BoardCell> testList = board.getAdjList(8, 16);
			assertEquals(4, testList.size());
			assert(testList.contains(board.getCell(8, 15)));
			assert(testList.contains(board.getCell(8, 17)));
			assert(testList.contains(board.getCell(7, 16)));
			assert(testList.contains(board.getCell(9, 16)));
			//Test By Door
			testList = board.getAdjList(3, 21);
			assertEquals(3, testList.size());
			assert(testList.contains(board.getCell(3, 20)));
			assert(testList.contains(board.getCell(3, 22)));
			assert(testList.contains(board.getCell(4, 21)));
			//Test By Top
			testList = board.getAdjList(0, 18);
			assertEquals(1, testList.size());
			assert(testList.contains(board.getCell(1, 18)));
			//Test By Bottom
			testList = board.getAdjList(20, 8);
			assertEquals(1, testList.size());
			assert(testList.contains(board.getCell(19, 8)));
			//Test By Left
			testList = board.getAdjList(13, 0);
			assertEquals(1, testList.size());
			//Changed from (13, 0) to (13,1) as thats what I meant to test in the first place.
			assert(testList.contains(board.getCell(13, 1)));
			//Test By Right
			testList = board.getAdjList(5, 24);
			assertEquals(2, testList.size());
			assert(testList.contains(board.getCell(5, 23)));
			assert(testList.contains(board.getCell(4, 24)));
		}
		
		//Test doors by rooms
		@Test
		public void testAdjacencyDoor(){
			//Test Blasters Brew Door
			Set<BoardCell> testList = board.getAdjList(1, 17);
			assertEquals(3, testList.size());
			assert(testList.contains(board.getCell(3, 15)));
			assert(testList.contains(board.getCell(1, 18)));
			assert(testList.contains(board.getCell(2, 17)));
			//Test Lecture Hall 2 Door
			testList = board.getAdjList(19, 16);
			assertEquals(4, testList.size());
			assert(testList.contains(board.getCell(16, 21)));
			assert(testList.contains(board.getCell(20, 16)));
			assert(testList.contains(board.getCell(19, 15)));
			assert(testList.contains(board.getCell(18, 16)));
		}
		
		//Test Walkway Targets by door and wall and room
		@Test
		public void testTargetsInWalkway() {
			// test a roll of 1
			board.calcTargets(board.getCell(13, 0), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assert(targets.contains(board.getCell(13, 1)));
			
			// test a roll of 2
			board.calcTargets(board.getCell(13, 0), 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assert(targets.contains(board.getCell(13, 2)));
			assert(targets.contains(board.getCell(12, 1)));
			assert(targets.contains(board.getCell(17, 4)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(13, 0), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assert(targets.contains(board.getCell(13, 3)));
			assert(targets.contains(board.getCell(12, 2)));
			assert(targets.contains(board.getCell(17, 4)));	
		}
		
		//Test room 1 door
		@Test
		public void testTargetsInMakerspace() {
			// test a roll of 1
			board.calcTargets(board.getCell(17, 12), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assert(targets.contains(board.getCell(14, 12)));
			
			// test a roll of 2
			board.calcTargets(board.getCell(17, 12), 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assert(targets.contains(board.getCell(13, 12)));
			assert(targets.contains(board.getCell(14, 11)));	
			assert(targets.contains(board.getCell(14, 13)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(17, 12), 3);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assert(targets.contains(board.getCell(13, 11)));
			assert(targets.contains(board.getCell(13, 13)));	
			assert(targets.contains(board.getCell(14, 10)));
			assert(targets.contains(board.getCell(14, 14)));	
		}
		
		//Test room with secret passage
		@Test
		public void testTargetsInOffice() {
			// test a roll of 1
			board.calcTargets(board.getCell(1, 22), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assert(targets.contains(board.getCell(3, 20)));
			assert(targets.contains(board.getCell(3, 2)));	
			
			// test a roll of 2
			board.calcTargets(board.getCell(1, 22), 2);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assert(targets.contains(board.getCell(3, 19)));
			assert(targets.contains(board.getCell(3, 21)));	
			assert(targets.contains(board.getCell(4, 20)));
			assert(targets.contains(board.getCell(3, 2)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(1, 22), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assert(targets.contains(board.getCell(3, 18)));
			assert(targets.contains(board.getCell(3, 22)));	
			assert(targets.contains(board.getCell(5, 20)));
			assert(targets.contains(board.getCell(4, 19)));
			assert(targets.contains(board.getCell(4, 21)));
			assert(targets.contains(board.getCell(3, 2)));	
		}
		
		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 3 blocked next to player
			board.getCell(5, 23).setOccupied(true);
			board.calcTargets(board.getCell(5, 24), 3);
			board.getCell(5, 23).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(2, targets.size());
			assert(targets.contains(board.getCell(3, 23)));
			assert(targets.contains(board.getCell(4, 22)));
			assert(!targets.contains( board.getCell(5, 23)));
		
			// we want to make sure we can get into a Blasters Brew, even if flagged as occupied
			board.getCell(3, 15).setOccupied(true);
			board.getCell(2, 18).setOccupied(true);
			board.calcTargets(board.getCell(0, 18), 3);
			board.getCell(3, 15).setOccupied(false);
			board.getCell(2, 18).setOccupied(false);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assert(targets.contains(board.getCell(3, 15)));	
			assert(targets.contains(board.getCell(2, 17)));	
			assert(!targets.contains(board.getCell(2, 18)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(13, 1).setOccupied(true);
			board.getCell(13, 5).setOccupied(true);
			board.calcTargets(board.getCell(17, 4), 3);
			board.getCell(13, 1).setOccupied(false);
			board.getCell(13, 5).setOccupied(false);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assert(targets.contains(board.getCell(17, 8)));
			assert(targets.contains(board.getCell(18, 9)));

		}
}
