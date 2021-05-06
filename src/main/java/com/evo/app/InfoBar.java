package com.evo.app;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoBar extends HBox {

    BasicSimInfo basicSimInfo;
    private static String tileFormat = "Tile: (%d, %d)";
    private static String epochFormat = "Epoch: %d";
    private static String aliveFormat = "Alive: %d";
    private static String deadFormat = "Dead: %d";
    private static String dominatingGenomeFormat = "Dominating genome: %s";
    TileInfo tileInfo;

    public InfoBar() {
        this.basicSimInfo = new BasicSimInfo();
        this.basicSimInfo.setStyle("-fx-border-color: black");
        this.basicSimInfo.setMinWidth(200);
        this.basicSimInfo.setMinHeight(300);
        this.tileInfo = new TileInfo();
        this.tileInfo.setStyle("-fx-border-color: black");
        this.tileInfo.setPrefWidth(800);
        this.tileInfo.setMinHeight(300);

        this.getChildren().addAll(basicSimInfo, tileInfo);
    }

    class BasicSimInfo extends VBox{

        private Label tile;
        private Label epoch;
        private Label alive;
        private Label dead;
        private Label dominatingGenome;

        public BasicSimInfo() {
            this.tile = new Label("Tile: ");
            this.epoch = new Label("Epoch: ");
            this.alive = new Label("Alive: ");
            this.dead = new Label("Dead: ");
            this.dominatingGenome = new Label("Dominating genome: ");

            this.getChildren().addAll(this.tile, this.epoch, this.alive, this.dead, this.dominatingGenome);
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
        public void SetDominatingGenome(String dominatingGenome){

        }

    }
    class TileInfo extends VBox{

        private Label animals;

        public TileInfo() {
            this.animals = new Label("Tile: ");
            this.getChildren().addAll(this.animals);
        }

        public void setAnimals(String animals){
            this.animals.setText(animals);
        }

    }

}
