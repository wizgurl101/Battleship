package nhu.phan.Battleship;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import nhu.phan.gameEntities.Player;
import nhu.phan.gameEntities.Ship;
import nhu.phan.gui.ChatGUI;
import nhu.phan.gui.LoginGUI;
import nhu.phan.gui.OpponentBoard;
import nhu.phan.gui.PlayerBoard;
import nhu.phan.gui.Tile;

/**
 * Display the Battleship Client GUI, display message communication between two clients in chat room and handle the Battleship game logic
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class GameClient extends JFrame implements PropertyChangeListener {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JButton startGameButton = new JButton("Start Game");
	private static JButton endGameButton = new JButton("End Game");
	
	// player object in battleship
	private static Player player;
	
	// player name
	private static String playername;
	
	// player fleet of ships
	private static ArrayList<Ship> fleet;
	
	// player number of ships
	private static int playerNumberOfShips;
	private static JLabel playerNum;
	
	private static String playerGuess;
	
	// opponent number of ships
	private static int oppNumberOfShips;
	private static JLabel opponentNum;
	
	// variables used for connecting client to servers
	private static final String SERVER_IP = "localhost";
	private static final int PORT_NUMBER = 9090;
	private static Socket socket;
	private OutputStream outStream;
	private static ObjectOutputStream objOutStream;
	private static String messageSend;
	
	/**
	 * Constructor start login to get username, create a new player object and initialize the GUI
	 */
	public GameClient() {
		new LoginGUI();
		
		// create a player
		player = new Player();
		fleet = player.getPlayerFleet();
		
		playername = LoginGUI.getUsername();
		
		if(playername == null) {
			System.exit(0);
		}
		
		playerNumberOfShips = 5;
		oppNumberOfShips = 5;
		
		generateGUI();
		
		setTitle("Battleship - User: " + playername);
		pack();
//		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	/**
	 * Generate the main window containing the fleet board, target board and chat room
	 */
	private void generateGUI() {
		setLayout(new BorderLayout());
		
		// Chat Area
		JPanel chatPanel = ChatGUI.generateChatPanel();
		add(chatPanel, BorderLayout.EAST);
		
		// Opponent Board
		JPanel opponentPanel = OpponentBoard.generateOpponentBoard();
		add(opponentPanel, BorderLayout.CENTER);
		
		// disable the Opponent Board
		OpponentBoard.disableOpponentBoard();
		
		// Player Board
		JPanel playerPanel = PlayerBoard.generatePlayerBoard();
		add(playerPanel, BorderLayout.WEST);
		
		// labels for player and opponent remaining ship
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(3, 1));
		bottomPanel.setBackground(Color.BLACK);
		
		playerNum = new JLabel("Player Number of Ships: " + playerNumberOfShips);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
		playerNum.setFont(font);
		playerNum.setBackground(Color.BLACK);
		playerNum.setForeground(Color.WHITE);
		playerNum.setOpaque(true);
		playerNum.setHorizontalAlignment(JLabel.CENTER);
		
		opponentNum = new JLabel("Opponent Number of Ships: " + oppNumberOfShips);
		opponentNum.setFont(font);
		opponentNum.setBackground(Color.BLACK);
		opponentNum.setForeground(Color.WHITE);
		opponentNum.setOpaque(true);
		opponentNum.setHorizontalAlignment(JLabel.CENTER);
		
		bottomPanel.add(playerNum);
		bottomPanel.add(opponentNum);
		
		// start and end game buttons
		JPanel start_end_Panel = new JPanel();
		start_end_Panel.setBackground(Color.BLACK);
		font = new Font(Font.MONOSPACED, Font.BOLD, 18);
		start_end_Panel.setBackground(Color.WHITE);
		JPanel startButtonPanel = new JPanel();
		startButtonPanel.setBackground(Color.BLACK);
		
		startGameButton.setFont(font);
		startGameButton.setBackground(Color.WHITE);
		startGameButton.setForeground(Color.BLACK);
		startButtonPanel.add(startGameButton);
		start_end_Panel.add(startButtonPanel);
		
		JPanel endButtonPanel = new JPanel();
		endButtonPanel.setBackground(Color.BLACK);
		
		endGameButton.setFont(font);
		endGameButton.setBackground(Color.WHITE);
		endGameButton.setForeground(Color.BLACK);
		endButtonPanel.add(endGameButton);
		start_end_Panel.add(endButtonPanel);
		bottomPanel.add(start_end_Panel);
		
		add(bottomPanel, BorderLayout.SOUTH);
		
		// place player ships on the board
		for(Ship s: fleet) {
			PlayerBoard.placeShipOnBoard(s);
		}
		
		// listener for ENTER key to send message
		ChatGUI.messageInputArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});
		
		// send button listener
		ChatGUI.sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		
		// start game button listener
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToServer();
			}
		});
		
		// end game button listener
		endGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disconnectFromServer();
			}
		});
		
		// disable disconnect button before connecting to server
		endGameButton.setEnabled(false);
				
	}
	
	/**
	 * Send message input by user to server to be sent to the other client
	 */
	public void sendMessage() {
		String message = ChatGUI.messageInputArea.getText().trim();
		if(message == null) {
			message = "";
		}
		
		// add date and time to message
		Date dateTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  h:mm a");
		String dateTimeStamp = dateFormat.format(dateTime);
		
		// add the > to the message so that user cannot send hit, miss or shipDown to win a game unfairly
		if(!message.isEmpty() && message != null) {
			messageSend = dateTimeStamp + " " + playername + "> " + message;
			
			ChatGUI.messageBoardArea.append(messageSend + "\n");
			
			try {
				objOutStream.writeObject(messageSend);
			} catch (IOException e) {}
			
			// reset message input text area
			ChatGUI.messageInputArea.setText("");
		} 
	}

	/**
	 * Connect client to server
	 */
	private void connectToServer() {		
		// start a connection to the server
		try {
			socket = new Socket(SERVER_IP, PORT_NUMBER);
			
			// disable connect button
			startGameButton.setEnabled(false);
			
			// disable player board
			PlayerBoard.disablePlayerBoard();
			
			// enable opponent board
			OpponentBoard.enableOpponentBoard();
					
			// enable message input are
			ChatGUI.messageInputArea.setEditable(true);
			ChatGUI.messageInputArea.setBackground(Color.WHITE);
			
			// re-enable send and disconnect button
			endGameButton.setEnabled(true);
			ChatGUI.sendButton.setEnabled(true);
			
			// create an object output stream to send messages to the server
			outStream = socket.getOutputStream();
			objOutStream = new ObjectOutputStream(outStream);
			
		} catch (IOException e) {
			int option = JOptionPane.showConfirmDialog(null, "Unable to connect to server.  Would you like to try again?", "Try to connect with server again?" ,JOptionPane.YES_NO_OPTION);
			
			if(option == JOptionPane.YES_OPTION) {
				connectToServer();
			} else {
				System.exit(0);
			}		
			
		}
				
		InputListener inputListener = new InputListener(socket, this, 0);
		// start a new thread
		new Thread(inputListener).start();
	}
	
	/**
	 * Disconnect client from server
	 */
 	public static void disconnectFromServer() {	

		try {
			// send to the other client so that they know a client left the game
			objOutStream.writeObject("quit");
			
			objOutStream.close();
			socket.close();
		
			// re-enable connect button
			startGameButton.setEnabled(true);
			
			// disable send and disconnect button
			endGameButton.setEnabled(false);
			ChatGUI.sendButton.setEnabled(false);
						
			int option = JOptionPane.showConfirmDialog(null, "You left the game.  Would you like play a new game?", "New Game?" ,JOptionPane.YES_NO_OPTION);
			
			if(option == JOptionPane.YES_OPTION) {
				reconnectToServer();
			} else {
				System.exit(0);
			}
			
		} catch (IOException e) {}
				
	}
 	
 	/**
 	 * Method to reconnect client to server when one client left mid-game
 	 */
 	public static void reconnectToServer() {
 		restartGameBoard();
 		
		try {
			// close output stream and socket
			objOutStream.close();
			socket.close();
			
			JOptionPane.showMessageDialog(null, "Click on Start Game Button when you are ready.");
			
		} catch (IOException e) {}
 	}
 	 	
	/**
	 * Notify that a message been sent. Method determine if the message means a game have started, which client play first, the opponent guess a coordinate have
	 * hit, miss or sunk a ship on fleet board or message to the chat room.
	 * @param evt Property that have changed
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String message = (String) evt.getNewValue();
		
		// check if it is the opponent guess of coordinates
		if(message.startsWith("start")) {
			JOptionPane.showMessageDialog(null, "A Battleship Game have started.  It is your turn first.");
			
		} else if(message.startsWith("wait")) {
			OpponentBoard.disableOpponentBoard();
			JOptionPane.showMessageDialog(null, "A Battleship Game have started.  It is your opponent turn first.");
			
		} else if(message.startsWith("guess")) {
			checkGuess(message);
			
		} else if(message.startsWith("hit")) {
			ChatGUI.messageBoardArea.append("It was a HIT!\n");
			
		} else if(message.startsWith("miss")) {
			ChatGUI.messageBoardArea.append("You MISSED!\n");
			
		} else if(message.startsWith("shipDown")) {
			opponentNum.setText("Opponent Number of Ships: " + --oppNumberOfShips);
			
			// when opponent number of ships reach zero, player win the game 
			if(oppNumberOfShips == 0) {				
				int option = JOptionPane.showConfirmDialog(null, "You win.  Would you like play another game?", "New Game?" ,JOptionPane.YES_NO_OPTION);
				
				if(option == JOptionPane.YES_OPTION) {
					reconnectToServer();
				} else {
					System.exit(0);
				}		
				
			} else {
				ChatGUI.messageBoardArea.append("You SUNK a ship!!!\n");
			}
						
		} else if(message.startsWith("quit")) {
			int option = JOptionPane.showConfirmDialog(null, "The other player left the game.  Would you like play another game?", "New Game?" ,JOptionPane.YES_NO_OPTION);
			
			if(option == JOptionPane.YES_OPTION) {
				reconnectToServer();
			} else {
				System.exit(0);
			}	
			
		} else {
			 //displayed the opponent's message to the message board
			ChatGUI.messageBoardArea.append(message + "\n"); 
		}
		
		// enable opponent board so that player cannot click on it to send another coordinates
		OpponentBoard.enableOpponentBoard();
		OpponentBoard.disableBlackTile();
	}
		
	/**
	 * Get the row and column value of the tile the player guess on the board
	 * and send their guess to the server 
	 * @param e ActionEvent trigger by the player mouse click on the opponent game board
	 */
	public static void getTileCoordinate(ActionEvent e) {
		// get the coordinate of the tile user click on
		Tile selectedTile = (Tile) e.getSource();
		int selectedRow = selectedTile.getRow();
		int selectedCol = selectedTile.getCol();
				
		selectedTile.setBackground(Color.BLACK);
		OpponentBoard.disableOpponentBoard();

		playerGuess = "guess," + selectedRow + "," + selectedCol;
		
		try {
			objOutStream.writeObject(playerGuess);
		} catch (IOException e1) {}
	}
		
	/**
	 * checkGuess take the opponent guess row and column values 
	 * @param guess Contain the opponent guess of row and column values
	 */
	private void checkGuess(String guess) {
		// disable opponent board so that player cannot click on it to send another coordinates
		OpponentBoard.disableOpponentBoard();
		
		int guessRow = 0;
		int guessCol = 0;
		
		String[] arrayGuess = guess.split(",");
		guessRow = Integer.parseInt(arrayGuess[1]);
		guessCol = Integer.parseInt(arrayGuess[2]);
				
		// if the opponent guess the right coordinate of a ship
		if(isItAShip(guessRow, guessCol)) {
			// opponent guess the right coordinate of a ship
			ChatGUI.messageBoardArea.append("Your opponent hit a ship!\n");
			sendShipStatusMessage("hit");
			PlayerBoard.tileGrid[guessRow][guessCol].setBackground(Color.BLACK);
			
			if(playerNumberOfShips == 0) {
				int option = JOptionPane.showConfirmDialog(null, "You lost. Would you like play another game?", "New Game?" ,JOptionPane.YES_NO_OPTION);
				
				if(option == JOptionPane.YES_OPTION) {
					reconnectToServer();
				} else {
					System.exit(0);
				}
				
			}
			
		} else {
			// opponent missed
			ChatGUI.messageBoardArea.append("Your opponent missed!\n");
			sendShipStatusMessage("miss");
			PlayerBoard.tileGrid[guessRow][guessCol].setBackground(Color.LIGHT_GRAY);
		}
		
		// enable opponent board so that player cannot click on it to send another coordinates
		OpponentBoard.enableOpponentBoard();
	
	}
	
	/**
	 * Take a coordinate of row and column to determine if a ship occupy the tile at the given coordinate
	 * @param row Contain the row number of tile
	 * @param col Contain the column number of tile
	 * @return isShip If there is a ship at the coordinate given
	 */
	private boolean isItAShip(int row, int col) {
		boolean isShip = false;
		
		// get the background color of the tile on the opponent guessed coordinate
		Color tileColor = PlayerBoard.tileGrid[row][col].getBackground();
		
		for(Ship s: fleet) {
			// check if the ship color match 
			if(s.getShipColor() == tileColor) {
				isShip = true;
				// get the ship health
				int tempHealth = s.getShipHealth();
				// decrease ship health by one
				tempHealth--;
				s.setShipHealth(tempHealth);
				// if ship health is zero, then ship is sink
				if(tempHealth == 0) {
					playerNumberOfShips--;
					playerNum.setText("Player Number of Ships: " + playerNumberOfShips);
					sendShipStatusMessage("shipDown");	
					}
					
				}
			
			}
		
		return isShip;
	}
	
	/**
	 * Send a message to inform opponent they hit, miss or sunk a player battleship
	 */
	private void sendShipStatusMessage(String message) {
		try {
			objOutStream.writeObject(message);
		} catch (IOException e) {}
	}
	
	/**
	 * Restart the game board and reset player number of ships and ship health
	 */
	private static void restartGameBoard() {
		playerNumberOfShips = 5;
		oppNumberOfShips = 5;
				
		// delete all previous player object and create a new one
		player = new Player();
		fleet = player.getPlayerFleet();
		
		PlayerBoard.restartPlayerBoard();
		OpponentBoard.restartOpponentBoard();
		
		// disable the Opponent Board
		OpponentBoard.disableOpponentBoard();
		
		// disable disconnect button before connecting to server
		startGameButton.setEnabled(true);
		endGameButton.setEnabled(false);
		
		playerNum.setText("Player Number of Ships: " + playerNumberOfShips);		
		opponentNum.setText("Opponent Number of Ships: " + oppNumberOfShips);
		
		// place player ships on the board
		for(Ship s: fleet) {
			PlayerBoard.placeShipOnBoard(s);
		}
	}
	
	/**
	 * Move ship to a new location
	 */
	public static void moveShipToNewLocation(ArrayList<Tile> list) {		
		Tile shipTile = new Tile();
		Tile newLocationTile = new Tile();
		
		for(Tile t: list) {
			// if tile background is not white, then it is a ship tile
			if(t.getBackground() != Color.WHITE) {
				shipTile = t;
			} else {
				newLocationTile = t;
			}
		}
		
		int temp = 0;
		// find which ship the player click on
		for(int i = 0; i < fleet.size(); i++) {
			if(fleet.get(i).getShipColor() == shipTile.getBackground()) {
				temp = i;
			}
		}
				
		Ship tempShip = fleet.get(temp);
		int[] tempRow = new int[tempShip.getShipHealth()];
		int[] tempCol = new int[tempShip.getShipHealth()];
		
		//check ship orientation
		if(tempShip.getOrientation().equals("vertical")) {
			int r = newLocationTile.getRow();
			
			for(int i = 0; i < tempShip.getShipHealth(); i++) {
				tempRow[i] = r + i;
				tempCol[i] = newLocationTile.getCol();
			}
			
		}
		
		if(tempShip.getOrientation().equals("horizontal")) {
			int c = newLocationTile.getCol();
			
			for(int i = 0; i < tempShip.getShipHealth(); i++) {
				tempRow[i] = newLocationTile.getRow();
				tempCol[i] = c + i;
			}
		}
				
		try {
			if(isCoordinateValid(tempRow, tempCol)) {
				
				fleet.get(temp).setRows(tempRow);
				fleet.get(temp).setCols(tempCol);
											
				PlayerBoard.repaintPlayerBoard();
				
				// place player ships on the board
				for(Ship s: fleet) {
					PlayerBoard.placeShipOnBoard(s);
				}
								
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Location selected.\nShip cannot be move out of the grid or to location occupied by another ship.");
			}
				
			
			tempShip = null;
			
		} catch(ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Invalid Location selected.\nShip cannot be move out of the grid or to location occupied by another ship.");
		}				
	}
	
	/**
	 * Check if the coordinates of the new tile location is valid to move ship to
	 * @param row The row value of the new tile location 
	 * @param col The column value of the new tile location
	 * @return isValid Return true if ship can move to the new location
	 */
	private static boolean isCoordinateValid(int[] row, int[] col) {
		boolean isValid = true;
		
		// values in row and column cannot be greater than the grid size
		for(int i : row) {
			if(i > PlayerBoard.GRIDSIZE) {
				isValid = false;
			}
		}
		
		for(int i: col) {
			if(i > PlayerBoard.GRIDSIZE) {
				isValid = false;
			}
		}
		
		// check if the tile at the row and column have a ship
		for(int i = 0; i < row.length; i++) {
			int tempR = row[i];
			int tempC = col[i];
			
			// if the tile at that coordinate is not white means it is occupied by a ship
			if(PlayerBoard.tileGrid[tempR][tempC].getBackground() != Color.WHITE) {
				isValid = false;
			}
		}
		
		return isValid;
	}
	
	
}
