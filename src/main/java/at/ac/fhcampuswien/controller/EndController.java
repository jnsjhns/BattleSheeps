package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndController {
    @FXML
    private Label winnerLabel;

    @FXML
    public void initialize() {
        // Optional: Initialisierungslogik
    }

    public void setWinner(Player winner) {
        if (winner != null) {
            winnerLabel.setText(winner.getName() + " wins!");
        } else {
            winnerLabel.setText("No winner!");
        }
    }
}
