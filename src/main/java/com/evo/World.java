package com.evo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class World {

	public int epoch;
	int worldHeight;
	int worldWidth;
	float jungleSteppeRatio;
	ArrayList<ArrayList<Tile>> mapGrid;
	int plantEnergy;
	public List<Animal> livingAnimals;
	public List<Animal> deadAnimals;

	private int jungleLeftBoundary;
	private int jungleRightBoundary;
	private int jungleUpperBoundary;
	private int jungleLowerBoundary;

	public World(int worldHeight, int worldWidth, float jungleSteppeRatio, int startingEnergy, int initialAnimals, int plantEnergy) {
		this.epoch = 0;
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.jungleSteppeRatio = jungleSteppeRatio;
		this.mapGrid = new ArrayList<ArrayList<Tile>>(worldWidth);
		this.plantEnergy = plantEnergy;
		
		// Build world
		int jungleWidth = (int) Math.ceil(Math.sqrt(jungleSteppeRatio) * (float) worldWidth);
		int jungleHeight = (int) Math.ceil(Math.sqrt(jungleSteppeRatio) * (float) worldWidth);
		
		this.jungleLeftBoundary = (worldWidth - jungleWidth) / 2;
		this.jungleRightBoundary = (worldWidth + jungleWidth) / 2;
		this.jungleLowerBoundary = (worldHeight - jungleHeight) / 2;
		this.jungleUpperBoundary = (worldHeight + jungleHeight) / 2;
		
		for (int i = 0; i < worldWidth; i++) {
			ArrayList innerList = new ArrayList<Tile>(worldHeight);
			for (int k = 0; k < worldHeight; k++) {
				if( i < this.jungleLeftBoundary || i > this.jungleRightBoundary ) {
					innerList.add(new SteppeTile(i,k));
				} 
				else {
					if ( k < this.jungleLowerBoundary || k > this.jungleUpperBoundary) {
						innerList.add(new SteppeTile(i,k));
					}
					else {
						innerList.add(new JungleTile(i,k));
					}
				}
			}
			this.mapGrid.add(innerList);
		}
		// Initialize animals
		this.livingAnimals = Stream.
				generate(() -> new Animal(
						startingEnergy,
						this)).
				limit(initialAnimals).
				collect(Collectors.toList());
		this.deadAnimals = new ArrayList<>();
	}

	public ArrayList<ArrayList<Tile>> getMapGrid() {
		return mapGrid;
	}
	public Tile getMapTile(int x, int y){
		return this.mapGrid.get(x).get(y);
	}

	public void removeDeadAnimals() {
		/*
		Removes dead animals from map and from list of currently living animals.
		 */
		for(ArrayList<Tile> l: this.mapGrid) {
			for (Tile t: l) {
				t.animalsOnTile.removeIf( animal -> !animal.isAlive);
			}
		}
		livingAnimals.stream()
				.filter(animal -> !animal.isAlive)
				.forEachOrdered(this.deadAnimals::add);

		this.livingAnimals.removeIf( animal -> !animal.isAlive);
	}

	public void growGrass() {
		Random random = new Random();

		ArrayList<Tile> grasslessJungleTiles = (ArrayList<Tile>) this.mapGrid.stream()
				.flatMap(List::stream)
				.filter(tile -> ! tile.hasGrass)
				.filter(tile -> tile.animalsOnTile.size() == 0)
				.filter(tile -> tile.isJungle)
				.collect(Collectors.toList());
		if( ! grasslessJungleTiles.isEmpty()){
			grasslessJungleTiles.get(random.nextInt(grasslessJungleTiles.size())).hasGrass = true;
		}

		ArrayList<Tile> grasslessSteppeTiles = (ArrayList<Tile>) this.mapGrid.stream()
				.flatMap(List::stream)
				.filter(tile -> ! tile.hasGrass)
				.filter(tile -> tile.animalsOnTile.size() == 0)
				.filter(tile -> ! tile.isJungle)
				.collect(Collectors.toList());
		if( ! grasslessSteppeTiles.isEmpty()){
			grasslessSteppeTiles.get(random.nextInt(grasslessSteppeTiles.size())).hasGrass = true;
		}


	}

	public void feedAnimals(){
		ArrayList<Tile> tilesWithGrassAndAnimals = (ArrayList<Tile>) this.mapGrid.stream()
				.flatMap(List::stream)
				.filter(tile -> tile.hasGrass)
				.filter(tile -> tile.animalsOnTile.size() > 0)
				.collect(Collectors.toList());

		if( ! tilesWithGrassAndAnimals.isEmpty()){
			for(Tile tile: tilesWithGrassAndAnimals){
				ArrayList<Animal> sortedByEnergy = tile.getAnimalsSortedByEnergy();
				int strongestEnergy = sortedByEnergy.get(sortedByEnergy.size()-1).getCurrentEnergy();
				sortedByEnergy.removeIf(animal -> animal.getCurrentEnergy()!= strongestEnergy);
				for(Animal animal: sortedByEnergy){
					animal.consumePlant(this.plantEnergy / sortedByEnergy.size());
				}
				tile.hasGrass = false;
			}
		}
	}

	public void reproduce(){
		ArrayList<Tile> tilesWithMoreThanOneAnimal = (ArrayList<Tile>) this.mapGrid.stream()
				.flatMap(List::stream)
				.filter(tile -> tile.animalsOnTile.size() >= 2)
				.collect(Collectors.toList());

		if( ! tilesWithMoreThanOneAnimal.isEmpty()){
			for(Tile tile: tilesWithMoreThanOneAnimal){

				ArrayList<Animal> sortedByEnergy = tile.getAnimalsSortedByEnergy();
				ArrayList<Animal> capableToReproduct = (ArrayList<Animal>) sortedByEnergy.stream()
						.filter(t -> t.getCurrentEnergy() >= t.getStartingEnergy()/2)
						.collect(Collectors.toList());

				if(capableToReproduct.size()>=2) {
					Animal mom = sortedByEnergy.get(sortedByEnergy.size() - 1);
					Animal dad = sortedByEnergy.get(sortedByEnergy.size() - 2);
					Tile tileForChild = getTileForChild(tile.x, tile.y);
					Animal child = mom.copulate(dad, tileForChild);

					tileForChild.addAnimalOnTile(child);
					this.livingAnimals.add(child);
				}
			}

		}


	}
	private Tile getTileForChild(int xPos, int yPos){
		ArrayList<Tile> nearestTiles = new ArrayList<>();
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos+1)%this.worldWidth).get((this.worldHeight + yPos+1)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos+1)%this.worldWidth).get((this.worldHeight + yPos)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos+1)%this.worldWidth).get((this.worldHeight + yPos-1)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos)%this.worldWidth).get((this.worldHeight + yPos+1)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos)%this.worldWidth).get((this.worldHeight + yPos-1)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos-1)%this.worldWidth).get((this.worldHeight + yPos+1)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos-1)%this.worldWidth).get((this.worldHeight + yPos)%this.worldHeight));
		nearestTiles.add(mapGrid.get((this.worldWidth + xPos-1)%this.worldWidth).get((this.worldHeight + yPos-1)%this.worldHeight));

		ArrayList<Tile> freeNearestTiles = (ArrayList<Tile>) nearestTiles.stream()
				.filter(t -> t.animalsOnTile.size()==0)
				.filter(t -> ! t.hasGrass)
				.collect(Collectors.toList());
		Random random = new Random();
		if (freeNearestTiles.size()>0){
			Tile freeTile = freeNearestTiles.get(random.nextInt(freeNearestTiles.size()));
			return freeTile;
		} else {
			Tile occupiedTile = nearestTiles.get(random.nextInt(nearestTiles.size()));
			return occupiedTile;
		}

	}

	public void printWorld() {
		int xindex = 0;
		System.out.print("     ");
		for(int y=0; y<this.worldWidth; y++){
			System.out.print(y%10);
		}
		System.out.println();
		for(ArrayList<Tile> l: this.mapGrid) {
			if(xindex<10) {
				System.out.print(xindex + "  : ");
			} else{
				System.out.print(xindex + " : ");
			}
			for (Tile t: l) {
				if(t.getClass().getName().equals("com.evo.JungleTile")) {
					if(t.animalsOnTile.size()>0){
						System.out.print("@");
					} else if (t.hasGrass){
						System.out.print("W");
					} else{
						System.out.print(" ");
					}
				} else {
					if(t.animalsOnTile.size()>0){
						System.out.print("@");
					} else if (t.hasGrass) {
						System.out.print("W");
					}
					else
					{
						System.out.print("=");
					}
				}
			}
			xindex++;
			System.out.println();
		}
	}
		
}
