package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;

public class StartController {

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        // Load the custom font with 20px size
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/alagard.ttf"), 20);

        if (customFont == null) {
            System.err.println("Failed to load font: alagard.ttf");
        } else {
            // Apply the font to the text fields
            player1Name.setFont(customFont);
            player2Name.setFont(customFont);
        }
    }

    public void startGame() {
        String name1 = player1Name.getText().trim();
        String name2 = player2Name.getText().trim();

        // Überprüfen, ob beide Namen eingegeben wurden
        if (name1.isEmpty() || name2.isEmpty()) {
            showAlert("Fehler", "Bitte beide Spielernamen eingeben!");
            return; // Abbruch, wenn Namen fehlen
        }

        // Spieler erstellen
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);

        // Szenenwechsel
        sceneManager.showPlacementView(player1, player2);
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply custom font to the alert dialog content
        alert.getDialogPane().setStyle("-fx-font-family: 'Alagard'; -fx-font-size: 20px;");

        alert.showAndWait();
    }
}
