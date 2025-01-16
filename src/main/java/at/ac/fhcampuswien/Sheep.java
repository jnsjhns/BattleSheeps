package at.ac.fhcampuswien;

public class Sheep {
    private final int size; // Größe des Schafs (z. B. 5 für XL-Flock)
    private boolean isHorizontal; // Ausrichtung des Schafs
    private int health; // Gesundheit des Schafs (wie viele Teile noch ungeschoren sind)

    private int startRow; // Startreihe des Schafs
    private int startCol; // Startspalte des Schafs

    public Sheep(int size) {
        this.size = size;
        this.isHorizontal = true; // Standardmäßig horizontal
        this.health = size;
    }

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public int getHealth() {
        return health;
    }

    public void hit() {
        health--;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setPosition(int row, int col) {
        this.startRow = row;
        this.startCol = col;
    }
}
