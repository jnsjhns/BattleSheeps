package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class EndController {
    @FXML
    private Label winnerLabel;

    public void setWinner(Player winner) {
            Font customFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);
            winnerLabel.setText(winner.getName() + " wins!");
            winnerLabel.setFont(customFont);
            winnerLabel.setStyle("-fx-alignment: center;");
    }
}
