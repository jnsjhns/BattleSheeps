package at.ac.fhcampuswien.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Cell extends Rectangle {
    private final int row;
    private final int col;
    private boolean isOccupied;
    private boolean wasSelectedBefore;

    // Pictures for different states
    private static final Image grass = new Image(Objects.requireNonNull(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg")));
    private static final Image grassShorn = new Image(Objects.requireNonNull(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/grass_shorn.jpg")));
    private static final Image sheep = new Image(Objects.requireNonNull(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep.jpg")));
    private static final Image sheepShorn = new Image(Objects.requireNonNull(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep_shorn.jpg")));
    private static final Image flockShorn = new Image(Objects.requireNonNull(Cell.class.getResourceAsStream(("/at/ac/fhcampuswien/pictures/flock_shorn.jpg"))));
    private static final Image selection = new Image(Objects.requireNonNull(Cell.class.getResourceAsStream(("/at/ac/fhcampuswien/pictures/selection.jpg"))));

    public Cell(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.isOccupied = false;
        this.wasSelectedBefore = false;

        // Initiate rectangle (Cell is subtype of Rectangle)
        this.setX(col * size);
        this.setY(row * size);
        this.setFill(new ImagePattern(grass)); // Standard picture
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean wasSelectedBefore() {
        return wasSelectedBefore;
    }

    public void setWasSelectedBefore(boolean wasSelectedBefore) {
        this.wasSelectedBefore = wasSelectedBefore;
    }


    // Update the image based on the state
    public void updateView(String state) {
        switch (state) {
            case "GRASS":
                this.setFill(new ImagePattern(grass));
                break;
            case "GRASS_SHORN":
                this.setFill(new ImagePattern(grassShorn));
                break;
            case "SHEEP":
                this.setFill(new ImagePattern(sheep));
                break;
            case "SHEEP_SHORN":
                this.setFill(new ImagePattern(sheepShorn));
                break;
            case "FLOCK_SHORN":
                this.setFill(new ImagePattern(flockShorn));
                break;
            case "SELECTION":
                this.setFill(new ImagePattern(selection));
                break;
            default:
                throw new IllegalArgumentException("Unknown State: " + state);
        }
    }
}
