package evolution.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.print.DocFlavor.URL;

import com.google.gson.Gson;

public class ParamsLoader {

	public int initial_animals;
	public int starting_energy;
	public int world_height;
	public int world_width;
	public float jungle_steppe_ratio;
	

	
	public ParamsLoader(int initial_animals, int starting_energy, int world_height, int world_width,
			float jungle_steppe_ratio) {
		this.initial_animals = initial_animals;
		this.starting_energy = starting_energy;
		this.world_height = world_height;
		this.world_width = world_width;
		this.jungle_steppe_ratio = jungle_steppe_ratio;
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