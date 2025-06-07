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
public class Sheep extends Animals {
	
	private int delay;  // number of ticks between moves

	/**
	 * Constructor
	 * 	
	 * @param pasture Reference to the object that created this Entity
	 */
	public Sheep(Pasture pasture) {
		super(pasture, new Color(255,255,255));
		
		// Assign a random delay before next move
		Random rand = new Random();	
		delay = rand.nextInt(5)+1;
	}

}
