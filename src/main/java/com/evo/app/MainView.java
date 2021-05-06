package com.evo.app;

import com.evo.Simulation;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

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

    private Simulate simulate;

    private int applicationState = PAUSED;

    public MainView() {

        this.simulation = new Simulation();

        this.worldWidth = this.simulation.parameters.getWorldWidth();
        this.worldHeight = this.simulation.parameters.getWorldHeight();
        this.canvasWidth= 800;
        this.canvasHeight= 800;

        this.toolbar = new Toolbar(this);
        this.infoBar = new InfoBar();
        this.infoBar.setStyle("-fx-border-color: black");
        this.infoBar.setMaxHeight(Double.MAX_VALUE);
        this.infoBar.basicSimInfo.setTile(0,0);

        this.canvas = new Canvas(this.canvasWidth, this.canvasHeight);
        this.canvas.setOnMouseClicked(this::handleTileInfo);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.getChildren().addAll(toolbar, this.canvas, this.infoBar);
        this.affine = new Affine();
        this.affine.appendScale(canvasWidth/worldWidth, canvasHeight/worldHeight);

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

        this.infoBar.tileInfo.setAnimals(simulation.world.getMapTile(tileXPos, tileYPos).toString());
        System.out.println(simulation.world.getMapTile(tileXPos, tileYPos));

    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void draw(){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.fillRect(0,0,canvasWidth,canvasHeight);

            for (int x = 0; x < worldWidth; x++){
                for (int y = 0; y < worldHeight; y++){

                    if(simulation.world.getMapTile(x,y).animalsOnTile.size() != 0){
                        g.setFill(Color.BLACK);
                    } else if (simulation.world.getMapTile(x,y).hasGrass) {
                        g.setFill(Color.GREEN);
                    } else if (simulation.world.getMapTile(x,y).isJungle){
                        g.setFill(Color.DARKGREEN);
                    } else {
                        g.setFill(Color.LIGHTGREEN);
                    }
                    g.fillRect(x,y,1, 1);
                }
            }
        simulation.runEpoch();
        infoBar.basicSimInfo.setAlive(this.simulation.world.livingAnimals.size());
        infoBar.basicSimInfo.setDead(this.simulation.world.deadAnimals.size());
        infoBar.basicSimInfo.setEpoch(this.simulation.world.epoch);

    }

    public void reset(){
        this.simulation = new Simulation();
        this.draw();
    }

    public void setApplicationState(int applicationState) {
        if (this.applicationState == applicationState){
            return;
        }
        if (applicationState == SIMULATING){
            this.simulate = new Simulate(this, this.simulation);
        }
        this.applicationState = applicationState;
        System.out.println("App state: " + this.applicationState);
    }

    public Simulate getSimulate() {
        return simulate;
    }
}
