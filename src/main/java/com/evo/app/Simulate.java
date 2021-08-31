package com.evo.app;

import com.evo.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulate{

    private Timeline timeline;
    private MainView mainView;
    private Simulation simulation;
    private int interval; //TODO

    public Simulate(MainView mainView, Simulation simulation, int simulationSpeed) {
        this.mainView = mainView;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(simulationSpeed), this::simulateOneEpoch));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void changeSpeed(int miliseconds){
        this.timeline = new Timeline(new KeyFrame(Duration.millis(miliseconds), this::simulateOneEpoch));
    }
    private void simulateOneEpoch(ActionEvent actionEvent) {
        this.mainView.draw();
    }
    public void start(){
        this.timeline.play();
    }
    public void stop(){
        this.timeline.stop();
    }

}
