package com.evo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public abstract class Tile {

	public int x;
	public int y;
	public boolean isJungle;
	public boolean hasGrass;
	public ArrayList<Animal> animalsOnTile;

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
	public ArrayList<Animal> getAnimalsSortedByEnergy() {
		ArrayList<Animal> sortedByEnergy = (ArrayList<Animal>) this.animalsOnTile.stream()
				.sorted(Comparator.comparing(Animal::getCurrentEnergy))
				.collect(Collectors.toList());
		return sortedByEnergy;
	}

	@Override
	public String toString() {
		if(animalsOnTile.size() != 0) {
			return "Animals on tile:\n" +
					animalsOnTile;
		} else if (this.hasGrass){
			return "Grass, please don't step!";
		}
		return "Tile has no animals";
	}

	public void addAnimalOnTile(Animal animal) {
		this.animalsOnTile.add(animal);
	}
	public void removeAnimalFromTile(Animal animal){
		this.animalsOnTile.remove(animal);
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
