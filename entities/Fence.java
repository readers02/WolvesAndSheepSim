package entities;

import java.awt.Color;

import simulation.Pasture;

public class Fence extends Entity{

	public Fence(Pasture pasture) {
		super(pasture,0,0,0,new Color(139,69,19));
	}
	
	@Override
	public void tick(Entity e, int row, int col) {
		
	}
	
}
