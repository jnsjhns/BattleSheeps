package at.ac.fhcampuswien;

import at.ac.fhcampuswien.controller.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            // Load the custom font 'alagard.ttf'
            System.out.println("Loading font...");
            Font alagardFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);
            /*
            if (alagardFont == null) {
                System.err.println("Failed to load font: alagard.ttf");
            } else {
                System.out.println("Custom font loaded successfully.");
            } */


            // Load the FXML file
            System.out.println("Loading FXML...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/view/start.fxml"));
            Scene scene = new Scene(loader.load());
            // System.out.println("FXML loaded successfully.");

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
