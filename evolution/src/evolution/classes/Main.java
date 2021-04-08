package evolution.classes;

import evolution.classes.ParamsLoader;
import evolution.classes.Animal;


public class Main {

	
	public static void main(String[] args) {
		ParamsLoader pm = GsonFromJson.LoadParams();
		System.out.println(pm);
		
		Animal a = new Animal(pm.getStarting_energy());
		System.out.println(a);
		
		Animal b = new Animal(pm.getStarting_energy());
		System.out.println(b);

		Animal child = a.copulate(b);
		System.out.println("");
		System.out.println("Animal 1: " + a);
		System.out.println("Animal 2: " + b);
		System.out.println("Child: " + child);
		Animal child2 = a.copulate(b);
		System.out.println("Animal 1: " + a);
		System.out.println("Animal 2: " + b);
		System.out.println("Child2: " + child2);
		
	}	
	
}
