package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import clueGame.*;

public class GameSetupTests {
	// Meant to ensure that a few different things are properly created.
	// In particular, these tests ensure that people are loaded into the game, that a human
	// or computer player is made given the people data, that a full deck is initialized,
	// that the solution to the game is dealt properly, and that the remaining cards are dealt
	// to the players.
	
	private static Board board;
	//Cards - Line 24 is people, Line 25 is places, Line 26 is weapons
	private static Card 
	mustardCard, whiteCard, peacockCard, scarletCard, greenCard, plumCard,
	makerspaceCard, blastersBrewCard, studyCard, lecture1Card, lecture2Card, officeCard, loungeCard, labCard, bathroomCard,
	candleCard, revolverCard, wrenchCard, ropeCard, pipeCard, daggerCard;
	
	@BeforeAll
	public static void startUp() {
		//Get the only instance of the board, then initialize it.
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		//Create the card constants for these tests...
		//6 People Cards
		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
		whiteCard = new Card("Mrs. White", CardType.PERSON);
		peacockCard = new Card("Mrs. Peacock", CardType.PERSON);
		scarletCard = new Card("Miss Scarlet", CardType.PERSON);
		greenCard = new Card("Reverend Green", CardType.PERSON);
		plumCard = new Card("Professor Plum", CardType.PERSON);
		//9 Room Cards
		makerspaceCard = new Card(" Makerspace", CardType.ROOM);
		blastersBrewCard = new Card(" Blaster's Brew", CardType.ROOM);
		studyCard = new Card(" Study Room", CardType.ROOM);
		lecture1Card = new Card(" Lecture Hall 1", CardType.ROOM);
		lecture2Card = new Card(" Lecture Hall 2", CardType.ROOM);
		officeCard = new Card(" Office", CardType.ROOM);
		loungeCard = new Card(" Lounge", CardType.ROOM);
		labCard = new Card(" Lab", CardType.ROOM);
		bathroomCard = new Card(" Bathroom", CardType.ROOM);
		//6 Weapon Cards
		candleCard = new Card("Candle", CardType.WEAPON);
		revolverCard = new Card("Revolver", CardType.WEAPON);
		wrenchCard = new Card("Wrench", CardType.WEAPON);
		ropeCard = new Card("Rope", CardType.WEAPON);
		pipeCard = new Card("Pipe", CardType.WEAPON);
		daggerCard = new Card("Dagger", CardType.WEAPON);
	}
	
	@BeforeEach
	public void testPrep() {
		//Set up things that need to be done before EVERY test here.
		
	}
	
	@Test
	public void playersLoaded() {
		//Test that all six players are loaded for the board.
		int numPlayers = board.getNumPlayers();
		assertTrue (numPlayers == 6);
	}
	
	@Test
	public void playerTypeCheck() {
		//Test that all players are either human players or computer players, and that
		//they're being set as such correctly.
		Player[] playerList = board.getPlayerList();
		int humanPlayers = 0;
		int computerPlayers = 0;
		for (int i = 0; i < board.getNumPlayers(); i++) {
			Player currPlayer = playerList[i];
			if (currPlayer.isHuman()) {
				humanPlayers += 1;
			}
			else {
				computerPlayers += 1;
			}
		}
		
		//Verify there's 1 human player, 5 computer players
		assertTrue (humanPlayers == 1);
		assertTrue (computerPlayers == 5);
		assertTrue ((humanPlayers + computerPlayers) == 6);
	}
	
	@Test
	public void deckLoaded() {
		//Test that the full deck is initialized 
		//I'm assuming that the deck would be initialized in the initialize function
		//Given that the deck is the 21 cards we have here, then we can assume the deck has 21 cards total
		ArrayList<Card> deck = board.getDeck();
		assertTrue(deck.size() == 21);
	}
	
	@Test
	public void remainingCardsDealt() {
		//Test that all cards that aren't the solution are in some player's hand.
		//Additionally, verifies that all but 3 cards are in playerHands, to match that
		//the solution should not be in a hand
		//Get the cards that are in the solution
		Solution theSolution = board.getSolution();
		ArrayList<Card> deck = board.getDeck();
		Card weapon = theSolution.getWeapon();
		Card room = theSolution.getRoom();
		Card person = theSolution.getPerson();
		
		//Iterate through all hands, get all cards and put them in cardList
		ArrayList <Card> cardList = new ArrayList();
		Player[] playerList = board.getPlayerList();
		
		for (int i = 0; i < playerList.length; i++) {
			Player currPlayer = playerList[i];
			ArrayList <Card> playerCards = currPlayer.getHand();
			for (int j = 0; j < playerCards.size(); j++) {
				cardList.add(playerCards.get(j));
			}
		}
		
		//Make sure the player hands total to the right amount of cards and
		//that none of the cards of the solution are in the hand
		assertTrue (cardList.size() == (deck.size() - 3));
		assertFalse (cardList.contains(weapon));
		assertFalse (cardList.contains(person));
		assertFalse (cardList.contains(room));
	}
}
