package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
    }

    public void startGame() {
        String name1 = player1Name.getText().trim();
        String name2 = player2Name.getText().trim();

        // initialise Players
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);

        // Scene change to placement
        sceneManager.showPlacementView(player1, player2);
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
