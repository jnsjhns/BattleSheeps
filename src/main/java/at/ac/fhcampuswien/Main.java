package at.ac.fhcampuswien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Starting application...");

            // Load the custom font 'alagard.ttf'
            System.out.println("Loading font...");
            Font alagardFont = Font.loadFont(getClass().getResourceAsStream("/fonts/alagard.ttf"), 20);
            if (alagardFont == null) {
                System.err.println("Failed to load font: alagard.ttf");
            } else {
                System.out.println("Custom font loaded successfully.");
            }

            // Load the FXML file
            System.out.println("Loading FXML...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/start.fxml"));
            Scene scene = new Scene(loader.load());
            System.out.println("FXML loaded successfully.");

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
