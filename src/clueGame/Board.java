package clueGame;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import java.util.Random;

import experiment.TestBoardCell;

public class Board extends JPanel {
	
	private BoardCell[][] grid;
	
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Solution theAnswer;
	private ArrayList<Card> deck;
	
	private final static int NUM_PLAYERS = 6;
	private Player[] players;
	
    /*
    * variable and methods used for singleton pattern
    */
    private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize(){
    	//Setup Initialize
    	try {
    		loadSetupConfig();
    		loadLayoutConfig();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	generateAdjacentcies();
    	deal();
    }

    public void setConfigFiles(String csvf, String txtf) {
    	this.layoutConfigFile = "ClueInitFiles/data/" + csvf;
    	this.setupConfigFile = "ClueInitFiles/data/" + txtf;
    }
    
    //Loads Room information from setup files
    public void loadSetupConfig() {
    	//Create filereader variables, array of strings for reading each line, and initializing roomMap to accept Room info.
    	BufferedReader br;
    	FileReader reader;
    	ArrayList<String> listOfLines;
    	listOfLines = new ArrayList<String>();
    	roomMap = new HashMap<Character, Room>();
    	deck = new ArrayList<Card>();
    	players = new Player[NUM_PLAYERS];
    	
    	try {
    		//Read file to buffered reader
    		reader = new FileReader(setupConfigFile);
			br = new BufferedReader(reader);
			
			//Load buffered reader into array of strings
			String line = br.readLine();
			while (line != null) {
				listOfLines.add(line);
				line = br.readLine();
			}
			int numPlayer = 0;
			//For each string in array, split data into room type, room name, and room character.
			for(String str : listOfLines) {
				String [] setupData = str.split(",");
				
				//If room type is room, insert into map as <Room Character, Room Object>, and create room card added to deck
				if(setupData[0].equals("Room")) {
					String roomName = setupData[1];
					Room room = new Room(roomName.substring(1));
					roomMap.put(setupData[2].charAt(1), room);
					
					Card roomCard = new Card(roomName, CardType.ROOM);
					deck.add(roomCard);
				//If room type is Space, insert into map as <Room Character, Room Object>, and test for walkway and add flag future flag for walkway testing.
				} else if(setupData[0].equals("Space")) {
					String roomName = setupData[1];
					Room room = new Room(roomName.substring(1));
					if(setupData[2].charAt(1) == 'W') {
						room.setTraversable();
					}
					roomMap.put(setupData[2].charAt(1), room);
				//If data is of person type, add person card and create instance of player.
				} else if(setupData[0].equals("Person")) {
					Card personCard = new Card(setupData[1].substring(1), CardType.PERSON);
					deck.add(personCard);
					//TODO: MAKE PLAYER INSTANCE AND ADD TO PLAYERLIST
					Player person;
					if (numPlayer == 0) {
						person = new HumanPlayer(setupData[1].substring(1), setupData[2].substring(1), true);
					} else {
						person = new ComputerPlayer(setupData[1].substring(1), setupData[2].substring(1), false);
					}
					//Set current players position
					person.setPosition(Integer.parseInt(setupData[3]), Integer.parseInt(setupData[4]));
					//Add person to array
					players[numPlayer] = person;
					numPlayer++;
				//If data is of weaopn type, add weapon card to deck.
				} else if(setupData[0].equals("Weapon")) {
					Card weaponCard = new Card(setupData[1].substring(1), CardType.WEAPON);
					deck.add(weaponCard);
				}

			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
  //Create grid of cells with attributed based off csv file
    public void loadLayoutConfig() throws Exception {
    	//Create filereader variables and array of strings for reading each line.
    	BufferedReader br;
    	FileReader reader;
    	ArrayList<String> listOfLines;
    	listOfLines = new ArrayList<String>();
		try {
			//Read file to buffered reader
			reader = new FileReader(layoutConfigFile);
			br = new BufferedReader(reader);
			
			//NumRows setup and load buffered reader into array of strings
			String line = br.readLine();
			while (line != null) {
				listOfLines.add(line);
				line = br.readLine();
			}
			
			this.numRows = listOfLines.size();
			
			//NumCols setup
			int tempCols = 0;
			int testCols = 0;
			int runs = 0;
			for(String str : listOfLines) {
				String [] strArr = str.split(",");				
				for(String init : strArr) {
					tempCols ++;
				}
				
				if (testCols != tempCols && runs > 0) {
					throw new BadConfigFormatException("Columns are inconsistent.");
				}
				
				this.numColumns = tempCols;
				testCols = tempCols;
				tempCols = 0;
				runs++;
			}
			
			//Grid Setup
			grid = new BoardCell[numRows][numColumns];
			
			//Set i and j to keep track of current position.
			int i = 0;
			int j = 0;
			//For each row, split into separate cells by column
			for(String str : listOfLines) {
				String[] cellArr = str.split(",");				
				for(String cellInfo : cellArr) {
					//Create new cell and add to grid for each cell in csv
					BoardCell cell = new BoardCell();
					grid[i][j] = cell;
					
					char cellInit = cellInfo.charAt(0);
					//Check if cell has label not stated in setup file
					if(!roomMap.containsKey(cellInit)) {
						throw new BadConfigFormatException("Layout contains room not specified by setup file.");
					}
					//Set initial using given initial, row and column info for cell, and default to not being a door.
					cell.setInitial(cellInit);
					cell.setRowCol(i, j);
					cell.setDoorDirection(DoorDirection.NONE);
					
					//Extra character setups
					if (cellInfo.length() == 2) {
						char extraChar = cellInfo.charAt(1);
						//Room Label Setter
						if(extraChar == '#') {
							cell.setLabel();
							Room room = getRoom(cellInit);
							room.setLabelCell(cell);
						
						//Room Center Setter
						} else if(extraChar == '*') {
							cell.setRoomCenter();
							Room room = getRoom(cellInit);
							
							room.setCenterCell(cell);
							getRoom(cellInit).setCenterCell(cell);
							
						//Door Direction setters
						} else if(extraChar == '^') {
							cell.setDoorDirection(DoorDirection.UP);
						} else if(extraChar == 'v') {
							cell.setDoorDirection(DoorDirection.DOWN);
						} else if(extraChar == '<') {
							cell.setDoorDirection(DoorDirection.LEFT);
						} else if(extraChar == '>') {
							cell.setDoorDirection(DoorDirection.RIGHT);
						} else {
							//If second character is none of these, room is a secret passage to room with the label of second character.
							cell.setSecretPassage(extraChar);
							getRoom(cellInit).setHasSecretPassage(extraChar);
						}
					}
					j++;
				}
				i++;
				j = 0;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    //Iterates through each cell in the grid and adds adjacencies to all walkways and room centers
    public void generateAdjacentcies() {
    	for (int i = 0; i < numRows; i++) {
    		for (int j = 0; j < numColumns; j++) {
    			if(grid[i][j].getInitial() == 'W') {
    				//Add adjacent Walkways
    				if(i > 0) {
    					if(getRoom(grid[i-1][j]).getTraversable()) {
    						grid[i][j].addAdj(grid[i-1][j]); }
    				}
    				if(i < numRows - 1) {
    					if(getRoom(grid[i+1][j]).getTraversable()) {
    						grid[i][j].addAdj(grid[i+1][j]); }
    				}
    				if(j > 0) {
    					if(getRoom(grid[i][j-1]).getTraversable()) {
    						grid[i][j].addAdj(grid[i][j-1]); }
    				}
    				if(j < numColumns - 1) {
    					if(getRoom(grid[i][j+1]).getTraversable()) {
    						grid[i][j].addAdj(grid[i][j+1]); }
    				}
    				//If door, add room center of room in direction of door to adjacency list, and door to room center adjacency.
    				if(grid[i][j].getDoorDirection() == DoorDirection.UP) {
    					Room adjRoom = getRoom(grid[i-1][j]);
    					grid[i][j].addAdj(adjRoom.getCenterCell());
    					adjRoom.getCenterCell().addAdj(grid[i][j]);
    					
    				} else if(grid[i][j].getDoorDirection() == DoorDirection.DOWN) {
    					Room adjRoom = getRoom(grid[i+1][j]);
    					grid[i][j].addAdj(adjRoom.getCenterCell());
    					adjRoom.getCenterCell().addAdj(grid[i][j]);
    					
    				} else if(grid[i][j].getDoorDirection() == DoorDirection.LEFT) {
    					Room adjRoom = getRoom(grid[i][j-1]);
    					grid[i][j].addAdj(adjRoom.getCenterCell());
    					adjRoom.getCenterCell().addAdj(grid[i][j]);
    					
    				} else if(grid[i][j].getDoorDirection() == DoorDirection.RIGHT) {
    					Room adjRoom = getRoom(grid[i][j+1]);
    					grid[i][j].addAdj(adjRoom.getCenterCell());
    					adjRoom.getCenterCell().addAdj(grid[i][j]);
    				}
    			//Add adjacency to room centers with connecting secret passages.
    			} else if(grid[i][j].isRoomCenter()) {
    				char sp = getRoom(grid[i][j]).getHasSecretPassage();
    				if(sp != '0') {
    					grid[i][j].addAdj(getRoom(sp).getCenterCell());
    				}
    			}
    		}
    	}
    	
    	
    }
    
    //Getter for room for cell or initial
    public Room getRoom(char c) {
    	return roomMap.get(c);
    }
    
    public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}
    
    //getter for cell at row/column
    public BoardCell getCell(int row, int col) {
    	return grid[row][col];
    }
    
    //Getters for amount of rows and columns
    public int getNumRows() {
    	return numRows;
    }
    
    public int getNumColumns() {
    	return numColumns;
    }
	
    //Getter for cell adjacency list
	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdj();
	}
	
	//Function for calcTargets which calls recursive function to find all targets
	public void calcTargets(BoardCell cell, int pathLength) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(cell);
		findTargets(cell, pathLength);
	}
	
	//Recursive function that finds all targets
	public void findTargets(BoardCell cell, int pathLength) {
		//Base case: if at end of path, add to targets
		if (pathLength == 0) {
			targets.add(cell);
			return;
		}
		//For each cell in adjList of cell, if the cell is a room, stop iterating on path, else if room is not occupied,
		//add to visited, call function at cell, and remove visited status after completing path.
		for (BoardCell adjCell : cell.getAdj()) {
			if (!visited.contains(adjCell)){
				if (adjCell.isRoomCenter()) {
					targets.add(adjCell);
				} else if (!adjCell.getOccupied()){
					visited.add(adjCell);
					findTargets(adjCell, pathLength - 1);
					visited.remove(adjCell);
				}
			}
		}
	}
	
	//Getter for targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	//Function to create Solution and deal cards to players;
	public void deal() {
		ArrayList<Card> dealDeck = new ArrayList<Card>();
		dealDeck.addAll(deck);
		//Get a random room, person, and weapon for our solution
		Random rand = new Random();
		int randomRoom = rand.nextInt(9);
		int randomPerson = (rand.nextInt(6) + 9);
		int randomWeapon = (rand.nextInt(6) + 15);
		
		theAnswer = new Solution(dealDeck.get(randomRoom), dealDeck.get(randomPerson), dealDeck.get(randomWeapon));
		
		dealDeck.remove(randomWeapon);
		dealDeck.remove(randomPerson);
		dealDeck.remove(randomRoom);
		
		//Deal card to player in sequence then remove card from deal deck.
		int i = 0;
		while (dealDeck.size() != 0) {
			//Deal random card from dealDeck, then remove it from dealDeck.
			int randomCard = rand.nextInt(dealDeck.size());
			players[i].updateHand(dealDeck.get(randomCard));
			dealDeck.remove(randomCard);
			
			i++;
			if (i >= NUM_PLAYERS) {
				i = 0;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawRect(0, 0, 600, 600);
		g.fillRect(0, 0, 600, 600);
		
		int height = 600/numRows;
		int width = 600/numColumns;
		
		//Arrays for Label Cell Draws
		ArrayList<BoardCell> labelCells = new ArrayList<BoardCell>();
		
		//Draw Loop for walkways, rooms, and doors
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				grid[i][j].draw(g, j*width, i*height, width, height);
				//If cell is a labelCell, add cell and current offset to arrays
				if(grid[i][j].isLabel()) {
					labelCells.add(grid[i][j]);
				}
			}
		}
		
		for(Player p : players) {
			p.drawPlayer(g, width, height);
		}
		
		//Call for label draws after board is drawn so that labels are on top.
		int currLabel = 0;
		for(BoardCell cell : labelCells) {
			Room currRoom = roomMap.get(cell.getInitial());
			String label = currRoom.getName();
			cell.drawLabel(g, cell.getCol()*width, cell.getRow()*height, label);
			currLabel++;
		}
	}
    
	public Solution getSolution() {
		return theAnswer;
	}
	
	public Player[] getPlayerList() {
		return players;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Card> getRoomCards() {
		ArrayList<Card> roomCards = new ArrayList<Card>(deck.subList(0, 9));
		return roomCards;
	}
	
	public ArrayList<Card> getPersonCards() {
		ArrayList<Card> personCards = new ArrayList<Card>(deck.subList(9, 15));
		return personCards;
	}
	
	public ArrayList<Card> getWeaponCards() {
		ArrayList<Card> weaponCards = new ArrayList<Card>(deck.subList(15, 21));
		return weaponCards;
	}
	
	public int getNumPlayers() {
		return players.length;
	}
	
	public boolean accusationCheck(Card weapon, Card room, Card person) {
		if (weapon == theAnswer.getWeapon() && room == theAnswer.getRoom() && person == theAnswer.getPerson()) {
			return true;
		} else {
			return false;
		}
	}
}
