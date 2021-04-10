package evolution.classes;

import java.awt.List;
import java.util.ArrayList;

public class World {

	int worldHeight;
	int worldWidth;
	float jungleSteppeRatio;
	ArrayList<ArrayList<Tile>> mapGrid;
	
	public World(int worldHeight, int worldWidth, float jungleSteppeRatio) {
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.jungleSteppeRatio = jungleSteppeRatio;
		this.mapGrid = new ArrayList<ArrayList<Tile>>(worldWidth);
		
		// Build world
		int jungleWidth = (int) Math.ceil(Math.sqrt(jungleSteppeRatio) * (float) worldWidth);
		int jungleHeight = (int) Math.ceil(Math.sqrt(jungleSteppeRatio) * (float) worldWidth);
		System.out.println(jungleWidth);
		System.out.println(jungleHeight);
		
		int jungleLeftBoundary = (worldWidth - jungleWidth) / 2;
		int jungleRightBoundary = (worldWidth + jungleWidth) / 2;
		int jungleLowerBoundary = (worldHeight - jungleHeight) / 2;
		int jungleUpperBoundary = (worldHeight + jungleHeight) / 2;
		
		for (int i = 0; i < worldWidth; i++) {
			ArrayList innerList = new ArrayList<Tile>(worldHeight);
			for (int k = 0; k < worldHeight; k++) {
				if( i < jungleLeftBoundary || i > jungleRightBoundary ) {
					innerList.add(new SteppeTile(i,k));
				} 
				else {
					if ( k < jungleLowerBoundary || k > jungleUpperBoundary) {
						innerList.add(new SteppeTile(i,k));
					}
					else {
						innerList.add(new JungleTile(i,k));
					}
				}
			}
			this.mapGrid.add(innerList);
		}
	}	

	public void printWorld() {
		for(ArrayList<Tile> l: this.mapGrid) {
			for (Tile t: l) {
				if(t.getClass().getName().equals("evolution.classes.JungleTile")) {
					System.out.print("X");
				} else {
					System.out.print("O");
				}
			}
			System.out.println();
		}
	}
		
}
