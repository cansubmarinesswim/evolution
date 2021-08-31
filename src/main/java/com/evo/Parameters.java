package com.evo;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Parameters {

	private int initialAnimals;
	private int startingEnergy;
	private int worldHeight;
	private int worldWidth;
	private float jungleRatio;
	private int moveEnergy;
	private int plantEnergy;
	private int simulationSpeed;

	public Parameters(int initialAnimals, int startingEnergy, int worldHeight, int worldWidth, float jungleRatio, int moveEnergy, int plantEnergy, int simulationSpeed) {
		this.initialAnimals = initialAnimals;
		this.startingEnergy = startingEnergy;
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.jungleRatio = jungleRatio;
		this.moveEnergy = moveEnergy;
		this.plantEnergy = plantEnergy;
		this.simulationSpeed = simulationSpeed;
	}

	public int getInitialAnimals() {
		return initialAnimals;
	}

	public int getStartingEnergy() {
		return startingEnergy;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public float getJungleRatio() {
		return jungleRatio;
	}

	public int getMoveEnergy() {
		return moveEnergy;
	}

	public int getPlantEnergy() {
		return plantEnergy;
	}
	public int getSimulationSpeed() {
		return simulationSpeed;
	}

	@Override
	public String toString() {
		return "Parameters{" +
				"initialAnimals=" + initialAnimals +
				", startingEnergy=" + startingEnergy +
				", worldHeight=" + worldHeight +
				", worldWidth=" + worldWidth +
				", jungleRatio=" + jungleRatio +
				", moveEnergy=" + moveEnergy +
				", plantEnergy=" + plantEnergy +
				", simulationSpeed=" + simulationSpeed +
				'}';
	}
}

class GsonFromJson {	
	
	public static Parameters LoadParams() {
		/*
		Load parameters from json file using gson library.
		 */
		Gson gson = new Gson();
        try (Reader reader = new FileReader("parameters.json")) {
			Parameters paramsLoader = gson.fromJson(reader, Parameters.class);
            return paramsLoader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

}