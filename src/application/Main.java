package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

/**
 * This class sets up the stage and scene for the UI interface.
 * @author Nicolae Turcan
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Gets the design from the .fxml file
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			// Scene that will be diplayed
			Scene scene = new Scene(root);
			primaryStage.setTitle("Video Player");
			// Mouse click listener
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// Go full-screen on double click
					if(event.getClickCount() == 2){
						primaryStage.setFullScreen(true);
					}
				}
			});
			// Apply the CSS formatting
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// Displays the scene
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Launches the program
		launch(args);
	}
}
