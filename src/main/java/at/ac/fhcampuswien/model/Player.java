package at.ac.fhcampuswien.model;

public class Player {
    private final String name;
    private final Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board(10, 10); // 10x10 Board
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }
}
