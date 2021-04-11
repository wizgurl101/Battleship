package nhu.phan.gameEntities;

import java.util.ArrayList;

/**
 * Player class contain the playerFleet ArrayList of ship objects, initialize player number of ships and playerFleet with initial values 
 * for the ship objects.
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class Player {
	// ArrayList containing player ship pieces
	private ArrayList<Ship> playerFleet = new ArrayList<Ship>();
			
	// number of ships in player fleet
	private int playerShipNumber;
	
	/**
	 * Constructor initialize playerShipNumber to the size of playerFleet and populate playerFleet with ship objects 
	 */
	public Player() {
		playerShipNumber = playerFleet.size();
		
		// create all the player ships in their fleet and add to playerFleet
		Aircraft aircraft = new Aircraft();
		playerFleet.add(aircraft);
		
		Battleship battleship = new Battleship();
		playerFleet.add(battleship);
		
		Cruiser cruiser = new Cruiser();
		playerFleet.add(cruiser);
		
		Submarine submarine = new Submarine();
		playerFleet.add(submarine);
		
		Destroyer destroyer = new Destroyer();
		playerFleet.add(destroyer);
	}
	
	/**
	 * Return an ArrayList of player ship objects.
	 * @return playerFleet  ArrayList of ship objects
	 */
	public ArrayList<Ship> getPlayerFleet() {
		return playerFleet;
	}
	
	/**
	 * Return the number of ship(s) the player have.
	 * @return playerShipNumber Integer with the value of the number of ship(s) the player have
	 */
	public int getPlayerShipNumber() {
		return playerShipNumber;
	}
	
	/**
	 * Assign playerShipNumber the value of the passed parameter.
	 * @param playerShipNumber Integer with the value of the number of ship(s) the player have
	 */
	public void setPlayerShipNumber(int playerShipNumber) {
		this.playerShipNumber = playerShipNumber;
	}

}
