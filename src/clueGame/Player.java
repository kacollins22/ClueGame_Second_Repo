package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public abstract class Player extends JPanel{
	protected String name;
	//May switch to string
	protected String color;
	protected boolean isHuman;
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
	
	public ArrayList<Card> getHandType(CardType type){
		ArrayList<Card> handType = new ArrayList<Card>();
		for(Card card : hand) {
			if(card.getCardType() == type) {
				handType.add(card);
			}
		}
		return handType;
	}
	
	public ArrayList<Card> getSeenType(CardType type){
		ArrayList<Card> seenType = new ArrayList<Card>();
		for(Card card : seenCards) {
			if(card.getCardType() == type) {
				seenType.add(card);
			}
		}
		return seenType;
	}
	
	public Color colorStringToColor() {
		//A bunch of things that takes the color string from a player and returns a color
		//associated with them. It's a bit bulky but it works!
		Color output;
		if (color.equals("YELLOW")) { output = new Color(255,255,150); }
		else if (color.equals("RED")) { output = new Color(255,150,150); }
		else if (color.equals("BLUE")) { output = new Color(150,150,255); }
		else if (color.equals("GREEN")) { output = new Color(150,255,150); }
		else if (color.equals("PURPLE")) { output = new Color(198,100,185); }
		else if (color.equals("WHITE")) { output = new Color(220,220,220); }
		else { output = new Color(150,150,150); }
		return output; 
	}
	
	public void drawPlayer(Graphics g, int width, int height) {
		g.setColor(colorStringToColor());
		g.drawOval(width*column + 2, height*row + 2, width - 4, height - 4);
		g.fillOval(width*column + 2, height*row + 2, width - 4, height - 4);
	}
}
