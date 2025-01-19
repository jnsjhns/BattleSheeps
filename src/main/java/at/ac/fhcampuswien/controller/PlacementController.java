package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.*;
import at.ac.fhcampuswien.view.BoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;



public class PlacementController {

    @FXML
    private StackPane boardContainer; // zentrierter StackContainer für das Spielfeld
    @FXML
    private Label playerLabel; // Label für den aktuellen Spieler

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private Sheep currentSheep; // Das aktuell zu platzierende Schaf
    private int currentSheepCount; // Anzahl der aktuellen Schafe
    private int maxSheepCount; // Maximale Anzahl an Schafen für die aktuelle Größe

    private SceneManager sceneManager;

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = player1; // Spieler 1 beginnt
        startPlacementPhase();
    }

    private void startPlacementPhase() {
        currentSheep = new Sheep(5, 0, 0, true); // Beginne mit XL-Flock (5 Felder), Platzhalterposition
        currentSheepCount = 0;
        maxSheepCount = 1; // 1 XL-Flock
        updatePlayerLabel();
        initializeBoard();
    }

    private void initializeBoard() {
        boardContainer.getChildren().clear();

        // Hol das aktuelle Board und seine View
        // Visuelle Darstellung des aktuellen Boards
        BoardView currentBoardView = new BoardView(currentPlayer.getBoard());
        boardContainer.getChildren().add(currentBoardView.getCurrentPlayerView());

        addEventHandlers(); // Event-Handler hinzufügen
    }

    private void addEventHandlers() {
        Board board = currentPlayer.getBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int finalRow = row;
                final int finalCol = col;

                board.getCell(row, col).getRectangle().setOnMouseEntered(event -> handleMouseEnter(finalRow, finalCol));
                board.getCell(row, col).getRectangle().setOnMouseExited(event -> handleMouseExit(finalRow, finalCol));
                board.getCell(row, col).getRectangle().setOnMouseClicked(event -> handleMouseClick(finalRow, finalCol, event.getButton()));
            }
        }
    }

    private void updatePlayerLabel() {
        Font alagardFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);

        String flockType;
        switch (currentSheep.getSize()) {
            case 5 -> flockType = "XL-Flock";
            case 4 -> flockType = "L-Flock";
            case 3 -> flockType = "M-Flock";
            case 2 -> flockType = "S-Flock";
            default -> flockType = "Unknown";
        }

        // ? Plural / Singular ?
        String flockText = (maxSheepCount - currentSheepCount) == 1 ? "" : "s";

        playerLabel.setText(
                currentPlayer.getName() + ", place your sheep! " +
                        (maxSheepCount - currentSheepCount) + " " + flockType + flockText + " left"
        );

        playerLabel.setFont(alagardFont);
    }

    private void handleMouseEnter(int row, int col) {
        if (currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
            previewSheep(row, col);
        }
    }

    private void handleMouseExit(int row, int col) {
        clearPreview();
    }

    private void handleMouseClick(int row, int col, MouseButton button) {
        if (button == MouseButton.SECONDARY) { // Rechtsklick: Ausrichtung ändern
            toggleOrientation();
            clearPreview();

            if (currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
                previewSheep(row, col);
            } else {
                System.out.println("Neue Ausrichtung ungueltig!");
            }
        } else if (button == MouseButton.PRIMARY && currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) { // Linksklick: Platzieren
            placeSheep(row, col);
            currentSheepCount++;
            if (currentSheepCount == maxSheepCount) {
                switchToNextSheepOrPlayer();
            }
            updatePlayerLabel();
        }
    }

    private void toggleOrientation() {
        currentSheep.setHorizontal(!currentSheep.isHorizontal());
    }

    private void previewSheep(int row, int col) {
        clearPreview();

        for (int i = 0; i < currentSheep.getSize(); i++) {
            int r = currentSheep.isHorizontal() ? row : row + i;
            int c = currentSheep.isHorizontal() ? col + i : col;

            if (r >= 0 && r < 10 && c >= 0 && c < 10) {
                Cell cell = currentPlayer.getBoard().getCell(r, c);
                cell.updateView("SHEEP");
                cell.getRectangle().setOpacity(0.5);
            }
        }
    }

    private void clearPreview() {
        Board board = currentPlayer.getBoard();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.isOccupied()) {
                    cell.updateView("GRASS");
                    cell.getRectangle().setOpacity(1);
                }
            }
        }
    }

    private void placeSheep(int row, int col) {
        clearPreview();

        // Erstelle das Schaf basierend auf dem currentSheep
        Sheep sheep = new Sheep(currentSheep.getSize(), row, col, currentSheep.isHorizontal());

        // Versuche, das Schaf im Board zu platzieren
        if (currentPlayer.getBoard().placeSheep(sheep)) { // placeSheep returns boolean

            // Aktualisiere die visuelle Darstellung
            for (int i = 0; i < sheep.getSize(); i++) {
                int r = sheep.isHorizontal() ? row : row + i;
                int c = sheep.isHorizontal() ? col + i : col;

                Cell cell = currentPlayer.getBoard().getCell(r, c);
                cell.updateView("SHEEP");
            }
        } else {
            System.out.println("Schaf konnte nicht platziert werden.");
        }
    }


    private void switchToNextSheepOrPlayer() {
        if (currentSheep.getSize() == 5) { // Von XL zu L
            currentSheep = new Sheep(4);
            currentSheepCount = 0;
            maxSheepCount = 2; // 2 L-Schafe
        } else if (currentSheep.getSize() == 4) { // Von L zu M
            currentSheep = new Sheep(3);
            currentSheepCount = 0;
            maxSheepCount = 3; // 3 M-Schafe
        } else if (currentSheep.getSize() == 3) { // Von M zu S
            currentSheep = new Sheep(2);
            currentSheepCount = 0;
            maxSheepCount = 4; // 4 S-Schafe
        } else if (currentPlayer == player1) { // Wechsel zu Spieler 2
            currentPlayer = player2;
            startPlacementPhase(); // Spieler 2 beginnt von vorne
        } else {
            System.out.println("Placement complete!");

            // DEBUG-Anzeige
            /*
            System.out.println("Player 1's sheep on board:");
            for (Sheep s : player1.getBoard().getSheepList()) {
                System.out.println("Sheep at (" + s.getStartRow() + ", " + s.getStartCol() +
                        ") with size " + s.getSize() +
                        " and unshorn parts: " + s.hasUnshornParts()+ ", horizontal: " + s.isHorizontal());
            }
            System.out.println("Player 2's sheep on board:");
            for (Sheep s : player2.getBoard().getSheepList()) {
                System.out.println("Sheep at (" + s.getStartRow() + ", " + s.getStartCol() +
                        ") with size " + s.getSize() +
                        " and unshorn parts: " + s.hasUnshornParts()+ ", horizontal: " + s.isHorizontal());

             */


            // Szenenwechsel zur game.fxml
            sceneManager.showGameView(player1, player2);
        }
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}

