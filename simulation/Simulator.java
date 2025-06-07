/**********************************************************
* Program	:  Sheep and Wolves Simulator
* Author	:  Scott Reader
* Due Date	:  May 1st 2020
* Description	:  Simulates sheep and wolves on a grassy pasture
***********************************************************/

package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Animals;
import entities.Entity;
import entities.Grass;

/*
 * Simulator
 * - Creates the GUI
 * - ticks every SPEED milliseconds 
 */

@SuppressWarnings("serial")
public class Simulator extends JPanel implements ActionListener {

	/*----------PARAMETERS-----------*/	
	private int SPEED = 100;	// 0 (fast) --> 1000 (slow)
	private final int NUM_ROWS = 60;
	private final int NUM_COLS = 80;
	private final Color DIRT = new Color(139,69,19);  // ground colour
	
	/*------------FIELDS-------------*/
	Pasture pasture;
	Timer time;
	
	/**
	 * Constructor
	 * - Create a new Pasture and initialize timer.
	 * - Adjust animation speed by changing SPEED field above
	 */
	public Simulator() {
		
		//Populate a new world
		this.pasture = new Pasture(NUM_ROWS, NUM_COLS);
		
		//Start the simulation
		time = new Timer(SPEED, this);
		time.start();
	}

	/*
	 * Getters 
	 */
	
	public int getNUM_COLS() {
		return NUM_COLS;
	}
	
	public int getNUM_ROWS() {
		return NUM_ROWS;
	}
	
	
	/**
	 * Update each Entity in the Pasture 
	 */
	@Override
	public void actionPerformed(ActionEvent e1) {
		//1 unit of time passes
		ArrayList<Entity> list = pasture.getEntities();
		
		for(Entity e:list) {
			e.tick(e, e.getRow(), e.getCol());
			//calls the method needed to see if a sheep can eat based on the location sent
			pasture.eatGrass(e, e.getRow(), e.getCol());
		}
		
		//Redraw pasture
		repaint();
	}

	/**
	 * Redraw Screen
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Draw the ground
		g.setColor(DIRT);
		g.fillRect(0, 0, NUM_COLS * 10, NUM_ROWS * 10);
		
		//Draw the entities
		ArrayList<Entity> a = new ArrayList<Entity>();
		a = pasture.getEntities();
		
		//prints the grass to the screen
		for(Entity e:a) {
			g.setColor(e.getColor());
			if (e instanceof Grass/* && pasture.grassEaten == false*/) {
				g.fillRect(e.getCol()*10, e.getRow()*10, 10, 10);
			}
		}

		//prints the animals to the screen
		for(Entity e:a) {
			g.setColor(e.getColor());
			if (e instanceof Animals) {
				g.fillOval(e.getCol()*10, e.getRow()*10, 10, 10);
			}
		}	
	}


}