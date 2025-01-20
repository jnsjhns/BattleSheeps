package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.SceneManager;
import at.ac.fhcampuswien.model.Cell;
import at.ac.fhcampuswien.model.Player;
import at.ac.fhcampuswien.model.Sheep;
import at.ac.fhcampuswien.view.BoardView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Objects;


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
    private Player currentPlayer; // Dynamisch: Der aktuelle Spieler
    private Player opponentPlayer; // Dynamisch: Der Gegner
    private boolean isFirstSwitch = true; // Initialzustand: erster Wechsel

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void initializeGame(Player player1, Player player2) {
        this.currentPlayer = player1;
        this.opponentPlayer = player2;

        // start with switch-screen
        switchPlayers();
    }

    private void setupBoards() {
        // Ansicht des gegnerischen Boards
        BoardView opponentBoardView = new BoardView(opponentPlayer.getBoard());
        // Ansicht des eigenen Boards
        BoardView currentPlayerBoardView = new BoardView(currentPlayer.getBoard());

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
                cell.setOnMouseEntered(event -> handleMouseEnter(finalRow, finalCol));
                cell.setOnMouseExited(event -> handleMouseExit(finalRow, finalCol));
                cell.setOnMouseClicked(event -> handleCellClick(finalRow, finalCol));

                cell.setMouseTransparent(false);
            }
        }
    }

    // disable Interactions on current Player Board View
    private void disableCurrentPlayerBoardInteractions() {
        Cell[][] cells = currentPlayer.getBoard().getCells();
        for (Cell[] cell : cells) {
            for (Cell value : cell) {
                value.setMouseTransparent(true);
            }
        }
    }


    private void updateLabels() {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);

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
            // Set the image as the fill for the rectangle
            cell.updateView("SELECTION");
        }
    }



    private void handleMouseExit(int row, int col) {
        Cell cell = opponentPlayer.getBoard().getCell(row, col);

        if (!cell.wasSelectedBefore()) {
            cell.updateView("GRASS");
            cell.setOpacity(1.0);
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
                /*
                // DEBUG-Ausgabe
                if (sheep != null) {
                    System.out.println("Sheep found at (" + row + ", " + col + ") with unshorn parts: " + sheep.hasUnshornParts());
                } else {
                    System.out.println("No sheep found at (" + row + ", " + col + ")");
                } */
                if (sheep != null) {
                    sheep.shear(); // Shear the sheep

                    if (sheep.isFullyShorn()) { // Is the sheep fully shorn?
                        // System.out.println("Sheep fully shorn!");
                        markSheepAsShorn(sheep);
                    }
                }

                // Check if all opponent's sheep are fully shorn (win condition)
                if (checkWinCondition(opponentPlayer)) {
                    System.out.println(currentPlayer.getName() + " wins!");
                    sceneManager.showEndView(currentPlayer); // Switch to end scene
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

        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            Cell cell = opponentPlayer.getBoard().getCell(row, col);
            if (cell != null) {
                cell.updateView("FLOCK_SHORN");
            }
        }
    }

    private boolean checkWinCondition(Player player) {
        // DEBUG-Ausgabe:
        /*
        for (Sheep sheep : player.getBoard().getSheepList()) {
            System.out.println("Sheep status: size=" + sheep.getSize() + ", unshorn=" + sheep.getUnshorn() + " notFullyShorn(): " + sheep.hasUnshornParts());
        } */
        return player.getBoard().getSheepList().stream().allMatch(Sheep::isFullyShorn);
    }

    private void switchPlayers() {
        // Normal player switching
        if(!isFirstSwitch) {
            Player temp = currentPlayer;
            currentPlayer = opponentPlayer;
            opponentPlayer = temp;
        }

        // Clear containers and labels
        opponentBoardContainer.getChildren().clear();
        currentPlayerBoardContainer.getChildren().clear();
        opponentLabel.setText("");
        currentPlayerLabel.setText("");

        // Set start.png as background for the left container (opponentBoardContainer)
        StackPane startImagePane = new StackPane();
        startImagePane.setStyle("-fx-background-image: url('/at/ac/fhcampuswien/pictures/start.png'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");
        startImagePane.setPrefSize(400, 400);
        opponentBoardContainer.getChildren().add(startImagePane);

        // Load the Alagard font
        Font alagardFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);

        // Create the button
        String buttonText;
        if (isFirstSwitch) {
            buttonText = currentPlayer.getName() + " begins"; // Nur beim ersten Wechsel
            isFirstSwitch = false; // Danach wird diese Bedingung nie wieder erfüllt
        } else {
            buttonText = "Change of player"; // Für alle weiteren Wechsel
        }

        Button changePlayerButton = new Button(buttonText);
        changePlayerButton.setStyle("-fx-padding: 10px 20px;");
        changePlayerButton.setFont(alagardFont); // Set the custom font

        // Add action to the button
        changePlayerButton.setOnAction(e -> {
            setupBoards();
            updateLabels();
        });

        // Create image views for the animation
        Image animation1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/animation1.png")));
        Image animation2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/animation2.png")));
        Image animation3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/animation3.png")));

        ImageView sheepImageView = new ImageView(animation1);
        sheepImageView.setPreserveRatio(true);

        // Create Timeline for the animation
        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(0.8), new EventHandler<>() {
                    private int loopCounter = 0;

                    @Override
                    public void handle(ActionEvent e) {
                        if (sheepImageView.getImage() == animation1) {
                            sheepImageView.setImage(animation2);
                            loopCounter = 0;
                        } else if (sheepImageView.getImage() == animation2) {
                            sheepImageView.setImage(animation3);
                            loopCounter++;
                        } else if (sheepImageView.getImage() == animation3) {
                            if (loopCounter < 5) {
                                sheepImageView.setImage(animation2);
                                loopCounter++;
                            } else {
                                sheepImageView.setImage(animation1);
                                loopCounter = 0;
                            }
                        }
                    }
                })
        );

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // Create a VBox to hold the image and the button
        VBox buttonAndImageContainer = new VBox(20);
        buttonAndImageContainer.setStyle("-fx-alignment: center;");
        buttonAndImageContainer.getChildren().addAll(sheepImageView, changePlayerButton);

        // Add the VBox to the right container (currentPlayerBoardContainer)
        StackPane buttonContainer = new StackPane(buttonAndImageContainer);
        buttonContainer.setStyle("-fx-background-color: transparent;");
        buttonContainer.setPrefSize(400, 400);
        currentPlayerBoardContainer.getChildren().add(buttonContainer);
    }


}
