package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class ComputerAITest {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		//Get the board working and loaded	
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void selectTargetTest() {
		//Supposed to test that the computer, given a dice roll, will either move into a random room
		//that it hasn't seen or a random space if no such room exists within range.
		
		//Tests that given 2 unseen rooms within range, a computer will always move to one of the two rooms at random.
		//Creates orange, the computer player we're using here, and puts him within range of both the lecture hall and bathroom
		ComputerPlayer orange = new ComputerPlayer("Dr. Orange", "ORANGE", false);
		orange.setPosition(11,18);
		//Creates counters for the times that lectureHall and bathroom are chosen
		int lectureHallChosen = 0;
		int bathroomChosen = 0;
		for (int i = 0; i < 50; i++) {
			BoardCell orangeTarget = orange.selectTarget(4);
			if (orangeTarget == board.getCell(8, 21)){
				bathroomChosen += 1;
			}
			else if (orangeTarget == board.getCell(16, 21)) {
				lectureHallChosen += 1;
			}
		}
		//Verifies that they were both chosen a roughly equal amount of times, and that no other
		//space was chosen.
		assertTrue(lectureHallChosen > 10);
		assertTrue(bathroomChosen > 10);
		assertTrue(lectureHallChosen + bathroomChosen == 50);
		
		//Tests that given one room to move into that is unseen, a computer will always move to that room
		//Orange is put outside a lecture hall and should always move into it
		orange.setPosition(13,3);
		BoardCell orangeTarget = orange.selectTarget(3);
		assertTrue(orangeTarget == board.getCell(17,4));
		
		//Tests that given no rooms within range, a computer will always move randomly
		//Orange is put in a walkway with movement range 1, so it should move in each cardinal direction
		//a roughly equal amount of times
		orange.setPosition(6,12);
		int northSpace = 0;
		int westSpace = 0;
		int eastSpace = 0;
		int southSpace = 0;
		for (int i = 0; i < 100; i++) {
			orangeTarget = orange.selectTarget(1);
			if (orangeTarget == board.getCell(5, 12)) {
				northSpace += 1;
			}
			else if (orangeTarget == board.getCell(6, 13)) {
				eastSpace += 1;
			}
			else if (orangeTarget == board.getCell(6, 11)) {
				westSpace += 1;
			}
			else if (orangeTarget == board.getCell(7, 12)) {
				southSpace += 1;
			}
		}
		//Verify that it moves each direction at least a good chunk of the time and that it doesn't
		//move anywhere else
		assertTrue(northSpace + westSpace + eastSpace + southSpace == 100);
		assertTrue(northSpace > 15);
		assertTrue(southSpace > 15);
		assertTrue(westSpace > 15);
		assertTrue(eastSpace > 15);
		
		//Finally, tests that given no unseen rooms in range but that a seen room is, that
		//a computer player moves into a space it can reach randomly (including the room)
		//Orange is put outside the office and has seen the office, so it should move in the office
		//roughly as much as any other space.
		orange.setPosition(4,20);
		orange.addRoomsSeen('O');
		northSpace = 0;
		westSpace = 0;
		eastSpace = 0;
		southSpace = 0;
		for (int i = 0; i < 100; i++) {
			orangeTarget = orange.selectTarget(1);
			if (orangeTarget == board.getCell(3, 20)) {
				northSpace += 1;
			}
			else if (orangeTarget == board.getCell(4, 21)) {
				eastSpace += 1;
			}
			else if (orangeTarget == board.getCell(4, 19)) {
				westSpace += 1;
			}
			else if (orangeTarget == board.getCell(5, 20)) {
				southSpace += 1;
			}
		}
		assertTrue(northSpace + westSpace + eastSpace + southSpace == 100);
		assertTrue(northSpace > 10);
		assertTrue(southSpace > 10);
		assertTrue(westSpace > 10);
		assertTrue(eastSpace > 10);
	}
	
	@Test
	public void createSuggestionTest() {
		//Supposed to test that a computer, given a list of seen cards, can make a
		//suggestion composed of the room it's currently in and an unseen weapon/person card pair.
		
		//How to do this: create a computer player, set their position in a room, and give them a list of cards
		//that they've seen. As long as their suggestion has the current room they're in, along with an unseen
		//weapon/person card pair, we're golden.
		
		//Create and place a computer player in the Lecture Hall 1 area.
		ComputerPlayer orange = new ComputerPlayer("Dr. Orange", "ORANGE", false);
		orange.setPosition(17,4);
		//Ensure that while in Lecture Hall 1, the room will always equal Lecture Hall 1.
		Solution orangeSuggestion = orange.makeSuggestion();
		assertTrue(orangeSuggestion.getRoom().getCardName().equals("Lecture Hall 1"));
		
		//Add a few cards to orange's seen weapons
		orange.addSeen(board.getWeaponCards().get(5));
		orange.addSeen(board.getWeaponCards().get(4));
		orange.addSeen(board.getWeaponCards().get(0));
		
		for (int i = 0; i < 30; i++) {
			//Make sure that orange never suggests a weapon he's seen already
			orangeSuggestion = orange.makeSuggestion();
			assertTrue(orangeSuggestion.getWeapon().getCardName() != "Dagger");
			assertTrue(orangeSuggestion.getWeapon().getCardName() != "Pipe");
			assertTrue(orangeSuggestion.getWeapon().getCardName() != "Candle");
		}
		
		//Add a couple more cards to orange's seen weapons.
		orange.addSeen(board.getWeaponCards().get(1));
		orange.addSeen(board.getWeaponCards().get(3));
		
		for (int i = 0; i < 30; i++) {
			//Make sure orange always suggests the wrench, as the one weapon he has not seen
			orangeSuggestion = orange.makeSuggestion();
			assertTrue(orangeSuggestion.getWeapon().equals(board.getWeaponCards().get(2)));
		}
	}
}
