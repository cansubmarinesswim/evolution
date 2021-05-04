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

	public Parameters(int initialAnimals, int startingEnergy, int worldHeight, int worldWidth, float jungleRatio, int moveEnergy, int plantEnergy) {
		this.initialAnimals = initialAnimals;
		this.startingEnergy = startingEnergy;
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.jungleRatio = jungleRatio;
		this.moveEnergy = moveEnergy;
		this.plantEnergy = plantEnergy;
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
				'}';
	}
}

class GsonFromJson {	
	
	public static Parameters LoadParams() {

		Gson gson = new Gson();
        try (Reader reader = new FileReader("parameters.json")) {
            // Convert JSON File to Java Object
			Parameters paramsLoader = gson.fromJson(reader, Parameters.class);
            return paramsLoader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

}