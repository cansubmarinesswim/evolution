package com.evo;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class Animal {
	
	public UUID ID;
	public Coordinates position;

	public int currentEnergy;
	public int startingEnergy;
	public Dna dna;
	public ArrayList<Animal> children;
	boolean isAlive;
	int birthEpoch;
	int deathEpoch;

	private World world;
	private int age;
	
	public Animal(int startingEnergy, World world) {
		/*
		 * This constructor is for initial animals that are generated in epoch 0
		 */
		this.ID = UUID.randomUUID();
		this.world = world;
		this.position = new Coordinates(world);
		this.startingEnergy = startingEnergy;
		this.currentEnergy = startingEnergy;
		this.children = new ArrayList<Animal>();
		this.isAlive = true;
		this.birthEpoch = world.epoch;
		this.deathEpoch = -1;
		this.dna = new Dna();
		this.age = 0;
	}
	
	public Animal(Animal mom, Animal dad, Tile tileForChild) {
		/*
		 * This constructor is for animals that were reproducted
		 */
		//TODO:Add coordinates
		this.ID = UUID.randomUUID();
		this.world = mom.world;
		this.position = new Coordinates(mom.world, tileForChild.x, tileForChild.y);
		this.children = new ArrayList<Animal>();
		this.isAlive = true;
		this.birthEpoch = world.epoch;
		this.deathEpoch = -1;
		this.dna = new Dna(mom.getDna(),dad.getDna());
		
		int inherited_energy = (mom.currentEnergy + dad.currentEnergy)/4;
		this.startingEnergy = inherited_energy;
		this.currentEnergy = inherited_energy;
		this.age = 0;
	}

	@Override
	public String toString() {
		return "Animal{" +
				"ID=" + ID +
				", position=" + position +
				", currentEnergy=" + currentEnergy +
				", startingEnergy=" + startingEnergy +
				", dna=" + dna +
				", children=" + children +
				", isAlive=" + isAlive +
				", birthEpoch=" + birthEpoch +
				", deathEpoch=" + deathEpoch +
				'}';
	}
	public String toStringForInfoBar(int childCountWhenStarted, int offspringCountWhenStarted){
		/*
		String representation used in infobar of the application.
		 */
		int totalChildCount = children.size();
		int newChildCount = totalChildCount - childCountWhenStarted;
		int totalOffspring = countTotalOffspring(this, new ArrayList<Animal>());
		int newOffspring = totalOffspring - offspringCountWhenStarted;

		String animalInfo = "Selected animal information:" +
				"\nTile=" + position +
				"\nEnergy=" + currentEnergy +
				"\nDna=" + dna +
				"\nChildren count=" + totalChildCount + " (+" + newChildCount +")" +
				"\nOffspring count=" + totalOffspring + " (+" + newOffspring +")" +
				"\nAlive=" + isAlive +
				"\nBirth in epoch=" + birthEpoch;
		if (!this.isAlive){
			animalInfo+="\nDied in epoch=" + deathEpoch;
		}
		return animalInfo;
	}

	public void move(int moveEnergy) {
		/*
		 * This method moves animal in his current direction and then spins him.
		 */
		if(this.isAlive){
			//remove animal from tile he is standing on
			this.world.getMapTile(this.position.x, this.position.y).removeAnimalFromTile(this);
			//actually move
			this.position.spin();
			this.position.stepForwad();
			this.age+=1;
			//add animal to tile he landed on
			this.world.getMapTile(this.position.x, this.position.y).addAnimalOnTile(this);

			this.currentEnergy-=moveEnergy;
			if(this.currentEnergy <= 0){
				this.isAlive = false;
			}
		}
	}

	public Animal copulate(Animal partner, Tile tileForChild) {
		/*
		 * Perform intercourse with partner that produces a child.
		 */
		Animal child = new Animal(this, partner, tileForChild);
		this.children.add(child);
		partner.getChildren().add(child);
		
		applyCopulationPenalty(this);
		applyCopulationPenalty(partner);
		
		return child;
	}
	
	private void applyCopulationPenalty(Animal copulatingAnimal) {
		/*
		 * Applies 25% penalty to current_energy.
		 * This function is called when animals are reproducing.
		 */
		int penalty = (int) ((double) copulatingAnimal.getCurrentEnergy()*0.25);
		copulatingAnimal.setCurrentEnergy(copulatingAnimal.getCurrentEnergy() - penalty);
	}

	public void consumePlant(int plantEnergy){
		this.currentEnergy += plantEnergy;
	}
	
	public UUID getID() {
		return ID;
	}

	public int getCurrentEnergy() {
		return currentEnergy;
	}

	public int getStartingEnergy() {
		return startingEnergy;
	}

	public Dna getDna() {
		return dna;
	}
	
	public Coordinates getPosition() {
		return position;
	}

	public ArrayList<Animal> getChildren() {
		return children;
	}

	public boolean isAlive() {
		return isAlive;
	}

	private void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}

	public int countTotalOffspring(Animal animal, ArrayList<Animal> checkedAnimals){
		if (animal.getChildren().size() == 0){
			return 0;
		}
		int counter = 0;
		checkedAnimals.add(animal);
		for (int i = 0; i < animal.getChildren().size(); i++){
			if( ! checkedAnimals.contains(animal.getChildren().get(i))){
				counter += 1 + countTotalOffspring(animal.getChildren().get(i), checkedAnimals);
			}
		}
		return counter;
	}
	
	class Coordinates {
		final int worldHeight;
		final int worldWidth;
		int x;
		int y;
		int direction;
		
		public Coordinates(World world) {
			/*
			This constructor is used for initial animals in epoch 0
			 */
			Random rand = new Random();
			this.worldHeight = world.worldHeight;
			this.worldWidth = world.worldWidth;
			this.x = rand.nextInt(this.worldWidth);
			this.y = rand.nextInt(this.worldHeight);
			this.direction = rand.nextInt(8);
		}

		public Coordinates(World world, int xPos, int yPos) {
			/*
			This constructor is used when animal position is determined
			by its parents tile.
			 */
			Random rand = new Random();
			this.worldHeight = world.worldHeight;
			this.worldWidth = world.worldWidth;
			this.x = xPos;
			this.y = yPos;
			this.direction = rand.nextInt(8);
		}

		private int getX() {
			return x;
		}

		private int getY() {
			return y;
		}

		private void setX(int x) {
			this.x = x;
		}

		private void setY(int y) {
			this.y = y;
		}

		private int getDirection() {
			return direction;
		}

		private void setDirection(int direction) {
			this.direction = direction;
		}

		@Override
		public String toString() {
			return "(x=" + x + ", y=" + y + ", direction=" + direction + ")";
		}
		
		public void stepForwad() {
			/*
			 * This method moves animal one tile in the direction that the animal is facing.
			 */
			
			switch(this.direction) {
				case 0:
					this.y += 1;
					break;
				case 1:
					this.x += 1;
					this.y += 1;
					break;
				case 2:
					this.x += 1;
					break;
				case 3:
					this.x += 1;
					this.y -= 1;
					break;
				case 4:
					this.y -= 1;
					break;
				case 5:
					this.x -= 1;
					this.y -= 1;
					break;
				case 6:
					this.x -= 1;
					break;
				case 7: 
					this.x -= 1;
					this.y += 1;
					break;
			}
			
			this.x = (this.worldWidth + this.x)%this.worldWidth;
			this.y = (this.worldHeight + this.y)%this.worldHeight;
		}
		
		private void spin() {
			/*
			 * This method spins animal based on his genome.
			 */
			ArrayList<Integer> sequence = getDna().getSequence();
			int randomDirection = sequence.get(new Random().nextInt(sequence.size()));
			setDirection((this.direction + randomDirection) % 8);	
		}
	}
	
	
}
