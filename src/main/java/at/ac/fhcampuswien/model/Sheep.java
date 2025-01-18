package at.ac.fhcampuswien.model;

public class Sheep {
    private final int size; // Größe des Schafs (z. B. 5 für XL-Flock)
    private boolean isHorizontal; // Ausrichtung des Schafs
    private int startRow; // Startreihe des Schafs
    private int startCol; // Startspalte des Schafs
    private int unshorn; // Anzahl der ungeschorenen Einzelschafe

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
        this.isHorizontal = isHorizontal;
        this.unshorn = size; // Zu Beginn ist das Schaf vollständig "gesund"
    }

    // Einfacher Konstruktor für temporäre Schafe (z. B. currentSheep)
    public Sheep(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Die Größe eines Schafs muss größer als 0 sein.");
        }

        this.size = size;
        this.isHorizontal = true; // Standardmäßig horizontal
        this.unshorn = size; // Zu Beginn komplett ungeschoren
    }

    public void setPosition(int startRow, int startCol) {
        this.startRow = startRow;
        this.startCol = startCol;
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

    // Getter für die Startposition
    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }


    // Treffer auf das Schaf (reduziert die Gesundheit)
    public void shear() {
        if (unshorn > 0) {
            unshorn--;
        } else {
            System.out.println("Das Schaf ist bereits vollständig geschoren.");
        }
    }

    // Überprüfen, ob noch (teil-) ungeschoren
    public boolean notFullyShorn() {
        return unshorn > 0;
    }
}
