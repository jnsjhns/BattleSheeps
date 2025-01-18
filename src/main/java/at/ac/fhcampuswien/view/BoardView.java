package at.ac.fhcampuswien.view;

import at.ac.fhcampuswien.model.Board;
import at.ac.fhcampuswien.model.Cell;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

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
                Rectangle rectangle = cell.getRectangle();

                // Position setzen
                rectangle.setX(col * CELL_SIZE);
                rectangle.setY(row * CELL_SIZE);
                rectangle.setWidth(CELL_SIZE);
                rectangle.setHeight(CELL_SIZE);

                // Rahmen hinzufügen
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(1);

                root.getChildren().add(rectangle);
            }
        }
    }

    public Group getCurrentPlayerView() {
        Cell[][] cells = board.getCells();
        Group playerView = new Group();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                Rectangle rectangle = cell.getRectangle();

                if (cell.isOccupied()) {
                    Image sheepImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep.jpg"));
                    rectangle.setFill(new ImagePattern(sheepImage));
                } else {
                    Image grassImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg"));
                    rectangle.setFill(new ImagePattern(grassImage));
                }

                playerView.getChildren().add(rectangle);
            }
        }
        return playerView;
    }

    public Group getOpponentView() {
        Cell[][] cells = board.getCells();
        Group opponentView = new Group();
        Image grassImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg"));

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                Rectangle rectangle = cell.getRectangle();

                if (cell.wasSelectedBefore()) {
                    if (cell.isOccupied()) {
                        Image sheepShornImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep_shorn.jpg"));
                        rectangle.setFill(new ImagePattern(sheepShornImage));
                    } else {
                        Image grassShornImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/grass_shorn.jpg"));
                        rectangle.setFill(new ImagePattern(grassShornImage));
                    }
                } else {
                    rectangle.setFill(new ImagePattern(grassImage));
                }

                opponentView.getChildren().add(rectangle);
            }
        }
        return opponentView;
    }

    public Rectangle getCellRectangle(int row, int col) {
        return board.getCell(row, col).getRectangle();
    }
}
