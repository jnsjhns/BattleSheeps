package at.ac.fhcampuswien;

public class Sheep {
    private final int size; // Größe des Schafs (z. B. 5 für XL-FLOCK)
    private boolean isHorizontal; // Ausrichtung des Schafs

    private int health;

    public Sheep(int size) {
        this.size = size;
        this.isHorizontal = true;// Standardmäßig horizontal
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
}
