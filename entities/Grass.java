/**********************************************************
* Program	:  Sheep and Wolves Simulator
* Author	:  Scott Reader
* Due Date	:  May 1st 2020
* Description	:  Simulates sheep and wolves on a grassy pasture
***********************************************************/

package entities;

import java.awt.Color;
import java.util.Random;

import simulation.Pasture;



/*
 * Dummy
 * - Sample subclass of Entity
 * - You can turn this into one of the assigned classes,
 *   or just keep it as a sample
 */
public class Grass extends Entity {
	
	private int delay;  // number of ticks between moves
	private boolean grassEaten = false;
	private int regrowCountdown;

	/**
	 * Constructor
	 * 	
	 * @param pasture Reference to the object that created this Entity
	 */
	public Grass(Pasture pasture) {
		super(pasture, 0, 0, 0, new Color(0, 255, 0));
		
		// Assign a random delay before next move
		Random rand = new Random();	
		delay = rand.nextInt(5)+1;
	}

	/*
	 *  Timing method
	 *  - Dummy moves once every 1-5 ticks (int delay)
	 */
	@Override
	public void tick(Entity e, int row, int col) {
		// Move when delay counts down to 0
		if(delay-- == 0) {
			
			// Assign a random delay
			Random rand = new Random();	
			delay = rand.nextInt(5)+1;
		}
		
		// Other stuff this Entity does goes here
		
		//checks to see if the grass is ready to grow yet
		if (regrowCountdown > 0) {
			regrowCountdown--;
		}else {
			regrowCountdown = 0;
		}
		
		//changes the colour back to green and primes it to be ready for ingestion again
		if (regrowCountdown == 0) {
			grassEaten = false;
			super.setColor(new Color(0, 255, 0));
		}		
	}
	
	//sets the countdown for the grass to regrow and sets the grass to be unedible "dirt"
	public void ingested(){
		regrowCountdown = 25;
		grassEaten = true;
		super.setColor(new Color(139,69,19));
	}
	
	//getter for the grassEaten variable
	public boolean isGrassEaten(){
		return grassEaten;
	}
	
	/*
	 * Get an ArrayList of available locations and pick one randomly
	 */
}
