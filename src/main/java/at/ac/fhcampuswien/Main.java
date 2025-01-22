package at.ac.fhcampuswien;

import at.ac.fhcampuswien.controller.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            System.out.println("Loading FXML...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/scenes/start.fxml"));
            Scene scene = new Scene(loader.load());

            // Set the SceneManager in the controller
            SceneManager sceneManager = new SceneManager(primaryStage);
            StartController controller = loader.getController();
            controller.setSceneManager(sceneManager);

            // Set up the stage
            primaryStage.setTitle("BattleSheeps");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
