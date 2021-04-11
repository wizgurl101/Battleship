package nhu.phan.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

/**
 * create JButton that can hold values for row and col which is used to make a grid of a coordinate system
 * @author Nhu Phan
 * @version October 6, 2020
 */
public class Tile extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row = 0;
	private int col = 0;
	
	/**
	 * No-arguments constructor 
	 */
	public Tile() {}
	
	/**
	 * Constructor assign row and col the values of the passed parameters, set tile background color and preferred dimension size.
	 * @param row The row value of where the tile is located on the grid
	 * @param col The column value of where the tile is located on the grid
	 */
	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		
		setBackground(Color.WHITE);;
		Dimension size = new Dimension(50, 50);
		setPreferredSize(size);
	}

	/**
	 * Return the row tile is located at.
	 * @return row The row value of where the tile is located on the grid
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Assign the row of the tile the value of the passed parameter.
	 * @param row The row value of where the tile is located on the grid
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Return the col tile is located at.
	 * @return col The column value of where the tile is located on the grid
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Assign the column of the tile the value of the passed parameter.
	 * @param col The column value of where the tile is located on the grid
	 */
	public void setCol(int col) {
		this.col = col;
	}
}
