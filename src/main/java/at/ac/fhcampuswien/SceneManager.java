package at.ac.fhcampuswien;

import at.ac.fhcampuswien.controller.*;
import at.ac.fhcampuswien.model.Board;
import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SceneManager {

    private Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // Wechsel zur PlacementView
    public void showPlacementView(Player player1Name, Player player2Name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/view/placement.fxml"));
            Scene scene = new Scene(loader.load());
            PlacementController controller = loader.getController();
            controller.setSceneManager(this);  // SceneManager an den Controller übergeben
            controller.setPlayers(player1Name, player2Name);
            stage.setScene(scene);
        } catch (Exception e) {
            handleError(e);
        }
    }

    // Wechsel zur GameView
    public void showGameView(Player player1Name, Player player2Name, Board player1Ships, Board player2Ships) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/view/game.fxml"));
            Scene scene = new Scene(loader.load());
            GameController controller = loader.getController();
            controller.setSceneManager(this);  // SceneManager an den Controller übergeben
            controller.initializeGame(player1Name, player2Name, player1Ships, player2Ships);
            stage.setScene(scene);
        } catch (Exception e) {
            handleError(e);
        }
    }


    public void showEndView(Player winner) {
        try {
            // Lade die Endview-FXML-Datei
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/view/end.fxml"));
            Scene scene = new Scene(loader.load());
            EndController controller = loader.getController();
            //controller.setWinner(winner);

            // Setze die Szene und zeige sie an
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            handleError(e);
        }
    }

    // Fehlerbehandlungsmethode
    private void handleError(Exception e) {
        // Fehler im Systemprotokoll ausgeben
        e.printStackTrace();

        // Eine Fehlermeldung dem Benutzer anzeigen
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Ein unerwarteter Fehler ist aufgetreten");
        alert.setContentText("Es gab ein Problem beim Laden der Szene: " + e.getMessage());
        alert.showAndWait();
    }
}
