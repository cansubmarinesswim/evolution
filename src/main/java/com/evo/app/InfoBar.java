package com.evo.app;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoBar extends HBox {

    BasicSimInfo basicSimInfo;
    private static String tileFormat = "Tile: (%d, %d)";
    private static String epochFormat = "Epoch: %d";
    private static String aliveFormat = "Alive: %d";
    private static String deadFormat = "Dead: %d";
    private static String dominatingGenomeFormat = "Dominating genome:\n %s";
    private static String plantsFormat = "Plants: %d";
    TileInfo tileInfo;

    public InfoBar() {
        this.basicSimInfo = new BasicSimInfo();
//        this.basicSimInfo.setStyle("-fx-border-color: black");
        this.basicSimInfo.setStyle("-fx-background-color: #e9eef0");
        this.basicSimInfo.setMinWidth(400);
        this.basicSimInfo.setPrefWidth(400);
        this.basicSimInfo.setMaxWidth(400);
        this.basicSimInfo.setMinHeight(Double.MAX_VALUE);
        this.basicSimInfo.setPrefHeight(Double.MAX_VALUE);
        this.basicSimInfo.setMaxHeight(Double.MAX_VALUE);


        this.tileInfo = new TileInfo();
        this.tileInfo.setStyle("-fx-background-color: #e4e9eb");
//        this.tileInfo.setStyle("-fx-border-color: black");
        this.tileInfo.setMinWidth(400);
        this.tileInfo.setPrefWidth(400);
        this.tileInfo.setMaxWidth(400);
        this.tileInfo.setMinHeight(Double.MAX_VALUE);
        this.tileInfo.setPrefHeight(Double.MAX_VALUE);
        this.tileInfo.setMaxHeight(Double.MAX_VALUE);


        this.getChildren().addAll(basicSimInfo, tileInfo);
    }

    class BasicSimInfo extends VBox{

        private Label tile;
        private Label epoch;
        private Label alive;
        private Label dead;
        private Label dominatingGenome;
        private Label plants;

        public BasicSimInfo() {
            this.tile = new Label();
            this.epoch = new Label();
            this.alive = new Label();
            this.dead = new Label();
            this.plants = new Label();
            this.dominatingGenome = new Label();
            this.dominatingGenome.setWrapText(true);

            this.getChildren().addAll(this.tile, this.epoch, this.alive, this.dead, this.plants, this.dominatingGenome);
        }

        public void setTile(int x, int y){
            this.tile.setText(String.format(tileFormat, x, y));
        }
        public void setEpoch(int epoch){
            this.epoch.setText(String.format(epochFormat, epoch));
        }
        public void setAlive(int alive){
            this.alive.setText(String.format(aliveFormat, alive));
        }
        public void setDead(int dead){
            this.dead.setText(String.format(deadFormat, dead));
        }
        public void setDominatingGenome(String dominatingGenome){
            this.dominatingGenome.setText(String.format(dominatingGenomeFormat, dominatingGenome));
        }
        public void setPlants(int plants){
            this.plants.setText(String.format(plantsFormat, plants));
        }

    }
    class TileInfo extends VBox{

//        private ToolBar toolBar;
        private Label animalTracked;
        private Label fromEpoch;
        private TextField fromEpochField;

        public TileInfo() {
//            this.toolBar = new ToolBar();
//            this.fromEpoch = new Label("From epoch:");
//            fromEpochField = new TextField("");
//            fromEpochField.setMaxWidth(50);
//            this.toolBar.getItems().addAll(this.fromEpoch, this.fromEpochField);


            this.animalTracked = new Label("No animals tracked right now");
            this.animalTracked.setWrapText(true);

//            this.getChildren().addAll(this.toolBar,this.animalTracked);
            this.getChildren().addAll(this.animalTracked);

        }

        public void setTrackedAnimal(String trackedAnimalInfo){
            this.animalTracked.setText(trackedAnimalInfo);
        }

    }

}
