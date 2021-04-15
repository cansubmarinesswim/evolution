package com.evo;

public class Main {
		
	public static void main(String[] args) {
		ParamsLoader pm = GsonFromJson.LoadParams();
		System.out.println(pm);

		Animal a = new Animal(pm.getStarting_energy(), pm.getWorld_height(), pm.getWorld_width());
		System.out.println(a);

		Animal b = new Animal(pm.getStarting_energy(), pm.getWorld_height(), pm.getWorld_width());
		System.out.println(b);

//		for(int i = 0; i < 200; i++) {
//			System.out.println(a.getPosition());
//			a.move();
//		}

		World world = new World(pm.getWorld_height(), pm.getWorld_width(), pm.getJungle_steppe_ratio());
		world.printWorld();

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
	}	
	
}
