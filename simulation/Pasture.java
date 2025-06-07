/**********************************************************
* Program	:  Sheep and Wolves Simulator
* Author	:  Scott Reader
* Due Date	:  May 1st 2020
* Description	:  Simulates sheep and wolves on a grassy pasture
***********************************************************/

package simulation;

import java.util.ArrayList;
import java.util.Random;

import entities.*;

/*
 * Pasture
 * - maintains an array of the field
 * - provides functionality for Entity Objects to interact with the array
 *   (movement, determining nearby objects, etc.)  
 */
public class Pasture {
	
	/*----------PARAMETERS-----------*/	
	// define other starting populations here
	private final int NUM_SHEEP = 100;
	private final int NUM_WOLVES = 60;	
	
		
	/*------------FIELDS-------------*/
	private Entity[][][] entities;  //
	private final int NUM_COLS;
	private final int NUM_ROWS;
	
	/**
	 * Constructor
	 * 
	 * @param NUM_ROWS (int)
	 * @param NUM_COLS (int)
	 */
	public Pasture(int NUM_ROWS, int NUM_COLS) {
		this.NUM_ROWS = NUM_ROWS;
		this.NUM_COLS = NUM_COLS;
		entities = new Entity[NUM_ROWS][NUM_COLS][2];

		// create starting dummy population
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				placeGrass(new Grass(this),row,col); // place grass over the entire pasture
			}
		}
		// initialize other starting populations here		
		for (int i = 0; i < NUM_SHEEP; i++) {
			placeAnimal(new Sheep(this)); // place a sheep
		}
		for (int i = 0; i < NUM_WOLVES; i++) {
			placeAnimal(new Wolves(this)); // place a wolf
		}		
		
	}
	
	//method that produces baby animals (appear above the parent animal)
	public void babyMaker(Entity e, int row, int col) {
		if(inRowRange(row-1) && inColRange(col) && !isOccupied(row-1, col, e)) {
			e.setRow(row-1);
			e.setCol(col);
			entities[row-1][col][1] = e;
		}
	}

	/*
	 *  Determine if a space is already taken by an Entity
	 */
	private boolean isOccupied(int row, int col, Entity e) {
		if(entities[row][col][1] instanceof Sheep && e instanceof Wolves) {
			if(!((Animals) e).isPreggers()) {
				((Animals) e).hasEaten();
			}
			return false;
		}else if (entities[row][col][1] != null) {
			return true;
		}
		return false;
	}

	/*
	 * Methods to determine if a specific row or column is valid
	 */
	private boolean inRowRange(int r) {
		return r >= 0 && r < NUM_ROWS;
	}
	
	private boolean inColRange(int c) {
		return c >= 0 && c < NUM_COLS;
	}
	
	/*
	 * Add an Entity to the array
	 * - highly inefficient for large populations!
	 * - will freeze the program if no spaces remain!
	 */
	
	
	
	private void placeAnimal(Entity e) {
		Random rand = new Random();
		int row = rand.nextInt(NUM_ROWS);
		int col = rand.nextInt(NUM_COLS);

		// Find an empty spot
		while (this.isOccupied(row, col, e)) {
			row = rand.nextInt(NUM_ROWS);
			col = rand.nextInt(NUM_COLS);
		}

		//Place Entity in location (row, col)
		e.setRow(row);
		e.setCol(col);
		entities[row][col][1] = e;
	}
	
	//sets grass at specific locations on the first of the two layers of the 3d array
	private void placeGrass(Entity e, int row, int col) {
		//Place Entity in location (row, col)
		e.setRow(row);
		e.setCol(col);
		entities[row][col][0] = e;
	}
	
	//handles sheep eating grass
	public void  eatGrass(Entity e, int row, int col) {
		//checks to see if there is a sheep on the specified section of grass
		if (e instanceof Grass && entities[row][col][1] instanceof Sheep) {
			//checks to see if the grass in the specified location has been eaten
			if (!((Grass) e).isGrassEaten()) {
				((Grass) e).ingested();
				Entity s = entities[row][col][1];
				//primes the sheep for reproduction if it has not been primed yet
				if(!((Animals) s).isPreggers()) {
					((Animals) s).hasEaten();
				}
			}
		}
	}

	/**
	 * Moves an Entity to a new location
	 * - Note: not currently checking for valid moves 
	 * 
	 * @param e Entity to be moved
	 * @param newLoc int[row, col]
	 */
	public void moveEntity(Entity e, int[] newLoc) {
		// Temporarily store the previous row and column
		int oldRow = e.getRow();
		int oldCol = e.getCol();
		
		// Update this Entity's row and column to the new location
		e.setRow(newLoc[0]);
		e.setCol(newLoc[1]); 
		
		// Move the Entity to the new location and set its old location to null, unless it's time has come
		if (Animals.timeUntilNotLiving > 0) {
			entities[e.getRow()][e.getCol()][1] = e;
			//sets an animals new location to null if it is time for it to die, or if a wolf is moving onto a sheep, setting the sheep's location to null, allowing for the wolf to "eat" the sheep
		}else if(Animals.timeUntilNotLiving <= 0 || entities[oldRow][oldCol][1] instanceof Wolves && entities[e.getRow()][e.getCol()][1] instanceof Sheep) {
			entities[e.getRow()][e.getCol()][1] = null;
			//in the instance that a wolf "eats" a sheep, it checks if they are primed for reproduction, and if they aren't, it primes them
			if (entities[oldRow][oldCol][1] instanceof Wolves && entities[e.getRow()][e.getCol()][1] instanceof Sheep) {
				Entity w = entities[oldRow][oldCol][1];
				if(!((Animals) w).isPreggers()) {
					((Animals) w).hasEaten();
				}
			}
		}
		entities[oldRow][oldCol][1] = null;
	}

	/**
	 * Returns a List of all Entity objects in the array
	 * 
	 * @return ArrayList<Entity>
	 */
	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> a = new ArrayList<Entity>();
		Random rand = new Random();
		
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				for(int lay = 0; lay < 2; lay++) {
					Entity e = entities[row][col][lay];
					if (e != null) {
						// Add to a random location to remove bias
						int p = rand.nextInt(a.size() + 1);
						a.add(p, e);
					}
				}
			}
		}
		return a;
	}
	
	/**
	 * Returns a List of empty positions in the 8 adjacent cells to the target Entity
	 * 
	 * @param e Entity checking for locations in adjacent positions
	 * @return ArrayList<int[row, col]> of positions containing no Entities
	 */
	public ArrayList<int[]> getFreePositions(Entity e) {

		ArrayList<int[]> a = new ArrayList<int[]>();
		
		int row = e.getRow();
		int col = e.getCol();
		
		for(int r = row - 1; r <= row + 1; r++) {
			for(int c = col - 1; c <= col + 1; c++) {
				for(int lay = 0; lay < 2; lay++) {
					if (inRowRange(r) && inColRange(c) && !(r == row && c == col) && !isOccupied(r,c, e)) {
						int[] i = {r,c};
						a.add(i);
					}
				}
			}
		}
		return a;
	}

}
