package at.ac.fhcampuswien.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Cell[][] cells;
    private final List<Sheep> sheepList; // List of sheep on the board

    public Board(int rows, int cols) {
        cells = new Cell[rows][cols];
        sheepList = new ArrayList<>();
        initializeBoard(rows, cols);
    }

    private void initializeBoard(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col, 40);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public List<Sheep> getSheepList() {
        return sheepList;
    }

    public Cell getCell(int row, int col) {
            return cells[row][col];
    }

    public boolean canPlaceSheep(Sheep sheep, int row, int col) {
        for (int i = 0; i < sheep.getSize(); i++) {
            int r = sheep.isHorizontal() ? row : row + i;
            int c = sheep.isHorizontal() ? col + i : col;

            if (r >= cells.length || c >= cells[0].length || isCellOccupied(r, c)) {
                return false;
            }

            // Check for direct neighbourhood
            if ((r > 0 && isCellOccupied(r - 1, c)) ||
                    (r < cells.length - 1 && isCellOccupied(r + 1, c)) ||
                    (c > 0 && isCellOccupied(r, c - 1)) ||
                    (c < cells[0].length - 1 && isCellOccupied(r, c + 1))) {
                return false;
            }
        }
        return true;
    }

    // Placing of sheep Object
    public boolean placeSheep(Sheep sheep) {
        int row = sheep.getStartRow();
        int col = sheep.getStartCol();

        if (canPlaceSheep(sheep, row, col)) {
            for (int i = 0; i < sheep.getSize(); i++) {
                int r = sheep.isHorizontal() ? row : row + i;
                int c = sheep.isHorizontal() ? col + i : col;

                Cell cell = cells[r][c];
                cell.setOccupied(true);
            }

            sheepList.add(sheep);
            return true; // Successful placement
        } else {
            return false; // Placement failed
        }
    }



    public Sheep getSheepAt(int row, int col) {
        for (Sheep sheep : sheepList) {
            int startRow = sheep.getStartRow();
            int startCol = sheep.getStartCol();
            int size = sheep.getSize();
            boolean isHorizontal = sheep.isHorizontal();

            if (isHorizontal) {
                if (row == startRow && col >= startCol && col < startCol + size) {
                    // System.out.println("Found horizontal sheep at (" + row + ", " + col + ")");
                    return sheep;
                }
            } else { // Vertical
                if (col == startCol && row >= startRow && row < startRow + size) {
                    // System.out.println("Found vertical sheep at (" + row + ", " + col + ")");
                    return sheep;
                }
            }
        }
        // System.out.println("No sheep found at (" + row + ", " + col + ")");
        return null;
    }



    public boolean isCellOccupied(int row, int col) {
        return cells[row][col].isOccupied();
    }
}
