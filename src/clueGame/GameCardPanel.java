package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCardPanel extends JPanel {
	
	JPanel peoplePanel;
	JPanel roomsPanel;
	JPanel weaponsPanel;
	
	public GameCardPanel(HumanPlayer player) {
		//Creates the layout panel as a narrow panel with a border.
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards:"));
		//Populates this panel with sub-panels that contain most of the interesting stuff.
		JPanel panel = createPeoplePanel(player);
		add(panel);
		panel = createRoomsPanel(player);
		add(panel);
		panel = createWeaponsPanel(player);
		add(panel);
	}
	
	public JPanel createPeoplePanel(HumanPlayer player) {
		JPanel panel = new JPanel();
		//Creates a panel of theoretically infinite columns and gives it a nice border.
		panel.setLayout(new GridLayout(0,1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People:"));
		
		//Makes a "In Hand" label.
		JLabel inHandLabel = new JLabel("In Hand:");
		panel.add(inHandLabel);
		
		//Gets all cards of the Person type in the player's hand, then adds them to the panel.
		//If there's no people cards in the hand, then print a none panel and add it
		ArrayList<Card> peopleInHand = player.getHandType(CardType.PERSON);
		if (peopleInHand.size() == 0) {
			JTextField nonePanel = new JTextField(20);
			nonePanel.setText("None");
			panel.add(nonePanel);
		}
		//Otherwise, add all cards in the hand
		for (int i = 0; i < peopleInHand.size(); i++) {
			JTextField cardPanel = new JTextField(20);
			cardPanel.setText(peopleInHand.get(i).getCardName());
			panel.add(cardPanel);
		}
		
		//Makes a "Seen" label.
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		
		//Gets all cards of the Person type that the player has seen, then adds them to the panel.
		//If there are no Person cards in that hand, then print a none panel and add it.
		ArrayList<Card> peopleSeen = player.getSeenType(CardType.PERSON);
		if (peopleSeen.size() == 0) {
			JTextField nonePanel = new JTextField(20);
			nonePanel.setText("None");
			panel.add(nonePanel);
		}
		//Otherwise, add all cards in the hand
		for (int i = 0; i < peopleSeen.size(); i++) {
			JTextField cardPanel = new JTextField(20);
			cardPanel.setText(peopleSeen.get(i).getCardName());
			panel.add(cardPanel);
		}
		
		//Finally, return the panel.
		peoplePanel = panel;
		return panel;
	}
	
	public JPanel createRoomsPanel(HumanPlayer player) {
		JPanel panel = new JPanel();
		//Creates a panel of theoretically infinite columns and gives it a nice border.
		panel.setLayout(new GridLayout(0,1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms:"));
		
		//Makes a "In Hand" label.
		JLabel inHandLabel = new JLabel("In Hand:");
		panel.add(inHandLabel);
		
		//Gets all cards of the Person type in the player's hand, then adds them to the panel.
		//If there's no people cards in the hand, then print a none panel and add it
		ArrayList<Card> roomsInHand = player.getHandType(CardType.ROOM);
		if (roomsInHand.size() == 0) {
			JTextField nonePanel = new JTextField(20);
			nonePanel.setText("None");
			panel.add(nonePanel);
		}
		//Otherwise, add all cards in the hand
		for (int i = 0; i < roomsInHand.size(); i++) {
			JTextField cardPanel = new JTextField(20);
			cardPanel.setText(roomsInHand.get(i).getCardName());
			panel.add(cardPanel);
		}
		
		//Makes a "Seen" label.
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		
		//Gets all cards of the Person type that the player has seen, then adds them to the panel.
		//If there are no Person cards in that hand, then print a none panel and add it.
		ArrayList<Card> roomsSeen = player.getSeenType(CardType.ROOM);
		if (roomsSeen.size() == 0) {
			JTextField nonePanel = new JTextField(20);
			nonePanel.setText("None");
			panel.add(nonePanel);
		}
		//Otherwise, add all cards in the hand
		for (int i = 0; i < roomsSeen.size(); i++) {
			JTextField cardPanel = new JTextField(20);
			cardPanel.setText(roomsSeen.get(i).getCardName());
			panel.add(cardPanel);
		}
		
		//Finally, return the panel.
		roomsPanel = panel;
		return panel;
	}
	
	public JPanel createWeaponsPanel(HumanPlayer player) {
		JPanel panel = new JPanel();
		//Creates a panel of theoretically infinite columns and gives it a nice border.
		panel.setLayout(new GridLayout(0,1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons:"));
		
		//Makes a "In Hand" label.
		JLabel inHandLabel = new JLabel("In Hand:");
		panel.add(inHandLabel);
		
		//Gets all cards of the Person type in the player's hand, then adds them to the panel.
		//If there's no people cards in the hand, then print a none panel and add it
		ArrayList<Card> weaponsInHand = player.getHandType(CardType.WEAPON);
		if (weaponsInHand.size() == 0) {
			JTextField nonePanel = new JTextField(20);
			nonePanel.setText("None");
			panel.add(nonePanel);
		}
		//Otherwise, add all cards in the hand
		for (int i = 0; i < weaponsInHand.size(); i++) {
			JTextField cardPanel = new JTextField(20);
			cardPanel.setText(weaponsInHand.get(i).getCardName());
			panel.add(cardPanel);
		}
		
		//Makes a "Seen" label.
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		
		//Gets all cards of the Person type that the player has seen, then adds them to the panel.
		//If there are no Person cards in that hand, then print a none panel and add it.
		ArrayList<Card> weaponsSeen = player.getSeenType(CardType.WEAPON);
		if (weaponsSeen.size() == 0) {
			JTextField nonePanel = new JTextField(20);
			nonePanel.setText("None");
			panel.add(nonePanel);
		}
		//Otherwise, add all cards in the hand
		for (int i = 0; i < weaponsSeen.size(); i++) {
			JTextField cardPanel = new JTextField(20);
			cardPanel.setText(weaponsSeen.get(i).getCardName());
			panel.add(cardPanel);
		}
		
		//Finally, return the panel.
		weaponsPanel = panel;
		return panel;
	}
	
	public void updatePanel(JPanel panel, HumanPlayer player) {
		//Updates the entire panel by removing the entire data set and rerunning
		//all the sub-panel creator functions.
		panel.removeAll();
		panel.add(createPeoplePanel(player));
		panel.add(createRoomsPanel(player));
		panel.add(createWeaponsPanel(player));
		panel.revalidate();
	}
	
	public Color colorStringToColor(String input) {
		//A bunch of things that takes the color string from a player and returns a color
		//associated with them. It's a bit bulky but it works!
		Color output = Color.WHITE;
		if (input == "YELLOW") { output = new Color(255,255,150); }
		if (input == "RED") { output = new Color(255,150,150); }
		if (input == "BLUE") { output = new Color(150,150,255); }
		if (input == "GREEN") { output = new Color(150,255,150); }
		if (input == "PURPLE") { output = new Color(198,255,185); }
		if (input == "WHITE") { output = new Color(220,220,220); }
		return output; 
	}
	
	//Main to test the panel and do some cool stuff
	public static void main(String[] args) {
		//Make the board exist.
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		//Get the human player from the board.
		HumanPlayer humanPlayer = (HumanPlayer) board.getPlayerList()[0];
		
		//Do a bunch of panel creation stuff.
		GameCardPanel panel = new GameCardPanel(humanPlayer);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 600);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		//For actual testing purposes, add a few different cards to seen, then update the panel.
		ArrayList<Card> people = board.getPersonCards();
		ArrayList<Card> rooms = board.getRoomCards();
		ArrayList<Card> weapons = board.getWeaponCards();
		
		//These cards could duplicate cards that are in a hand, but it shouldn't in the actual
		//game when we finally get to it. For now, it works and it works well!
		humanPlayer.addSeen(people.get(1));
		humanPlayer.addSeen(people.get(5));
		humanPlayer.addSeen(weapons.get(3));
		humanPlayer.addSeen(weapons.get(0));
		humanPlayer.addSeen(rooms.get(8));
		humanPlayer.addSeen(rooms.get(4));
		
		panel.updatePanel(panel, humanPlayer);
	}
}
