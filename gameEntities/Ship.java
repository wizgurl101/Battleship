package nhu.phan.gameEntities;

import java.awt.Color;
import java.util.Arrays;

/**
 * Ship is the parent class for all ship pieces in the battleship game
 * @author Nhu Phan
 * @version October 6, 2020
 */
public abstract class Ship {
	// ship name
	private String shipName;
	
	// store the coordinates of where the ship is placed on the player board
	private int[] rows;
	private int[] cols;
	
	// ship color
	private Color shipColor;
	
	// ship orientation can be either horizontal or vertical
	private String orientation;
	
	// ship health
	private int shipHealth;
	
	/**
	 * Constructor assign values for shipName, rows, cols, shipColor, orientation and shipHealth.
	 * @param shipName Ship name
	 * @param rows Integer array that hold the row values of the ship location on fleet board
	 * @param cols Integer array that hold the column values of the ship location on fleet board
	 * @param shipColor String that has the value of the ship color 
	 * @param orientation String that can be either vertical or horizontal to determine how the ship is place on the fleet board
	 * @param shipHealth Integer variable that hold the number of tiles the ship will occupied on the fleet board
	 */
	public Ship(String shipName, int[] rows, int[] cols, Color shipColor, String orientation, int shipHealth) {
		this.shipName = shipName;
		this.rows = rows;
		this.cols = cols;
		this.shipColor = shipColor;
		this.orientation = orientation;
		this.shipHealth = shipHealth;
	}
	
	/**
	 * Return Integer array of the ship's location row values
	 * @return rows Integer array holding the row values of the ship location on fleet board
	 */
	public int[] getRows() {
		return rows;
	}
	
	/**
	 * Assign rows the value of the passed parameter
	 * @param rows Integer array holding the row values of the ship location on fleet board
	 */
	public void setRows(int[] rows) {
		this.rows = rows;
	}
	
	/**
	 * Return Integer array of the ship's location column values
	 * @return cols Integer array holding the column values of the ship location on fleet board
	 */
	public int[] getCols() {
		return cols;
	}

	/**
	 * Assign cols the value of the passed parameter
	 * @param cols Integer array holding the column values of the ship location on fleet board
	 */
	public void setCols(int[] cols) {
		this.cols = cols;
	}

	/**
	 * Return the color of the ship 
	 * @return shipColor Java Swing Color holding the value of the ship color
	 */
	public Color getShipColor() {
		return shipColor;
	}
	
	/**
	 * Assign shipColor the value of the passed parameter
	 * @param shipColor Java Swing Color holding the value of the ship color
	 */
	public void setShipColor(Color shipColor) {
		this.shipColor = shipColor;
	}
	
	/**
	 * Return the value of orientation
	 * @return orientation String that holds the value of the ship orientation 
	 */
	public String getOrientation() {
		return orientation;
	}
	
	/**
	 * Assign orientation the value of the passed parameter
	 * @param orientation String that holds the value of the ship orientation 
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Return the value of shipHealth 
	 * @return shipHealth Integer that hold the value of the number of tiles the ship occupied on fleet grid
	 */
	public int getShipHealth() {
		return shipHealth;
	}
	
	/**
	 * Assign shipHealth the value of the passed parameter
	 * @param shipHealth Integer that hold the value of the number of tiles the ship occupied on fleet grid
	 */
	public void setShipHealth(int shipHealth) {
		this.shipHealth = shipHealth;
	}

	/**
	 * Return the ship name as a String
	 * @return shipName String holding the value of the ship name
	 */
	public String getShipName() {
		return shipName;
	}

	/**
	 * Assign shipName the value of the passed parameter
	 * @param shipName String holding the value of the ship name
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	/**
	 * Return a String containing the value of shipName, row and column values of the ship location, ship color, ship 
	 * orientation and ship health.
	 */
	@Override
	public String toString() {
		String output = "Ship name: " + getShipName() 
							+ "\nRows Location: " + Arrays.toString(getRows())
							+ "\nCols Location: " + Arrays.toString(getCols())
							+ "\nShip color: " + getShipColor()
							+ "\nShip orientation: " + getOrientation()
							+ "\nShip health: " + getShipHealth();
						
		return output;
	}
}
