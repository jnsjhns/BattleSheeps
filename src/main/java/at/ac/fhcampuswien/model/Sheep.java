package at.ac.fhcampuswien.model;

public class Sheep {
    private final int size; // Größe des Schafs (z. B. 5 für XL-Flock)
    private boolean isHorizontal; // Ausrichtung des Schafs
    private int startRow; // Startreihe des Schafs
    private int startCol; // Startspalte des Schafs
    private int unshorn; // Anzahl der ungeschorenen Felder

    public Sheep(int size, int startRow, int startCol, boolean isHorizontal) {
        if (size <= 0) {
            throw new IllegalArgumentException("Die Größe eines Schafs muss größer als 0 sein.");
        }
        if (startRow < 0 || startCol < 0) {
            throw new IllegalArgumentException("Startreihe und Startspalte müssen positive Werte sein.");
        }

        this.size = size;
        this.startRow = startRow;
        this.startCol = startCol;
        this.isHorizontal = isHorizontal; // Standardmäßig horizontal
        this.unshorn = size; // Zu Beginn komplett ungeschoren
    }

    // Konstruktor für temporäre Schafe / currentSheep
    public Sheep(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Die Größe eines Schafs muss größer als 0 sein.");
        }

        this.size = size;
        this.isHorizontal = true; // Standardmäßig horizontal
        this.unshorn = size; // Zu Beginn komplett ungeschoren
    }


    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }


    // Feld eines Schafs gefunden, unshorn--
    public void shear() {
            unshorn--;
            // DEBUG-Ausgabe
            // System.out.println("Sheep shorn! Remaining unshorn parts: " + unshorn);
    }

    /* only used for Debugging
    public int getUnshorn() {
        return unshorn;
    } */

    // Überprüfen, ob komplett geschoren
    public boolean isFullyShorn() {
        return unshorn <= 0;
    }
}
