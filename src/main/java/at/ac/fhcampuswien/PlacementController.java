package at.ac.fhcampuswien;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

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

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = player1; // Spieler 1 beginnt
        startPlacementPhase();
    }

    private void startPlacementPhase() {
        currentSheep = new Sheep(5); // Beginne mit XL-Flock (5 Felder)
        currentSheepCount = 0;
        maxSheepCount = 1; // 1 XL-Flock
        updatePlayerLabel();
        initializeBoard();
    }

    private void initializeBoard() {
        boardContainer.getChildren().clear();
        boardContainer.getChildren().add(currentPlayer.getBoard().getRoot());
        addEventHandlers();
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
        // Bestimme den Typ des aktuellen Flocks basierend auf der Größe
        String flockType;
        switch (currentSheep.getSize()) {
            case 5 -> flockType = "XL-Flock";
            case 4 -> flockType = "L-Flock";
            case 3 -> flockType = "M-Flock";
            case 2 -> flockType = "S-Flock";
            default -> flockType = "Unknown"; // Falls ein Fehler auftritt
        }

        // Singular oder Plural für "Flock(s)"
        String flockText = (maxSheepCount - currentSheepCount) == 1 ? "" : "s";

        // Aktualisiere das Label mit dem neuen Text
        playerLabel.setText(
                currentPlayer.getName() + ", place your sheep! " +
                        (maxSheepCount - currentSheepCount) + " " + flockType + flockText + " left"
        );
    }

    private void handleMouseEnter(int row, int col) {
        if (currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
            previewSheep(row, col); // Vorschau anzeigen
        }
    }

    private void handleMouseExit(int row, int col) {
        clearPreview(); // Entferne alte Vorschau
    }

    private void handleMouseClick(int row, int col, MouseButton button) {
        if (button == MouseButton.SECONDARY) { // Rechtsklick: Ausrichtung ändern
            toggleOrientation();
            clearPreview(); // Alte Vorschau entfernen

            if (currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
                previewSheep(row, col); // Neue Vorschau anzeigen
            } else {
                System.out.println("Neue Ausrichtung ungültig!");
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
        clearPreview(); // Alte Vorschau entfernen

        Image sheepImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep.jpg"));

        for (int i = 0; i < currentSheep.getSize(); i++) {
            int r = currentSheep.isHorizontal() ? row : row + i;
            int c = currentSheep.isHorizontal() ? col + i : col;

            if (r >= 0 && r < 10 && c >= 0 && c < 10) {
                if (currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
                    Cell cell = currentPlayer.getBoard().getCell(r, c);
                    cell.getRectangle().setFill(new ImagePattern(sheepImage)); // Setze das Bild als Vorschau
                    cell.getRectangle().setOpacity(0.5); // Halbe Transparenz für die Vorschau
                }
            }
        }
    }

    private void clearPreview() {
        Board board = currentPlayer.getBoard();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.isOccupied()) {
                    cell.getRectangle().setFill(new ImagePattern(
                            new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg"))
                    ));
                    cell.getRectangle().setOpacity(1.0); // Volle Deckkraft wiederherstellen
                }
            }
        }
    }

    private void placeSheep(int row, int col) {
        clearPreview(); // Entferne die Vorschau

        Image sheepImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep.jpg"));

        for (int i = 0; i < currentSheep.getSize(); i++) {
            int r = currentSheep.isHorizontal() ? row : row + i;
            int c = currentSheep.isHorizontal() ? col + i : col;

            if (r >= 0 && r < 10 && c >= 0 && c < 10) {
                Cell cell = currentPlayer.getBoard().getCell(r, c);
                cell.setOccupied(true);
                cell.getRectangle().setFill(new ImagePattern(sheepImage)); // Setze das Schafbild als Füllung
                cell.getRectangle().setOpacity(1.0); // Volle Deckkraft für gesetzte Schafe
            }
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
            // Szenenwechsel zur nächsten Phase hier einfügen.
        }
    }
}
