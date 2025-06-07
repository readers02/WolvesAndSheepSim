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
public class Dummies extends Entity {
	
	private int delay;  // number of ticks between moves
	public static int timeUntilNotLiving;
	private int birthDelay;
	private boolean preggers = false;
	
	/**
	 * Constructor
	 * 	
	 * @param pasture Reference to the object that created this Entity
	 */
	public Dummies(Pasture pasture) {
		super(pasture, 0, 0, 1, new Color(255,75,95));
		
		// Assign a random delay before next move
		Random rand = new Random();	
		delay = rand.nextInt(5)+1;
		timeUntilNotLiving = 500;
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
		// Move when delay counts down to 0
		if (preggers) {
			if(birthDelay-- == 0) {
				if (e instanceof Wolves) {	
					pasture.babyMaker(new Wolves(pasture), row, col);
				}
	
				if (e instanceof Sheep) {
					pasture.babyMaker(new Sheep(pasture), row, col);
				}
			}
		}
	}
	
	public void hasEaten(){
		Random rand = new Random();	
		birthDelay = rand.nextInt(50)+1;
		preggers = true;
	}
	
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
