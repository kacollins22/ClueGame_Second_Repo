package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSolutionTest {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		//Get the board working and loaded	
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void accusationChecks() {
		//Get the cards that are the solution.
		Solution theSolution = board.getSolution();
		Card weapon = theSolution.getWeapon();
		Card room = theSolution.getRoom();
		Card person = theSolution.getPerson();
						
		//Make cards that are specifically NOT the solution cards, and have no
		//chance of being the solution cards.
		Card notWeapon = new Card("Shoe", CardType.WEAPON);
		Card notRoom = new Card("Backyard", CardType.ROOM);
		Card notPerson = new Card("Dr. Orange", CardType.PERSON);		
		
		//Asserts that an accusationCheck is true when done with all 3 correct cards,
		//and that it returns false when any card is wrong.
		assertTrue (board.accusationCheck(weapon, room, person));
		assertFalse (board.accusationCheck(weapon, room, notPerson));
		assertFalse (board.accusationCheck(weapon, notRoom, person));
		assertFalse (board.accusationCheck(notWeapon, room, person));
	}
	
	@Test
	public void disproveSuggestions() {
		//Get the cards that are the solution.
		Solution theSolution = board.getSolution();
		Card weapon = theSolution.getWeapon();
		Card room = theSolution.getRoom();
		Card person = theSolution.getPerson();
								
		//Make cards that are specifically NOT the solution cards, and have no
		//chance of being the solution cards.
		Card notWeapon = new Card("Shoe", CardType.WEAPON);
		Card notRoom = new Card("Backyard", CardType.ROOM);
		Card notPerson = new Card("Dr. Orange", CardType.PERSON);
		
		//Create two players, and give them some cards to disprove suggestions with.
		Player red = new HumanPlayer("Crimson", "RED", true);
		Player blue = new ComputerPlayer("Teal", "BLUE", false);
		//Red gets the notWeapon card and notRoom card
		red.updateHand(notWeapon);
		red.updateHand(notRoom);
		//Blue gets the notRoom card and notPerson card
		blue.updateHand(notRoom);
		blue.updateHand(notPerson);
		
		//Assert that both red and blue can return a card given that they have it in their hand
		//and that it is a part of the suggestion
	    assertTrue(red.disproveSuggestion(notWeapon, room, person) == notWeapon);
	    assertTrue(blue.disproveSuggestion(weapon, room, notPerson) == notPerson);
	    
	    //Assert that red and blue can return null given that no cards are in their hand that
	    //match the card in the suggestion.
	    assertTrue(red.disproveSuggestion(weapon, room, notPerson) == null);
	    assertTrue(blue.disproveSuggestion(notWeapon, room, person) == null);
	    
	    //Assert that given two cards, both red and blue choose the card to disprove at random.
	    int rWeaponDisproves = 0;
	    int rRoomDisproves = 0;
	    int bRoomDisproves = 0;
	    int bPersonDisproves = 0;
	    
	    for (int i = 0; i < 50; i++) {
	    	//Red, the human player, can disprove weapon and room, but must choose at random.
	    	Card disprovedCard = red.disproveSuggestion(notWeapon, notRoom, person);
	    	if (disprovedCard == notWeapon) {
	    		rWeaponDisproves += 1;
	    	}
	    	else if (disprovedCard == notRoom){
	    		rRoomDisproves += 1;
	    	}
	    	
	    	//Blue must do the same thing as red, but with room and person cards
	    	disprovedCard = blue.disproveSuggestion(weapon, notRoom, notPerson);
	    	if (disprovedCard == notRoom) {
	    		bRoomDisproves += 1;
	    	}
	    	else if (disprovedCard == notPerson){
	    		bPersonDisproves += 1;
	    	}
	    }
	    assertTrue(bRoomDisproves > 15 && bPersonDisproves > 15);
	    assertTrue(rWeaponDisproves > 15 && rRoomDisproves > 15);
	}
}
