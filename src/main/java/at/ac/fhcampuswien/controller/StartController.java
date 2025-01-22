package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);
        player1Name.setFont(customFont);
        player2Name.setFont(customFont);

        // Restrict input length to 10 characters
        restrictNameInput(player1Name);
        restrictNameInput(player2Name);
    }

    // Restricts the input in the TextField to a maximum of 10 characters.
    private void restrictNameInput(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > 10) {
                return null; // Reject the change if it exceeds 10 characters
            }
            return change; // Accept the change
        }));
    }

    public void startGame() {
        String name1 = player1Name.getText().trim();
        String name2 = player2Name.getText().trim();

        if (name1.isEmpty()) {
            name1 = "Player 1";
        }
        if (name2.isEmpty()) {
            name2 = "Player 2";
        }

        // Initialize players
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);

        // Scene change to placement
        sceneManager.showPlacementView(player1, player2);
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
