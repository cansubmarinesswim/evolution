package com.evo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

	public Parameters parameters;
	public World world;

	public Simulation() {
		this.parameters = GsonFromJson.LoadParams();
		this.world = new World(parameters.getWorldHeight(),
				parameters.getWorldWidth(),
				parameters.getJungleRatio(),
				parameters.getStartingEnergy(),
				parameters.getInitialAnimals(),
				parameters.getPlantEnergy());
	}

	@Override
	public String toString() {
		return "Simulation{" +
				"parameters=" + parameters +
				", world=" + world +
				", livingAnimals=" + world.livingAnimals.size() +
				", deadAnimals=" + world.deadAnimals.size() +
				'}';
	}

	public void runEpoch(){
		/*
		One epoch in world history
		 1. Dead animals rot,
		 2. Living animals move,
		 3. Strongest animals feast,
				(roślina jest zjadana przez zwierzę posiadające najwięcej energii lub kilka najsilniejszych zwierząt,
				jeśli więcej niż jedno posiada taką samą, największą energię; w takim przypadku energia rośliny jest dzielona),
		 4. Strongest animals procreate,
				(rozmnażają się zawsze dwa zwierzęta o najwyższej energii na danym polu;
				jeśli występuje więcej zwierząt o tej samej energii, wybór jest losowy),
		 5. New plants grow.
 		*/

		// Stage 1
		this.world.removeDeadAnimals();

		// Stage 2
		world.livingAnimals.stream().forEach(a -> a.move(this.parameters.getMoveEnergy()));

		// Stage 3
		world.feedAnimals();

		// Stage 4

		world.reproduce();

		// Stage 5
		this.world.growGrass();
		this.world.epoch++;

		//Debug printing
//		for (Animal a: world.livingAnimals) {
//			System.out.println(a.position + "Energy: " + a.currentEnergy);
//		}
//		for (Animal a: world.deadAnimals) {
//			System.out.println(a.ID);
//		}
//		System.out.println("Total living animals: " + world.livingAnimals.size());
//		System.out.println("Total dead animals: " + world.deadAnimals.size());
//		this.world.printWorld();
	}

	public static void main(String[] args) throws InterruptedException {
		Simulation simulation = new Simulation();
		for (int i = 0; i < 10; i++) {
			simulation.runEpoch();
			System.out.println(simulation.world.epoch);
		}
	}
}

//		Parameters pm = GsonFromJson.LoadParams();
//		System.out.println(pm);
//
//		Animal a = new Animal(pm.getStarting_energy(), pm.getWorld_height(), pm.getWorld_width());
//		System.out.println(a);
//
//		Animal b = new Animal(pm.getStarting_energy(), pm.getWorld_height(), pm.getWorld_width());
//		System.out.println(b);

//		for(int i = 0; i < 200; i++) {
//			System.out.println(a.getPosition());
//			a.move();
//		}

//		World world = new World(pm.getWorld_height(), pm.getWorld_width(), pm.getJungle_steppe_ratio());
//		world.printWorld();

//		Animal child = a.copulate(b);
//		System.out.println("");
//		System.out.println("\nAnimal 1: " + a);
//		System.out.println("\nAnimal 2: " + b);
//		System.out.println("\nChild: " + child);
//		Animal child2 = a.copulate(b);
//		System.out.println("\nAnimal 1: " + a);
//		System.out.println("\nAnimal 2: " + b);
//		System.out.println("\nChild2: " + child2);
//