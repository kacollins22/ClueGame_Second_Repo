package clueGame;

public class Solution {
	//Variables to store the solution's Room, Person, and Weapon cards.
	private Card room;
	private Card person;
	private Card weapon;
	
	//An empty constructor, just in case. We shouldn't use this, but it's here.
	public Solution(){
		
	}
	
	//The proper constructor for a solution. Sets a room, person, and weapon card to be stored.
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	//Getters for the room, person, and weapon cards respectively.
	public Card getRoom() {
		return room;
	}
	
	public Card getPerson() {
		return person;
	}
	
	public Card getWeapon() {
		return weapon;
	}
}
