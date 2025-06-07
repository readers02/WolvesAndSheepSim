/**********************************************************
* Program	:  Sheep and Wolves Simulator
* Author	:  Scott Reader
* Due Date	:  May 1st 2020
* Description	:  Simulates sheep and wolves on a grassy pasture
***********************************************************/

package entities;

import java.awt.Color;

import simulation.Pasture;

/*
 * Entity
 * - The superclass for all the animal & plant objects
 */
abstract public class Entity {
	private int row;
	private int col;
	private int layer;
	private Color colour;
	protected Pasture pasture;  // A reference to the object that created this Entity

	/**
	 * Constructor
	 * 
	 * @param pasture Reference to the object that created this Entity
	 * @param row int
	 * @param col int
	 * @param colour RGB Color
	 */
	public Entity(Pasture pasture, int row, int col, int layer, Color colour) {
		this.row = row;
		this.col = col;
		this.colour = colour;
		this.pasture = pasture;
	}

	/*
	 * Method used for timing
	 * Must be overridden by subclasses
	 */
	abstract public void tick(Entity e, int row, int col);


	public Color getColor() {
		return colour;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLayer() {
		return layer;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setColor(Color colour) {
		this.colour = colour;
	}

}
