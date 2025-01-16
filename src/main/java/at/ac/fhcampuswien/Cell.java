package at.ac.fhcampuswien;

import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    private int row;
    private int col;
    private boolean isOccupied; // Gibt an, ob die Zelle belegt ist
    private boolean wasSelectedBefore; // Speichert, ob die Zelle schon ausgewählt wurde
    private Rectangle rectangle; // Rechteck für die visuelle Darstellung

    public Cell(int row, int col, Rectangle rectangle) {
        this.row = row;
        this.col = col;
        this.rectangle = rectangle;
        this.isOccupied = false; // Standardmäßig nicht belegt
        this.wasSelectedBefore = false; // Standardmäßig nicht ausgewählt
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

    public void setFill(ImagePattern imagePattern) {
        this.rectangle.setFill(imagePattern); // Setzt ein Bild als Füllung
    }

    public void setFill(Color color) {
        this.rectangle.setFill(color); // Setzt eine Farbe als Füllung
    }
}
