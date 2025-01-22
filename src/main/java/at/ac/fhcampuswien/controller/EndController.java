package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.Player;
import at.ac.fhcampuswien.view.BoardView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Objects;

public class EndController {

    @FXML
    private Label winnerLabel; // Label for winner announcement

    @FXML
    private Label winnerName; // Label for winner's flock name

    @FXML
    private Label loserName; // Label for loser's flock name

    @FXML
    private StackPane winnerBoard; // StackPane for winner's board

    @FXML
    private StackPane loserBoard; // StackPane for loser's board

    @FXML
    private ImageView animationView; // ImageView for animation

    private Player winner; // The winning player
    private Player loser;  // The losing player


    public void setEnding(Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;

        setText();       // Set text for labels
        setBoards();     // Display boards
        addAnimation();  // Add animation below boards
    }

    // Set up labels
    private void setText() {
        Font winnerFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 30); // 30px font size
        Font otherFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20); // 20px font size

        winnerLabel.setText(winner.getName() + " wins!");
        winnerLabel.setFont(winnerFont);
        winnerLabel.setStyle("-fx-text-fill: rgb(5, 0, 197); -fx-alignment: center;");

        winnerName.setText(winner.getName() + "'s Flock");
        winnerName.setFont(otherFont);

        loserName.setText(loser.getName() + "'s Flock");
        loserName.setFont(otherFont);
    }

    // Shows winner board (left), loser board (right)
    private void setBoards() {
        BoardView winnerBoardView = new BoardView(winner.getBoard());
        BoardView loserBoardView = new BoardView(loser.getBoard());

        // Add board views to their respective StackPanes
        winnerBoard.getChildren().clear(); // Clear any existing children (important if reusing views)
        loserBoard.getChildren().clear();

        winnerBoard.getChildren().add(winnerBoardView.getCurrentPlayerView());
        loserBoard.getChildren().add(loserBoardView.getCurrentPlayerView());

        // Disable interactions with the boards
        winnerBoard.setMouseTransparent(true);
        loserBoard.setMouseTransparent(true);
    }

    // Add animation below the boards
    private void addAnimation() {
        // Load animation images
        Image animation1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/animation1.png")));
        Image animation2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/animation2.png")));
        Image animation3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/animation3.png")));

        // Initialize the animation view with the first image
        animationView.setImage(animation1);

        // Create Timeline for the animation
        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(0.8), new EventHandler<>() {
                    private int loopCounter = 0;

                    @Override
                    public void handle(ActionEvent e) {
                        if (animationView.getImage() == animation1) {
                            animationView.setImage(animation2);
                            loopCounter = 0;
                        } else if (animationView.getImage() == animation2) {
                            animationView.setImage(animation3);
                            loopCounter++;
                        } else if (animationView.getImage() == animation3) {
                            if (loopCounter < 5) {
                                animationView.setImage(animation2);
                                loopCounter++;
                            } else {
                                animationView.setImage(animation1);
                                loopCounter = 0;
                            }
                        }
                    }
                })
        );

        // Set the animation to loop indefinitely
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

}
