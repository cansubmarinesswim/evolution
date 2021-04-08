package evolution.classes;

import evolution.classes.ParamsLoader;

public class Main {

	
	public static void main(String[] args) {
		ParamsLoader pm = GsonFromJson.LoadParams();
		System.out.println(pm.initial_animals);
	}	
	
}
