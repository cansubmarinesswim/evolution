package com.evo.app;

import com.evo.Simulation;
import com.evo.Stats;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox{

    public static final int PAUSED = 0;
    public static final int SIMULATING = 1;

    private InfoBar infoBar;
    private Toolbar toolbar;
    private Canvas canvas;
    private Affine affine;

    int worldWidth;
    int worldHeight;
    int canvasWidth;
    int canvasHeight;

    private Simulation simulation;

    private Stats stats;
    private Simulate simulate;

    private int applicationState = PAUSED;

    public MainView() {

        this.simulation = new Simulation();
        this.stats = new Stats(this.simulation);

        this.worldWidth = this.simulation.parameters.getWorldWidth();
        this.worldHeight = this.simulation.parameters.getWorldHeight();

        float ratio;
        if (this.worldHeight <= this.worldWidth) {
            ratio = (float) worldWidth / (float) worldHeight;
            this.canvasWidth = 800;
            this.canvasHeight = (int) ((float) 800 / ratio);
        } else {
            ratio = (float) worldHeight / (float) worldWidth;
            this.canvasWidth = (int) ((float) 800 / ratio);
            this.canvasHeight = 800;
        }

        this.toolbar = new Toolbar(this);
        this.infoBar = new InfoBar();
        this.infoBar.setMaxHeight(Double.MAX_VALUE);
        this.infoBar.basicSimInfo.setTile(0, 0);

        this.canvas = new Canvas(this.canvasWidth, this.canvasHeight);
        this.canvas.setOnMouseClicked(this::handleTileInfo);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.getChildren().addAll(toolbar, this.canvas, this.infoBar);
        this.affine = new Affine();
        this.affine.appendScale(canvasWidth / worldWidth, canvasHeight / worldHeight);

    }

    private Point2D getSimulationCoords(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        try {
            Point2D tileCoord = this.affine.inverseTransform(mouseX, mouseY);
            return tileCoord;

        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Bad transform");
        }
    }

    private void handleMoved(MouseEvent mouseEvent) {
        Point2D simCoord = getSimulationCoords(mouseEvent);
        this.infoBar.basicSimInfo.setTile((int) simCoord.getX(), (int) simCoord.getY());
    }

    private void handleTileInfo(MouseEvent mouseEvent) {

        Point2D simCoord = getSimulationCoords(mouseEvent);

        int tileXPos = (int) simCoord.getX();
        int tileYPos = (int) simCoord.getY();
        if (this.simulation.world.getMapTile(tileXPos, tileYPos).animalsOnTile.size() != 0) {
            this.simulation.setTrackedAnimal(tileXPos, tileYPos);
        } else {
            this.simulation.trackedAnimal = null;
        }
//        infoBar.tileInfo.setTrackedAnimal(this.simulation.getTrackedAnimalInfo());
        drawWorldMap(this.simulation);
        System.out.println(simulation.world.getMapTile(tileXPos, tileYPos));
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void draw() {

        this.simulation.runEpoch();
        this.drawWorldMap(this.simulation);
        this.stats.trackSimulationProgress();
    }

    public void drawWorldMap(Simulation simulation) {

        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {

                if (simulation.world.getMapTile(x, y).isJungle) {
                    //If tile is jungle - draw it.
                    g.setFill(Color.DARKGREEN);
                    g.fillRect(x, y, 1, 1);
                } else {
                    //if tile is steppe - draw it
                    g.setFill(Color.GREEN);
                    g.fillRect(x, y, 1, 1);
                }

                if (simulation.world.getMapTile(x, y).animalsOnTile.size() != 0) {
                    //If animal on tile - draw it
                    double rectSize = 1;
                    double strongestEnergy = simulation.world.getMapTile(x, y).getAnimalsSortedByEnergy().get(
                            simulation.world.getMapTile(x, y).getAnimalsSortedByEnergy().size() - 1).getCurrentEnergy();
                    if (strongestEnergy < this.simulation.parameters.getStartingEnergy()) {
                        rectSize = strongestEnergy / this.simulation.parameters.getStartingEnergy();

                    }
                    if (simulation.world.getMapTile(x, y).animalsOnTile.contains(simulation.trackedAnimal)) {
                        g.setFill(Color.GOLD);
                    } else {
                        g.setFill(Color.BLACK);
                    }

                    g.fillOval(x + 0.5 - (rectSize / 2), y + 0.5 - (rectSize / 2), rectSize, rectSize);
                } else if (simulation.world.getMapTile(x, y).hasGrass) {
                    //If grass on tile - draw it
                    g.setFill(Color.LIGHTGREEN);
                    g.fillRect(x + 0.25, y + 0.25, 0.5, 0.5);
                }
            }
        }
        infoBar.tileInfo.setTrackedAnimal(this.simulation.getTrackedAnimalInfo());

        infoBar.basicSimInfo.setAlive(this.stats.getTotalLivingAnimals());
        infoBar.basicSimInfo.setDead(this.stats.getTotalDeadAnimals());
        infoBar.basicSimInfo.setEpoch(this.stats.getEpoch());
        infoBar.basicSimInfo.setPlants(this.stats.getTotalPlants());
        infoBar.basicSimInfo.setDominatingGenome(this.stats.getDominatingGenome());

    }

    public void highlightDominatingGenome() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                if (simulation.world.getMapTile(x, y).animalsOnTile.size() != 0) {
                    for (int i = 0; i < simulation.world.getMapTile(x, y).animalsOnTile.size(); i++) {
                        if (simulation.world.getMapTile(x, y).animalsOnTile.get(i).getDna().toString()
                                .equals(simulation.world.dominatingGenome)) {
                            g.setFill(Color.BLUE);
                            g.fillOval(x, y, 1, 1);
                            System.out.println("found");
                        }
                    }
                }
            }
        }
    }

    public void reset() {
        this.simulation = new Simulation();
        this.stats = new Stats(this.simulation);
        this.drawWorldMap(this.simulation);
    }

    public void setApplicationState(int applicationState) {
        if (this.applicationState == applicationState) {
            return;
        }
        if (applicationState == SIMULATING) {
            this.simulate = new Simulate(this, this.simulation, this.simulation.parameters.getSimulationSpeed());
        }
        this.applicationState = applicationState;
    }

    public int getApplicationState() {
        return this.applicationState;
    }

    public Simulate getSimulate() {
        return simulate;
    }

    public Stats getStats() {
        return stats;
    }
}

