package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.Board;
import at.ac.fhcampuswien.model.Player;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    private StackPane boardContainer; // zentrierter StackContainer für das Spielfeld des aktuellen Spielers
    @FXML
    private StackPane opponentBoardContainer; // zentrierter StackContainer für das Spielfeld des Gegners

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player opponentPlayer;

    private SceneManager sceneManager;

    // Diese Methode wird vom SceneManager aufgerufen, um das Spiel zu initialisieren
    public void initializeGame(Player player1, Player player2, Board player1Board, Board player2Board) {
        this.player1 = player1;
        this.player2 = player2;

        // Zu Beginn ist Spieler 1 der aktuelle Spieler
        this.currentPlayer = player1;
        this.opponentPlayer = player2;

        // Die Boards werden angezeigt
        setBoards(player1Board, player2Board);
    }

    // Setzt die Boards für beide Spieler
    public void setBoards(Board player1Board, Board player2Board) {
        // Das Spielfeld des aktuellen Spielers laden
        boardContainer.getChildren().clear();
        boardContainer.getChildren().add(player1Board.getRoot());

        // Das Spielfeld des Gegners laden
        opponentBoardContainer.getChildren().clear();
        opponentBoardContainer.getChildren().add(player2Board.getRoot());
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
