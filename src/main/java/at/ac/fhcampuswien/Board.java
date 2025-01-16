package at.ac.fhcampuswien;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {
    private final Cell[][] cells; // 2D-Array von Cell-Objekten
    private final Group root; // Root-Node für die Darstellung

    public Board(int rows, int cols) {
        cells = new Cell[rows][cols];
        root = new Group();
        initializeBoard(rows, cols);
    }

    private void initializeBoard(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle cellRect = new Rectangle(30, 30);
                cellRect.setFill(Color.LIGHTGREEN); // Grasgrün für leere Felder
                cellRect.setStroke(Color.BLACK);
                cellRect.setX(col * 30); // X-Position basierend auf der Spalte
                cellRect.setY(row * 30); // Y-Position basierend auf der Zeile
                Cell cell = new Cell(row, col, cellRect);
                cells[row][col] = cell;
                root.getChildren().add(cellRect); // Rechteck zur Gruppe hinzufügen
            }
        }
    }

    public Group getRoot() {
        return root;
    }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < cells.length && col >= 0 && col < cells[0].length) {
            return cells[row][col];
        }
        return null; // Ungültige Koordinaten
    }

    public boolean canPlaceSheep(Sheep sheep, int row, int col) {
        for (int i = 0; i < sheep.getSize(); i++) {
            int r = sheep.isHorizontal() ? row : row + i;
            int c = sheep.isHorizontal() ? col + i : col;

            if (r >= cells.length || c >= cells[0].length || isCellOccupied(r, c)) {
                return false; // Belegte oder ungültige Zelle gefunden
            }

            // Überprüfen auf direkte Nachbarschaft (oben, unten, links, rechts)
            if ((r > 0 && isCellOccupied(r - 1, c)) || // Oben
                    (r < cells.length - 1 && isCellOccupied(r + 1, c)) || // Unten
                    (c > 0 && isCellOccupied(r, c - 1)) || // Links
                    (c < cells[0].length - 1 && isCellOccupied(r, c + 1))) { // Rechts
                return false; // Direkte Nachbarschaft gefunden
            }
        }
        return true;
    }



    public void placeSheep(Sheep sheep, int row, int col) {
        // Vor der Platzierung sicherstellen, dass das Schaf platziert werden kann
        if (canPlaceSheep(sheep, row, col)) {
            for (int i = 0; i < sheep.getSize(); i++) {
                int r = sheep.isHorizontal() ? row : row + i;
                int c = sheep.isHorizontal() ? col + i : col;

                Cell cell = getCell(r, c);
                if (cell != null) {
                    cell.setOccupied(true);
                    cell.getRectangle().setFill(Color.GRAY); // Schaf optisch anzeigen
                }
            }
        } else {
            // Hier könntest du eine Fehlermeldung oder eine andere Reaktion einbauen
            System.out.println("Schaf kann nicht hier platziert werden.");
        }
    }


    public boolean isCellOccupied(int row, int col) {
        Cell cell = getCell(row, col);
        return cell != null && cell.isOccupied();
    }

    public void setCellColor(int row, int col, Color color) {
        Cell cell = getCell(row, col);
        if (cell != null) {
            cell.getRectangle().setFill(color);
        }
    }
}
