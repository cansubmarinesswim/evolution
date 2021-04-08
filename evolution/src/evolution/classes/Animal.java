package evolution.classes;

import java.util.ArrayList;
import java.util.UUID;
import evolution.classes.Dna;


public class Animal {

//	- id zwierzęcia  
//	- koordynat x  
//	- koordynat y  
//	- kierunek zwierzęcia  
//	- energia  
//	- kod genetyczny (niezmienny dla osobnika)  
//	- boolean opisujący czy zyje czy nie  
//	- tablica z id dzieci
	
	public UUID ID;
	public int currentEnergy;
	public int startingEnergy;
	public Dna dna;
	public ArrayList<Animal> offspring;
	boolean isAlive;
	

	public Animal(int startingEnergy) {
		/*
		 * This constructor is for initial pioneer animals that god sent on the earth
		 */
		this.ID = UUID.randomUUID(); 
		this.startingEnergy = startingEnergy;
		this.currentEnergy = startingEnergy;
		this.offspring = new ArrayList<Animal>();
		this.isAlive = true;
		this.dna = new Dna();
	}
	
	public Animal(Animal mom, Animal dad) {
		/*
		 * This constructor is for animals that were a fruit of reproduction
		 */
		this.ID = UUID.randomUUID(); 
		this.offspring = new ArrayList<Animal>();
		this.isAlive = true;
		this.dna = new Dna(mom.getDna(),dad.getDna());
		
		int inherited_energy = (mom.currentEnergy + dad.currentEnergy)/4;
		this.startingEnergy = inherited_energy;
		this.currentEnergy = inherited_energy;
		
	}
	
	
	@Override
	public String toString() {
		return "Animal [ID=" + ID + ", currentEnergy=" + currentEnergy + ", startingEnergy=" + startingEnergy + ", dna="
				+ dna + ", offspring=" + offspring + ", isAlive=" + isAlive + "]";
	}
	
	
	
	public Animal copulate(Animal partner) {
		/*
		 * Perform intercourse with partner that produces a child.
		 */
		Animal child = new Animal(this, partner);
		this.offspring.add(child);
		partner.getOffspring().add(child);
		
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

	public ArrayList<Animal> getOffspring() {
		return offspring;
	}

	public boolean isAlive() {
		return isAlive;
	}

	private void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}
	
	
	
	
	
}
