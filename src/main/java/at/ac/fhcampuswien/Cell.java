package at.ac.fhcampuswien;

import javafx.scene.shape.Rectangle;

public class Cell {
    private int row;
    private int col;
    private boolean isOccupied;
    private Rectangle rectangle; // Rechteck für die visuelle Darstellung

    public Cell(int row, int col, Rectangle rectangle) {
        this.row = row;
        this.col = col;
        this.rectangle = rectangle;
        this.isOccupied = false; // Standardmäßig nicht belegt
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
