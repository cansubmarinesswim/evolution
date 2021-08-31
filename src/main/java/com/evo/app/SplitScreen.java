package com.evo.app;

import javafx.scene.layout.HBox;

public class SplitScreen extends HBox {

    private MainView first_screen;
    private MainView second_screen;

    public SplitScreen() {
        this.first_screen = new MainView();
        this.second_screen = new MainView();

        this.first_screen.setStyle("-fx-border-color: black");
        this.second_screen.setStyle("-fx-border-color: black");

        this.getChildren().addAll(this.first_screen, this.second_screen);
    }
}
