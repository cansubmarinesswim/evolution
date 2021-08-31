package com.evo;

import com.evo.app.MainView;
import com.evo.app.SplitScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SplitScreen splitScreen = new SplitScreen();
        Scene scene = new Scene(splitScreen, 1600, 1200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
