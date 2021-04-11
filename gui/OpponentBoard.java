package nhu.phan.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nhu.phan.Battleship.GameClient;

/**
 * OpponentBoard class generate the opponent board GUI and have methods to disable, enable and restore the opponent board to it initial state and
 * to disable a single tile on the opponent board.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class OpponentBoard {
	public static final int GRIDSIZE = 10;
	public static Tile[][] tileGrid = new Tile[GRIDSIZE][GRIDSIZE];
	
	/**
	 * Generate the opponent board GUI
	 * @return opponentBoard JPanel containing the opponent board GUI components 
	 */
	public static JPanel generateOpponentBoard() {
		JPanel opponentBoard = new JPanel();
		opponentBoard.setLayout(new BorderLayout());
		
		// title label
		JLabel titleLabel = new JLabel("Target Board");
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
		titleLabel.setFont(font);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		opponentBoard.add(titleLabel, BorderLayout.PAGE_START);
		opponentBoard.add(titleLabel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
		opponentBoard.add(centerPanel, BorderLayout.CENTER);
		
		// make a 10x10 grid
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				tileGrid[row][col] = new Tile(row, col);
				
				// display grid
				centerPanel.add(tileGrid[row][col]);
				
				// listener to get tile row and col when user click on it
				tileGrid[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GameClient.getTileCoordinate(e);
					}
				});
			}
		}
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.BLACK);
		opponentBoard.add(emptyPanel, BorderLayout.WEST);
		
		JPanel emptyPanel1 = new JPanel();
		emptyPanel1.setBackground(Color.BLACK);
		opponentBoard.add(emptyPanel1, BorderLayout.EAST);
		
		return opponentBoard;
	}
	
	
	/**
	 *  Disable the opponent board
	 */
	public static void disableOpponentBoard() {
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				
				// remove the Action Listener on the tile of player board
				tileGrid[row][col].setEnabled(false);
			}
		}
	}
	
	/**
	 *  Enable the opponent board
	 */
	public static void enableOpponentBoard() {
		for(int row = 0; row < 10; row++) {
			for(int col = 0; col < 10; col++) {
				
				// remove the Action Listener on the tile of player board
				tileGrid[row][col].setEnabled(true);
			}
		}
	}
	
	/**
	 * Restore the opponent board to it initial state for a new game
	 */
	public static void restartOpponentBoard() {
		// place ship on the board
		for(int row = 0; row < tileGrid.length; row++) {
			for(int col = 0; col < tileGrid.length; col++) {
				
				tileGrid[row][col].setBackground(Color.WHITE);
				tileGrid[row][col].setEnabled(true);
			}
		}
	}
	
	/**
	 * Disable any tile that is black which is the tile that the player click to guess the coordinate of their opponent ship
	 */
	public static void disableBlackTile() {
		// place ship on the board
		for(int row = 0; row < tileGrid.length; row++) {
			for(int col = 0; col < tileGrid.length; col++) {
				
				if(tileGrid[row][col].getBackground() == Color.BLACK) {
					tileGrid[row][col].setEnabled(false);
				}
			}
		}
	}
}
