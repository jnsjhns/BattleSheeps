package at.ac.fhcampuswien.model;

public class Sheep {
    private final int size; // Size of the sheep (e.g. 5 for XL flock)
    private boolean isHorizontal; // Sheep alignment, horizontal by default
    private int startRow;
    private int startCol;
    private int unshorn; // Number of unshorn single sheep

    public Sheep(int size, int startRow, int startCol, boolean isHorizontal) {
        /*
        if (size <= 0) {
            throw new IllegalArgumentException("Size of the sheep must be >0.");
        }
        if (startRow < 0 || startCol < 0) {
            throw new IllegalArgumentException("Start row and column must be positive.");
        } */

        this.size = size;
        this.startRow = startRow;
        this.startCol = startCol;
        this.isHorizontal = isHorizontal;
        this.unshorn = size;
    }

    // Constructor for temporary sheep / currentSheep
    public Sheep(int size) {
        this.size = size;
        this.isHorizontal = true;
        this.unshorn = size;
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


    public void shear() {
            unshorn--;
            // DEBUG-Ausgabe
            // System.out.println("Sheep shorn! Remaining unshorn parts: " + unshorn);
    }

    /* only used for Debugging
    public int getUnshorn() {
        return unshorn;
    } */


    public boolean isFullyShorn() {
        return unshorn <= 0;
    }
}
