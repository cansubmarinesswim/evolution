package com.evo;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.application.Application;

import java.net.URL;

public class Demo extends Application {

	@Override
	public void start(Stage stage) throws Exception {


		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
//		GridPane root = new GridPane();
//		root.setAlignment(Pos.CENTER);
//		root.setVgap(10);
//		root.setHgap(10);
//
//
//		Label greeting = new Label("Siemanko");
//		greeting.setTextFill(Color.GREEN);
//		greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
//		root.getChildren().add(greeting);

		Scene scene = new Scene(root, 800, 400);
		stage.setTitle("Evolution");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
