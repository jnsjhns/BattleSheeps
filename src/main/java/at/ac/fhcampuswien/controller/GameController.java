package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.Board;
import at.ac.fhcampuswien.model.Cell;
import at.ac.fhcampuswien.model.Player;
import at.ac.fhcampuswien.model.Sheep;
import at.ac.fhcampuswien.view.BoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.YELLOW;

public class GameController {

    @FXML
    private StackPane opponentBoardContainer;

    @FXML
    private StackPane currentPlayerBoardContainer;

    @FXML
    private Label opponentLabel;

    @FXML
    private Label currentPlayerLabel;

    private SceneManager sceneManager;
    private Player player1;
    private Player player2;
    private Player currentPlayer; // Dynamisch: Der aktuelle Spieler
    private Player opponentPlayer; // Dynamisch: Der Gegner

    private BoardView opponentBoardView; // Ansicht des gegnerischen Boards
    private BoardView currentPlayerBoardView; // Ansicht des eigenen Boards

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void initializeGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        // Spieler 1 beginnt, Spieler 2 ist der Gegner
        this.currentPlayer = player1;
        this.opponentPlayer = player2;

        setupBoards();
        updateLabels();
    }

    private void setupBoards() {
        opponentBoardView = new BoardView(opponentPlayer.getBoard());
        currentPlayerBoardView = new BoardView(currentPlayer.getBoard());

        opponentBoardContainer.getChildren().clear();
        currentPlayerBoardContainer.getChildren().clear();

        opponentBoardContainer.getChildren().add(opponentBoardView.getOpponentView());
        currentPlayerBoardContainer.getChildren().add(currentPlayerBoardView.getCurrentPlayerView());

        addOpponentBoardEventHandlers();
        disableCurrentPlayerBoardInteractions();
    }

    private void addOpponentBoardEventHandlers() {
        Cell[][] cells = opponentPlayer.getBoard().getCells();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;

                Cell cell = cells[row][col];
                Rectangle cellRect = cell.getRectangle();

                cellRect.setOnMouseEntered(event -> handleMouseEnter(finalRow, finalCol));
                cellRect.setOnMouseExited(event -> handleMouseExit(finalRow, finalCol));
                cellRect.setOnMouseClicked(event -> handleCellClick(finalRow, finalCol));

                cellRect.setMouseTransparent(false);
            }
        }
    }

    private void disableCurrentPlayerBoardInteractions() {
        Cell[][] cells = currentPlayer.getBoard().getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col].getRectangle().setMouseTransparent(true);
            }
        }
    }


    private void updateLabels() {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/alagard.ttf"), 20);

        // Aktualisiere die Beschriftungen mit zentriertem Text und benutzerdefinierter Schriftart
        opponentLabel.setText(opponentPlayer.getName() + "'s Flock");
        opponentLabel.setFont(customFont);
        opponentLabel.setStyle("-fx-alignment: center;");

        currentPlayerLabel.setText("Your Flock");
        currentPlayerLabel.setFont(customFont);
        currentPlayerLabel.setStyle("-fx-alignment: center;");
    }

    private void handleMouseEnter(int row, int col) {
        Cell cell = opponentPlayer.getBoard().getCell(row, col);

        if (!cell.wasSelectedBefore()) {
            cell.getRectangle().setFill(YELLOW);
            cell.getRectangle().setOpacity(0.5);
        }
    }



    private void handleMouseExit(int row, int col) {
        Cell cell = opponentPlayer.getBoard().getCell(row, col);

        if (!cell.wasSelectedBefore()) { // Nur wenn die Zelle noch nicht ausgewählt wurde
            Image grassImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg"));
            cell.getRectangle().setFill(new ImagePattern(grassImage));
            cell.getRectangle().setOpacity(1.0); // Zurück zur normalen Ansicht
        }
    }

    public void handleCellClick(int row, int col) {
        Cell cell = opponentPlayer.getBoard().getCell(row, col);

        if (!cell.wasSelectedBefore()) { // Only proceed if the cell hasn't been selected before
            cell.setWasSelectedBefore(true);

            if (cell.isOccupied()) { // Hit!
                System.out.println("Treffer!");
                cell.updateView("SHEEP_SHORN");


                Sheep sheep = opponentPlayer.getBoard().getSheepAt(row, col);
                if (sheep != null) {
                    System.out.println("Sheep found at (" + row + ", " + col + ") with unshorn parts: " + sheep.notFullyShorn());
                } else {
                    System.out.println("No sheep found at (" + row + ", " + col + ")");
                }
                if (sheep != null) {
                    sheep.shear(); // Shear the sheep

                    if (!sheep.notFullyShorn()) { // Is the sheep fully shorn?
                        System.out.println("Schaf vollständig geschoren!");
                        //markSheepAsShorn(sheep);
                    }
                }

                // Check if all opponent's sheep are fully shorn (win condition)
                if (checkWinCondition(opponentPlayer)) {
                    System.out.println(currentPlayer.getName() + " hat gewonnen!");
                    sceneManager.showEndView(currentPlayer); // Switch to end scene
                    return; // End the game here
                }
            } else { // Miss!
                System.out.println("Kein Treffer!");
                switchPlayers();
            }
        } else {
            System.out.println("Zelle wurde bereits ausgewählt.");
        }
    }


    private void markSheepAsShorn(Sheep sheep) {
        int startRow = sheep.getStartRow();
        int startCol = sheep.getStartCol();
        int size = sheep.getSize();
        boolean isHorizontal = sheep.isHorizontal();

        Image flockShornImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/flock_shorn.jpg"));

        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            Cell cell = opponentPlayer.getBoard().getCell(row, col);
            if (cell != null) {
                cell.updateView("FLOCK_SHORN");
                cell.getRectangle().setFill(new ImagePattern(flockShornImage)); // Aktualisiere visuelle Darstellung
            }
        }
    }

    private boolean checkWinCondition(Player player) {
        for (Sheep sheep : player.getBoard().getSheepList()) {
            System.out.println("Sheep status: size=" + sheep.getSize() + ", unshorn=" + sheep.getUnshorn() + " notFullyShorn(): " + sheep.notFullyShorn());
        }
        return player.getBoard().getSheepList().stream().allMatch(s -> !s.notFullyShorn());
    }


    private void switchPlayers() {
        // normaler Spielerwechsel
        Player temp = currentPlayer;
        currentPlayer = opponentPlayer;
        opponentPlayer = temp;

        // Container und Labels leeren
        opponentBoardContainer.getChildren().clear();
        currentPlayerBoardContainer.getChildren().clear();
        opponentLabel.setText("");
        currentPlayerLabel.setText("");

        // Button für das rechte Feld
        Button changePlayerButton = new Button("Change of player");
        changePlayerButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
        changePlayerButton.setOnAction(e -> {
            setupBoards();
            updateLabels();
        });

        // Button zentriert im Container platzieren
        StackPane buttonContainer = new StackPane(changePlayerButton);
        buttonContainer.setStyle("-fx-background-color: transparent;");
        buttonContainer.setPrefSize(400, 400);
        currentPlayerBoardContainer.getChildren().add(buttonContainer);
    }


}
