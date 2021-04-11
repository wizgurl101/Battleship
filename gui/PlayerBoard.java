package nhu.phan.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nhu.phan.Battleship.GameClient;
import nhu.phan.gameEntities.*;

/**
 * PlayerBoard class generate the player board and have method to paint the ship on the board, repaint the board, disable & enable the board, restore
 * the board to it initial state and clear the tileList ArrayList used to paint ship to a new location.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class PlayerBoard {
	public static final int GRIDSIZE = 10;
	public static Tile[][] tileGrid = new Tile[GRIDSIZE][GRIDSIZE];
	
	// ArrayList to contain the two values for old coordinate of a ship and the new coordinate to move ship to
	private static ArrayList<Tile> tileList = new ArrayList<Tile>(2);
	
	/**
	 * Generate the player board GUI
	 * @return playerBoard JPanel contain the player board
	 */
	public static JPanel generatePlayerBoard() {
		JPanel playerBoard = new JPanel();
		playerBoard.setLayout(new BorderLayout());
		
		// title label
		JLabel titleLabel = new JLabel("Fleet Board");
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
		titleLabel.setFont(font);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		playerBoard.add(titleLabel, BorderLayout.PAGE_START);
		playerBoard.add(titleLabel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
		playerBoard.add(centerPanel, BorderLayout.CENTER);
		
		// make a 10x10 grid
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				tileGrid[row][col] = new Tile(row, col);
				
				// display grid
				centerPanel.add(tileGrid[row][col]);
				
				// listener to get tile row and col when user click on it
				tileGrid[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Tile selectedTile = (Tile) e.getSource();
						
						// if two white tiles get selected clear tileList 
						if(tileList.size() == 2 && tileList.get(0).getBackground() == Color.WHITE && tileList.get(1).getBackground() == Color.WHITE) {
							tileList.clear();
						}
						
						// check to see if a ship is click on by player
						if(selectedTile.getBackground() != Color.WHITE) {
							tileList.add(selectedTile);
						} else {
							tileList.add(selectedTile);
						}
						
						// check if there is a ship tile and a non-ship tile selected
						if((tileList.size() == 2 && tileList.get(0).getBackground() == Color.WHITE && tileList.get(1).getBackground() != Color.WHITE)
								|| (tileList.size() == 2 && tileList.get(0).getBackground() != Color.WHITE && tileList.get(1).getBackground() == Color.WHITE)) {
							
							GameClient.moveShipToNewLocation(tileList);
							
							tileList.clear();
						}
						
						// check if there both tile have ship on it
						if(tileList.size() == 2 && tileList.get(0).getBackground() != Color.WHITE && tileList.get(1).getBackground() != Color.WHITE) {
							JOptionPane.showMessageDialog(null, "Invalid location. Ship cannot overlap each other.");
							
							tileList.clear();
						}
					}
				});
			}
		}
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.BLACK);
		playerBoard.add(emptyPanel, BorderLayout.EAST);
		
		JPanel emptyPanel1 = new JPanel();
		emptyPanel1.setBackground(Color.BLACK);
		playerBoard.add(emptyPanel1, BorderLayout.WEST);
				
		return playerBoard;
	}
	
	/**
	 * Clear the ArrayList of two tiles, tileList
	 */
	public static void clearTileList() {
		tileList.clear();
	}
	
	/**
	 * Paint the ship pieces on the board once player board
	 */
	public static void placeShipOnBoard(Ship ship) {
		// get ship row and col
		int[] shipRows = ship.getRows();
		int[] shipCols = ship.getCols();
		
		// place ship on the board		
		for(int i = 0; i < shipRows.length; i++) {
				int row = shipRows[i];
				int col = shipCols[i];
				
				tileGrid[row][col].setBackground(ship.getShipColor());
		}
	}
	
	/**
	 * Repaint the player board to white
	 */
	public static void repaintPlayerBoard() {
		for(int row = 0; row < GRIDSIZE; row++) {
			for(int col = 0; col < GRIDSIZE; col++) {
				tileGrid[row][col].setBackground(Color.WHITE);
			}
		}
	}
	
	/**
	 *  Disable the player board
	 */
	public static void disablePlayerBoard() {
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				
				// remove the Action Listener on the tile of player board
				tileGrid[row][col].setEnabled(false);
			}
		}
	}
	
	/**
	 * Return player board to it initial state of having white tile and enabling the actionListener to each tile
	 */
	public static void restartPlayerBoard() {
		// place ship on the board
		for(int row = 0; row < tileGrid.length; row++) {
			for(int col = 0; col < tileGrid.length; col++) {
				
				tileGrid[row][col].setBackground(Color.WHITE);
				tileGrid[row][col].setEnabled(true);
			}
		}
	}
	
}
