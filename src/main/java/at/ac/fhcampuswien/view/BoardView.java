package at.ac.fhcampuswien.view;

import at.ac.fhcampuswien.model.Board;
import at.ac.fhcampuswien.model.Cell;
import at.ac.fhcampuswien.model.Sheep;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class BoardView {
    private final Group root;
    private final Board board;
    private static final int CELL_SIZE = 40;

    public BoardView(Board board) {
        this.board = board;
        this.root = new Group();
        initializeView();
    }

    private void initializeView() {
        Cell[][] cells = board.getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];

                // Set position
                cell.setX(col * CELL_SIZE);
                cell.setY(row * CELL_SIZE);
                cell.setWidth(CELL_SIZE);
                cell.setHeight(CELL_SIZE);

                // Add frame
                cell.setStroke(Color.BLACK);
                cell.setStrokeWidth(1);

                root.getChildren().add(cell);
            }
        }
    }

    private void updateCellAppearance(Cell cell, boolean isOpponentView) {
        if (cell.wasSelectedBefore()) {
            if (cell.isOccupied()) {
                Sheep sheep = board.getSheepAt(cell.getRow(), cell.getCol());
                if (sheep != null && sheep.isFullyShorn()) {
                    // Fully shorn sheep
                    cell.updateView("FLOCK_SHORN");
                } else {
                    // Partially shorn sheep
                    cell.updateView("SHEEP_SHORN");
                }
            } else {
                // Grass that was selected before
                cell.updateView("GRASS_SHORN");
            }
        } else if (!isOpponentView && cell.isOccupied()) {
            // Show unshorn sheep for current player
            cell.updateView("SHEEP");
        } else {
            // Default grass image
            cell.updateView("GRASS");
        }
    }

    public Group getCurrentPlayerView() {
        Cell[][] cells = board.getCells();
        Group playerView = new Group();

        for (Cell[] value : cells) {
            for (Cell cell : value) {
                updateCellAppearance(cell, false); // Pass false for current player's view
                playerView.getChildren().add(cell);
            }
        }
        return playerView;
    }

    public Group getOpponentView() {
        Cell[][] cells = board.getCells();
        Group opponentView = new Group();

        for (Cell[] value : cells) {
            for (Cell cell : value) {
                updateCellAppearance(cell, true); // Pass true for opponent's view
                opponentView.getChildren().add(cell);
            }
        }
        return opponentView;
    }

}
