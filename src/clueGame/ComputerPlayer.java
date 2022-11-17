package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	public ComputerPlayer(String name, String color, boolean isHuman) {
		super(name, color, isHuman);
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
	
	public BoardCell selectTarget(int pathLength) {
		Set<BoardCell> targets = new HashSet<BoardCell>();
		Board board = Board.getInstance();
		BoardCell positionCell = board.getCell(row, column);
		
		board.calcTargets(positionCell, pathLength);
		targets = board.getTargets();
		
		ArrayList<BoardCell> availibleRooms = new ArrayList<BoardCell>();
		for(BoardCell cell : targets) {
			if (cell.isRoomCenter() && !seenRooms.contains(board.getRoom(cell))) {
				availibleRooms.add(cell);
			}
		}
		
		if(!availibleRooms.isEmpty()) {
			Random rand = new Random();
			int randomRoom = rand.nextInt(availibleRooms.size());
			
			return availibleRooms.get(randomRoom);
			
		} else {
			ArrayList<BoardCell> targetArrayList = new ArrayList<BoardCell>(targets);
			
			Random rand = new Random();
			int randomSpace = rand.nextInt(targetArrayList.size());
			
			return targetArrayList.get(randomSpace);
		}
	}
	
	public Solution makeSuggestion() {
		Solution suggestion;
		Board board = Board.getInstance();
		BoardCell cell = board.getCell(row, column);
		
		Card roomCard = new Card(board.getRoom(cell).getName(), CardType.ROOM);
		//TODO: Get person and weapon cards from deck - seen;
		
		ArrayList<Card> personOptions = board.getPersonCards();
		personOptions.removeAll(seenCards);
		personOptions.removeAll(hand);
		
		ArrayList<Card> weaponOptions = board.getWeaponCards();
		weaponOptions.removeAll(seenCards);
		weaponOptions.removeAll(hand);
		
		Random rand = new Random();
		int randomPerson = rand.nextInt(personOptions.size());
		Card personCard = personOptions.get(randomPerson);
		
		rand = new Random();
		int randomWeapon = rand.nextInt(weaponOptions.size());
		Card weaponCard = weaponOptions.get(randomWeapon);
		
		suggestion = new Solution(roomCard, personCard, weaponCard);
		return suggestion;
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
	
	public String getColor() {
		return super.getColor();
	}
	
	public ArrayList<Card> getHandType(CardType type){
		return super.getHandType(type);
	}
	
	public ArrayList<Card> getSeenType(CardType type){
		return super.getSeenType(type);
	}
	
	public Color colorStringToColor() {
		return super.colorStringToColor();
	}
	
	public void drawPlayer(Graphics g, int width, int height) {
		super.drawPlayer(g, width, height);
	}
}
