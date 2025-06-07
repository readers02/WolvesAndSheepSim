/**********************************************************
* Program	:  Sheep and Wolves Simulator
* Author	:  Scott Reader
* Due Date	:  May 1st 2020
* Description	:  Simulates sheep and wolves on a grassy pasture
***********************************************************/

package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import simulation.Pasture;

/*
 * Dummy
 * - Sample subclass of Entity
 * - You can turn this into one of the assigned classes,
 *   or just keep it as a sample
 */
public class Animals extends Entity {

	private int delay;  // number of ticks between moves
	public static int timeUntilNotLiving; //how long the animal has until being not alive
	private int birthDelay; //how long the animal is pregnant for
	private boolean preggers = false; //whether or not the animal is pregnant

	/**
	 * Constructor
	 * 	
	 * @param pasture Reference to the object that created this Entity
	 */
	public Animals(Pasture pasture, Color colour) {
		super(pasture, 0, 0, 1, colour);

		// Assign a random delay before next move
		Random rand = new Random();	
		delay = rand.nextInt(5)+1;
		timeUntilNotLiving = 250;
		
	}

	/*
	 *  Timing method
	 *  - Dummy moves once every 1-5 ticks (int delay)
	 */
	@Override
	public void tick(Entity e, int row, int col) {
		// Move when delay counts down to 0
		if(delay-- == 0) {
			move();

			// Assign a random delay before next move
			Random rand = new Random();	
			delay = rand.nextInt(5)+1;
		}
		timeUntilNotLiving--;

		// Other stuff this Entity does goes here

		//checks if the animal is pregnant
		if (preggers) {
			//checks if the animal is ready to give birth
			if(birthDelay-- == 0) {
				//checks if the animal is a sheep or a wolf in order to determine which baby should be made and makes the animal no longer pregnant
				if (e instanceof Wolves) {	
					pasture.babyMaker(new Wolves(pasture), row, col);
					preggers = false;
				}else if (e instanceof Sheep) {
					pasture.babyMaker(new Sheep(pasture), row, col);
					preggers = false;
				}
			}
		}
	}
	
	//makes an animal pregnant and sets the amount of time they will be pregnant for
	public void hasEaten(){
		Random rand = new Random();	
		birthDelay = rand.nextInt(50)+1;
		preggers = true;
	}
	
	//getter for the pregnant variable
	public boolean isPreggers(){
		return preggers;
	}

	/*
	 * Get an ArrayList of available locations and pick one randomly
	 */
	private void move() {	
		Random rand = new Random();	

		ArrayList<int[]> a = pasture.getFreePositions(this);
		if (a.size() > 0) {
			int moveTo = rand.nextInt(a.size());  //choose a random location
			int[] newLoc = a.get(moveTo);
			pasture.moveEntity(this, newLoc);

		}

	}
}
