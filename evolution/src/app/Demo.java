package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Demo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		setupStage(stage);

	}
	
	public static void main(String[] args) {
		launch();
	}
	
	public void setupStage(Stage stage) throws Exception {
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setVgap(10);
		root.setHgap(10);
			
		
		Label greeting = new Label("Siemanko");
		greeting.setTextFill(Color.GREEN);
		greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		root.getChildren().add(greeting);
				
		Scene scene = new Scene(root, 400, 200);
		stage.setTitle("Evolution");
		stage.setScene(scene);
		stage.show();

	}
	

}
