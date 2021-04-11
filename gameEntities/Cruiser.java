package nhu.phan.gameEntities;

import java.awt.Color;

/**
 * Cruiser class holds the ship name, ship color, row and columns values of the ship location on the fleet board, ship orientation and
 * the ship health.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class Cruiser extends Ship {
	private static final String SHIPNAME = "Cruiser";
	private static final Color SHIP_COLOR = new Color(0, 204, 0); // is a green color
	
	// Initialize values for the location and ship health for when the player board first is generated
	private static int[] startingRows = {5, 5, 5};
	private static int[] startingCols = {6, 7, 8};
	private static String startingOrientation = "horizontal";
	private static int startingShipHealth = 3;
	
	/**
	 * Constructor assign value to shipName, rows, cols, shipColor, orientation and shipHealth.
	 * @param shipName Ship name
	 * @param rows Integer array that hold the row values of the ship location on fleet board
	 * @param cols Integer array that hold the column values of the ship location on fleet board
	 * @param shipColor String that has the value of the ship color 
	 * @param orientation String that can be either vertical or horizontal to determine how the ship is place on the fleet board
	 * @param shipHealth Integer variable that hold the number of tiles the ship will occupied on the fleet board
	 */
	public Cruiser(String shipName, int[] rows, int[] cols, Color shipColor, String orientation, int shipHealth) {
		super(shipName, rows, cols, shipColor, orientation, shipHealth);

	}
	
	/**
	 * No-arguments constructor that create a aircraft object with initialize values for shipName, rows, cols, shipColor, orientation and shipHealth.
	 */
	public Cruiser() {
		super(SHIPNAME, startingRows, startingCols, SHIP_COLOR, startingOrientation, startingShipHealth);
	}
}
