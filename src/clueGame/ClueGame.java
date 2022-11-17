package clueGame;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ClueGame extends JFrame{
	
	public static void main(String[] args) {
		//This is the initialization function for the entire program. Right now, the goal
		//is to make it pull up a complete Board, the GameControlPanel, and the GameCardPanel
		//simultaneously.

		//Create the frame, make it exit on close, and make it visible. 
		ClueGame gameFrame = new ClueGame();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		
		//Get the contents of the board from their various places.
		GameControlPanel controlPanel = new GameControlPanel();
		//Create and quickly initialize the board.
		Board boardPanel = Board.getInstance();
		boardPanel.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		boardPanel.initialize();
		//Get the human player for GameCardPanel, then create it
		Player[] players = boardPanel.getPlayerList();
		HumanPlayer human = (HumanPlayer) players[0];
		GameCardPanel cardPanel = new GameCardPanel(human);
		
		//Finally, set the contents of the panel to be the respective controls and displays.
		gameFrame.setTitle("CSCI 306 - Clue");
		gameFrame.setLayout(new BorderLayout());
		JButton testButton = new JButton();
		gameFrame.add(boardPanel, BorderLayout.CENTER);
		gameFrame.add(cardPanel,BorderLayout.EAST);
		gameFrame.add(controlPanel,BorderLayout.SOUTH);
		gameFrame.setSize(900, 880);
		
	}
}
