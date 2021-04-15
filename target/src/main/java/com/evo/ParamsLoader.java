package com.evo;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ParamsLoader {

	private int initial_animals;
	private int starting_energy;
	private int world_height;
	private int world_width;
	private float jungle_steppe_ratio;


	public ParamsLoader(int initial_animals, int starting_energy, int world_height, int world_width,
			float jungle_steppe_ratio) {
		this.initial_animals = initial_animals;
		this.starting_energy = starting_energy;
		this.world_height = world_height;
		this.world_width = world_width;
		this.jungle_steppe_ratio = jungle_steppe_ratio;
	}
	
	public int getInitial_animals() {
		return initial_animals;
	}

	public int getStarting_energy() {
		return starting_energy;
	}

	public int getWorld_height() {
		return world_height;
	}

	public int getWorld_width() {
		return world_width;
	}

	public float getJungle_steppe_ratio() {
		return jungle_steppe_ratio;
	}

	@Override
	public String toString() {
		return "ParamsLoader [initial_animals=" + initial_animals + ", starting_energy=" + starting_energy
				+ ", world_height=" + world_height + ", world_width=" + world_width + ", jungle_steppe_ratio="
				+ jungle_steppe_ratio + "]";
	}
	
}

class GsonFromJson {	
	
	public static ParamsLoader LoadParams() {

		Gson gson = new Gson();
        try (Reader reader = new FileReader("parameters.json")) {
            // Convert JSON File to Java Object
            ParamsLoader paramsLoader = gson.fromJson(reader, ParamsLoader.class);
            return paramsLoader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

}