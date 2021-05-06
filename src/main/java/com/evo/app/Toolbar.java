package com.evo.app;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView mainView;

    public Toolbar(MainView mainView){
        this.mainView = mainView;
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        TextField intervalText = new TextField();
        Button interval = new Button("Set interval (ms)");
//        interval.setOnAction(e -> {
//            Integer intervalValue = Integer.valueOf(intervalText.getText());
//            this.mainView.getSimulate().
//        });


        this.getItems().addAll(start, stop, reset, interval, intervalText);
    }

    private void handleStop(ActionEvent actionEvent) {
        this.mainView.getSimulate().stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        this.mainView.setApplicationState(MainView.SIMULATING);
        this.mainView.getSimulate().start();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.mainView.reset();
    }

}
