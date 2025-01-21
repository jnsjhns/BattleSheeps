package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.model.Player;
import at.ac.fhcampuswien.view.BoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class EndController {
    @FXML
    private Label winnerLabel;

    @FXML
    private Label winnerName;

    @FXML
    private Label loserName;

    @FXML
    private Pane winnerBoard;

    @FXML
    private Pane loserBoard;

    private Player winner;
    private Player loser;

    public void setEnding(Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;

        setText();
        setBoards();
    }

    private void setText() {
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/at/ac/fhcampuswien/fonts/alagard.ttf"), 20);
        winnerLabel.setText(winner.getName() + " wins!");
        winnerLabel.setFont(customFont);
        winnerLabel.setStyle("-fx-alignment: center;");

        winnerName.setText(winner.getName() + " Board");
        winnerName.setFont(customFont);

        loserName.setText(loser.getName() + " Board");
        loserName.setFont(customFont);
    }

    private void setBoards() {
        BoardView winnerBoardView = new BoardView(winner.getBoard());
        BoardView loserBoardView = new BoardView(loser.getBoard());

        winnerBoard.getChildren().add(winnerBoardView.getCurrentPlayerView());
        loserBoard.getChildren().add(loserBoardView.getCurrentPlayerView());

        winnerBoard.setMouseTransparent(true);
        loserBoard.setMouseTransparent(true);
    }
}
