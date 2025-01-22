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
    private StackPane boardContainer; // centered StackContainer for the game board
    @FXML
    private Label playerLabel; // Label for the current player

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private Sheep currentSheep; // sheep-flock that is currently being placed
    private int currentSheepCount; // count: how many Cells does the current sheep have
    private int maxSheepCount; // Maximum number of sheep-flocks for the current size

    private SceneManager sceneManager;

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = player1; // Spieler 1 beginnt
        startPlacementPhase();
    }

    private void startPlacementPhase() {
        currentSheep = new Sheep(5, 0, 0, true); // Start with XL-flock (5 fields)
        currentSheepCount = 0;
        maxSheepCount = 1; // 1 XL-Flock
        updatePlayerLabel();
        initializeBoard();
    }

    private void initializeBoard() {
        boardContainer.getChildren().clear();

        // Visual representation of the current board
        BoardView currentBoardView = new BoardView(currentPlayer.getBoard());
        boardContainer.getChildren().add(currentBoardView.getCurrentPlayerView());

        addEventHandlers();
    }

    private void addEventHandlers() {
        Board board = currentPlayer.getBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int finalRow = row;
                final int finalCol = col;

                board.getCell(row, col).setOnMouseEntered(event -> handleMouseEnter(finalRow, finalCol));
                board.getCell(row, col).setOnMouseExited(event -> handleMouseExit());
                board.getCell(row, col).setOnMouseClicked(event -> handleMouseClick(finalRow, finalCol, event.getButton()));
            }
        }
    }

    private void updatePlayerLabel() {
        Font alagardFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);

        // Output plural or singular ?
        String flockType;
        switch (currentSheep.getSize()) {
            case 5 -> flockType = "XL-Flock";
            case 4 -> flockType = "L-Flock";
            case 3 -> flockType = "M-Flock";
            case 2 -> flockType = "S-Flock";
            default -> flockType = "Unknown";
        }
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

    private void handleMouseExit() {
        clearPreview();
    }

    private void handleMouseClick(int row, int col, MouseButton button) {
        // Right-click: Change isHoritzontal, only visible if canPlaceSheep
        if (button == MouseButton.SECONDARY) {
            toggleOrientation();
            clearPreview();

            if (currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
                previewSheep(row, col);
            } /*else {
                // System.out.println("Neue Ausrichtung ungueltig!");
            }*/


        // Left-click: placeSheep if canPlaceSheep
        } else if (button == MouseButton.PRIMARY && currentPlayer.getBoard().canPlaceSheep(currentSheep, row, col)) {
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
                cell.setOpacity(0.5);
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
                    cell.setOpacity(1);
                }
            }
        }
    }

    // Handles the placement of a sheep visually
    // Delegates logic to Board class method placeSheep(Sheep sheep)
    private void placeSheep(int row, int col) {
        clearPreview();

        // Create the sheep based on the currentSheep
        Sheep sheep = new Sheep(currentSheep.getSize(), row, col, currentSheep.isHorizontal());

        // Try to place the sheep on the board, delegate placement logic to Board class
        if (currentPlayer.getBoard().placeSheep(sheep)) { // placeSheep returns boolean

            // Update the visual representation
            for (int i = 0; i < sheep.getSize(); i++) {
                int r = sheep.isHorizontal() ? row : row + i;
                int c = sheep.isHorizontal() ? col + i : col;

                Cell cell = currentPlayer.getBoard().getCell(r, c);
                cell.updateView("SHEEP");
            }
        } /* else {
            System.out.println("Invalid placement!");
        } */
    }


    private void switchToNextSheepOrPlayer() {
        // Transition to the next sheep size or player
        if (currentSheep.getSize() > 2) {
            // Reduce sheep size and configure the count for the new size
            currentSheep = new Sheep(currentSheep.getSize() - 1);
            currentSheepCount = 0;
            maxSheepCount = getMaxSheepCountForSize(currentSheep.getSize());
        } else if (currentPlayer == player1) {
            // Switch to player 2 and restart placement phase
            currentPlayer = player2;
            startPlacementPhase();
        } else {
            // Placement complete, transition to the game view
            sceneManager.showGameView(player1, player2);
        }
    }

    private int getMaxSheepCountForSize(int size) {
        return switch (size) {
            case 4 -> 2; // L-Flocks
            case 3 -> 3; // M-Flocks
            case 2 -> 4; // S-Flocks
            default -> throw new IllegalArgumentException("Invalid sheep size: " + size);
        };
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}

