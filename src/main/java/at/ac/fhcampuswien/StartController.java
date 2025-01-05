package at.ac.fhcampuswien;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class StartController {

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    public void startGame() {
        String name1 = player1Name.getText().trim();
        String name2 = player2Name.getText().trim();

        // Überprüfen, ob beide Namen eingegeben wurden
        if (name1.isEmpty() || name2.isEmpty()) {
            showAlert("Fehler", "Bitte beide Spielernamen eingeben!");
            return; // Abbruch, wenn Namen fehlen
        }

        try {
            // Spieler erstellen
            Player player1 = new Player(name1);
            Player player2 = new Player(name2);

            // Wechsel zur placement.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/placement.fxml"));
            Scene scene = new Scene(loader.load());

            // Zugriff auf den PlacementController
            PlacementController placementController = loader.getController();
            placementController.setPlayers(player1, player2);

            // Szenenwechsel
            Stage stage = (Stage) player1Name.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
