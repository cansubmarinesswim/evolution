package evolution.classes;

import java.util.ArrayList;

public abstract class Tile {

	private int x;
	private int y;
	private boolean isJungle;
	private boolean hasGrass;
	private ArrayList<Animal> animalsOnTile;

	public Tile(int x, int y, boolean isJungle) {
		this.x = x;
		this.y = y;
		this.isJungle = isJungle;
		this.hasGrass = false;
		this.animalsOnTile = new ArrayList<Animal>();
	}

	private ArrayList<Animal> getAnimalsOnTile() {
		return animalsOnTile;
	}

	private void setAnimalsOnTile(ArrayList<Animal> animalsOnTile) {
		this.animalsOnTile = animalsOnTile;
	}

	private int getX() {
		return x;
	}

	private int getY() {
		return y;
	}

	private boolean isJungle() {
		return isJungle;
	}
	
	
	
	
	
}
