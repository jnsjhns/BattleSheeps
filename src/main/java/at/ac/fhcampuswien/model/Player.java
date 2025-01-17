package at.ac.fhcampuswien.model;

public class Player {
    private String name;
    private Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board(10, 10); // 10x10 Spielfeld
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }
}
