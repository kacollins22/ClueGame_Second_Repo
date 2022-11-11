package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String string, String string2, boolean b) {
		super(string, string2, b);
	}
	public void updateHand(Card card) {
		super.updateHand(card);
	}
	
	public ArrayList<Card> getHand(){
		return super.getHand();
	}
	
	public boolean isHuman() {
		return super.isHuman();
	}
	
	public Card disproveSuggestion(Card weapon, Card room, Card person) {
		return super.disproveSuggestion(weapon, room, person);
	}
	
	public void setPosition(int row, int column) {
		super.setPosition(row, column);
	}
	
	public void addRoomsSeen(char c) {
		super.addRoomsSeen(c);
	}
	
	public void addSeen(Card card) {
		super.addSeen(card);
	}
	
	public String getName() {
		return super.getName();
	}
}
