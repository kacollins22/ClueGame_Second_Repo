package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	//May switch to string
	private String color;
	boolean isHuman;
	protected int row;
	protected int column;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seenCards;
	protected ArrayList<Room> seenRooms;
	
	Player(){}
	
	Player(String name, String color, boolean isHuman){
		this.name = name;
		this.color = color;
		this.isHuman = isHuman;
		hand = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
		seenRooms = new ArrayList<Room>();
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public boolean isHuman() {
		return isHuman;
	}
	
	public Card disproveSuggestion(Card weapon, Card room, Card person) {
		if(hand.contains(weapon) || hand.contains(room) || hand.contains(person)) {
			ArrayList<Card> disproveCardList = new ArrayList<Card>();
			if(hand.contains(weapon)) {
				disproveCardList.add(weapon);
			}
			if(hand.contains(room)) {
				disproveCardList.add(room);
			}
			if(hand.contains(person)) {
				disproveCardList.add(person);
			}
			
			Random rand = new Random();
			int randomCard = rand.nextInt(disproveCardList.size());
			
			return disproveCardList.get(randomCard);
			
		} else { return null; }
	}
	
	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void addRoomsSeen(char room) {
		Board board = Board.getInstance();
		seenRooms.add(board.getRoom(room));
	}
	
	public void addSeen(Card card) {
		seenCards.add(card);
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
}
