package at.ac.fhcampuswien.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    private final int row;
    private final int col;
    private boolean isOccupied; // Gibt an, ob die Zelle belegt ist
    private boolean wasSelectedBefore; // Gibt an, ob die Zelle bereits ausgewählt wurde
    private final Rectangle rectangle; // Visuelle Darstellung der Zelle

    // Bilder für verschiedene Zustände
    private static final Image grass = new Image(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg"));
    private static final Image grassShorn = new Image(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/grass_shorn.jpg"));
    private static final Image sheep = new Image(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep.jpg"));
    private static final Image sheepShorn = new Image(Cell.class.getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep_shorn.jpg"));

    public Cell(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.isOccupied = false;
        this.wasSelectedBefore = false;

        // Erstelle das Rechteck für die visuelle Darstellung
        this.rectangle = new Rectangle(size, size);
        this.rectangle.setX(col * size);
        this.rectangle.setY(row * size);
        this.rectangle.setFill(new ImagePattern(grass)); // Standardbild
        this.rectangle.setStroke(Color.BLACK);
        this.rectangle.setStrokeWidth(1);
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    // Aktualisiere das Bild basierend auf dem Zustand
    public void updateView(String state) {
        switch (state) {
            case "GRASS":
                rectangle.setFill(new ImagePattern(grass));
                break;
            case "GRASS_SHORN":
                rectangle.setFill(new ImagePattern(grassShorn));
                break;
            case "SHEEP":
                rectangle.setFill(new ImagePattern(sheep));
                break;
            case "SHEEP_SHORN":
                rectangle.setFill(new ImagePattern(sheepShorn));
                break;
            default:
                throw new IllegalArgumentException("Unbekannter Zustand: " + state);
        }
    }
}
