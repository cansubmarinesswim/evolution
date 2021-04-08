package evolution.classes;

import java.util.ArrayList;
import java.util.UUID;


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
	
	
	public Animal() {
		this.ID = UUID.randomUUID();
		//this.startingEnergy = 
	}
	
	
	
}
