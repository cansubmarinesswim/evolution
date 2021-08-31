package com.evo.app;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class Toolbar extends VBox {

    private MainView mainView;
    private ToolBar upperTools = new ToolBar();
    private ToolBar lowerTools = new ToolBar();

    TextField reportNameField;
    TextField fromEpochField;
    TextField toEpochField;

    public Toolbar(MainView mainView){

        this.mainView = mainView;

        // Upper Tools
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        Button generateFullReport = new Button("Generate full report");
        generateFullReport.setOnAction(this::handleGenerateFullReport);
        Button generatePartialReport = new Button("Generate partial report");
        generatePartialReport.setOnAction(this::handleGeneratePartialReport);

        Button dominatingGenome = new Button("Dominating genome");
        dominatingGenome.setOnAction(this::handleDominatingGenome);
        this.upperTools.getItems().addAll(
                start,
                stop,
                reset,
                new Separator(),
                dominatingGenome,
                generateFullReport,
                generatePartialReport);


        Label fileName = new Label("Report name:");
        reportNameField = new TextField("report.csv");
        Label fromEpoch = new Label("From epoch:");
        fromEpochField = new TextField("");
        fromEpochField.setMaxWidth(50);
        Label toEpoch = new Label("To epoch:");
        toEpochField = new TextField("");
        toEpochField.setMaxWidth(50);

        this.lowerTools.getItems().addAll(
                fileName,
                reportNameField,
                fromEpoch,
                fromEpochField,
                toEpoch,
                toEpochField
        );
//        this.intervalText = new TextField("500");
//
//        Button interval = new Button("Set interval (ms)");
//        interval.setOnAction(this::handleSetInterval);

        this.getChildren().addAll(upperTools, lowerTools);
    }

    private void handleGeneratePartialReport(ActionEvent actionEvent) {
        if(this.mainView.getApplicationState() == MainView.PAUSED){
            String fileName = this.reportNameField.getText();
            if (!fileName.isEmpty()) {
                try {
                    int fromEpochInt = Integer.parseInt(this.fromEpochField.getText());
                    int toEpochInt = Integer.parseInt(this.toEpochField.getText());

                    if (fromEpochInt < 1){
                        fromEpochInt = 1;
                    }
                    if (toEpochInt > this.mainView.getSimulation().world.epoch){
                        toEpochInt = this.mainView.getSimulation().world.epoch;
                    }
                    System.out.println(fromEpochInt);
                    System.out.println(toEpochInt);
                    if(fromEpochInt >= toEpochInt){
                        throw new NumberFormatException();
                    }

                    this.mainView.getStats().saveSimulationReport(fromEpochInt, toEpochInt, fileName);

                } catch (NumberFormatException e) {
                    System.out.println("Provide proper epoch values for generating partial report");
                }
            }

        }
    }

    private void handleGenerateFullReport(ActionEvent actionEvent) {
        if (this.mainView.getApplicationState() == MainView.PAUSED) {
            String fileName = this.reportNameField.getText();
            if (!fileName.isEmpty()) {
                this.mainView.getStats().saveSimulationReport(fileName);
            }
        }
    }

//    private void handleSetInterval(ActionEvent actionEvent) {
//        if(this.mainView.getApplicationState() == MainView.PAUSED){
//            Integer interval = Integer.valueOf(intervalText.getText());
//            this.mainView.getSimulate().changeSpeed(interval);
//            System.out.println(interval);
//        }
//    }

    private void handleDominatingGenome(ActionEvent actionEvent) {
        if(this.mainView.getApplicationState() == MainView.PAUSED){
            this.mainView.highlightDominatingGenome();
        }
    }

    private void handleStop(ActionEvent actionEvent) {
        if(this.mainView.getApplicationState() == MainView.SIMULATING){
            this.mainView.setApplicationState(MainView.PAUSED);
            this.mainView.getSimulate().stop();
        }
    }

    private void handleStart(ActionEvent actionEvent) {
        this.mainView.setApplicationState(MainView.SIMULATING);
        this.mainView.getSimulate().start();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.mainView.reset();
    }

}
