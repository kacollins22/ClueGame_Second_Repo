package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	private JTextField name;
	private JTextField roll;
	private JTextField guess;
	private JTextField guessResult;

	// A constructor for the panel. Between it and it's submethods,
	// it does the vast majority of the work.
	public GameControlPanel()  {
		//Create a layout with 2 columns.
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopRow();
		add(panel);
		panel = createBottomRow();
		add(panel);
	}
	
	public JPanel createTopRow() {
		//Create the top panel and set it to a 1x4 layout (1 row, 4 elements)
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		//Create the subpanels in their own submethods
		JPanel nPanel = createNamePanel();
		add(nPanel);
		JPanel tPanel = createTurnPanel();
		add(tPanel);
		JPanel accButton = createAccButton();
		add(accButton);
		JPanel nextTurnButton = createNextTurnButton();
		add(nextTurnButton);
		//Return the topPanel, where the four sub-panels are stored
		return topPanel;
	}
	
	private JPanel createNamePanel() {
		//Create a panel with layout of 2,1 (2 rows, 1 element each)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		//Above the name, we signify this is for who's turn it is
		JLabel nameLabel = new JLabel("Whose Turn?", SwingConstants.CENTER);
		//Also, make the name a part of this (name was set earlier as a private variable)
		name = new JTextField(20);
		name.setEditable(false);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		//Add both nameLabel and name to the panel, then return it
		panel.add(nameLabel);
		panel.add(name);
		return panel;
	}
	
	private JPanel createTurnPanel() {
		//Create a panel with a layout of 2 rows, 1 element each (consistency!)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		//To the left, we have a label signifying this is the roll
		JLabel rollLabel = new JLabel("Roll:", SwingConstants.CENTER);
		//We have a JTextField that should contain a number
		roll = new JTextField(5);
		roll.setEditable(false);
		roll.setHorizontalAlignment(SwingConstants.CENTER);
		//Add both rollLabel and roll to the panel
		panel.add(rollLabel);
		panel.add(roll);
		return panel;
	}
	
	private JPanel createAccButton() {
		JPanel panel = new JPanel();
		JButton accuse = new JButton("Make Accusation");
		accuse.setPreferredSize(new Dimension(180,70));
		panel.add(accuse);
		return panel;
	}
	
	private JPanel createNextTurnButton() {
		JPanel panel = new JPanel();
		JButton nextTurn = new JButton("Next!");
		nextTurn.setPreferredSize(new Dimension(180,70));
		panel.add(nextTurn);
		return panel;
	}
	
	public JPanel createBottomRow() {
		//Create the lower panel, and set it to a 1x2 layout (1 row, 2 elements
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,0));
		JPanel gPanel = createGuessPanel();
		add(gPanel);
		JPanel gResultPanel = createGuessResultPanel();
		add(gResultPanel);
		return bottomPanel;
	}
	
	public JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, undefined elements 
		panel.setLayout(new GridLayout(1,0));
		guess = new JTextField(20);
		panel.add(guess);
		guess.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess:"));
		return panel;
	}
	
	public JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		guessResult = new JTextField(20);
		panel.add(guessResult);
		guessResult.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result:"));
		return panel;
	}
	
	public Color colorStringToColor(String input) {
		Color output = Color.WHITE;
		if (input == "YELLOW") { output = new Color(255,255,150); }
		if (input == "RED") { output = new Color(255,150,150); }
		if (input == "BLUE") { output = new Color(150,150,255); }
		if (input == "GREEN") { output = new Color(150,255,150); }
		if (input == "PURPLE") { output = new Color(198,255,185); }
		if (input == "WHITE") { output = new Color(220,220,220); }
		return output; 
	}
	
	//Setters for name, the die roll, guess, and guessResult
	public void setName(Player player) {
		name.setText(player.getName());
		name.setBackground(colorStringToColor(player.getColor()));
	}
	
	public void setRoll(Integer newRoll) {
		roll.setText(newRoll.toString());
	}
	
	public void setGuess(String newGuess) {
		guess.setText(newGuess);
	}
	
	public void setGuessResult(String newGuessResult) {
		guessResult.setText(newGuessResult);
	}
	
	//Main to test the panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setName(new ComputerPlayer("Col. Mustard", "YELLOW", false));
		panel.setRoll(5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}