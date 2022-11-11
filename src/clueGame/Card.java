package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	Card(){}
	
	public Card(String cardName, CardType cardType){
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	//Card names are all unique and can be tested
	public boolean equals(Card target) {
		if(target.getCardName() == cardName) {
			return true;
		} else {
			return false;
		}
	}
	
	//Getters for card info
	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return cardType;
	}
}
