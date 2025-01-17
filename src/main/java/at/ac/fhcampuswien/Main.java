package at.ac.fhcampuswien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the custom font 'alagard.ttf'
        Font alagardFont = Font.loadFont(getClass().getResourceAsStream("/fonts/alagard.ttf"), 20);
        if (alagardFont == null) {
            System.err.println("Failed to load font: alagard.ttf");
        }

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/start.fxml"));
        Scene scene = new Scene(loader.load());


        // Set up the stage
        primaryStage.setTitle("BattleSheeps");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
