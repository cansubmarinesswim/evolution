package com.evo.app;

import com.evo.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulate {

    private Timeline timeline;
    private MainView mainView;
    private Simulation simulation;
    private int interval; //TODO

    public Simulate(MainView mainView, Simulation simulation) {
        this.mainView = mainView;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::simulateOneEpoch));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
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
