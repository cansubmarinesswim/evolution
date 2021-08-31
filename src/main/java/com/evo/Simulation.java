package com.evo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

	public Parameters parameters;
	public World world;
	public Animal trackedAnimal;
	public String trackedAnimalInfo;
	public int childCountWhenStarted;
	public int offspringCountWhenStarted;

	public Simulation() {
		this.parameters = GsonFromJson.LoadParams();
		this.world = new World(parameters.getWorldHeight(),
				parameters.getWorldWidth(),
				parameters.getJungleRatio(),
				parameters.getStartingEnergy(),
				parameters.getInitialAnimals(),
				parameters.getPlantEnergy());
		this.trackedAnimal = null;
		this.trackedAnimalInfo = "";
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
		 4. Strongest animals procreate,
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
	}
	public void setTrackedAnimal(int xPos, int yPos){
		List<Animal> animalsOnTileSorted = this.world.getMapTile(xPos, yPos).getAnimalsSortedByEnergy();
		this.trackedAnimal = animalsOnTileSorted.get(animalsOnTileSorted.size() - 1);
		this.childCountWhenStarted = this.trackedAnimal.getChildren().size();
		this.offspringCountWhenStarted = this.trackedAnimal.countTotalOffspring(trackedAnimal, new ArrayList<Animal>());
	}
	public String getTrackedAnimalInfo(){
		if(this.trackedAnimal != null){
			return this.trackedAnimal.toStringForInfoBar(this.childCountWhenStarted, this.offspringCountWhenStarted);
		}
		return "No animal tracked right now";
	}

	public static void main(String[] args) throws InterruptedException {
		Simulation simulation = new Simulation();
		for (int i = 0; i < 10; i++) {
			simulation.runEpoch();
			System.out.println(simulation.world.epoch);
		}
	}
}