package at.ac.fhcampuswien;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Cell[][] cells; // 2D-Array von Cell-Objekten
    private final Group root; // Root-Node für die Darstellung
    private final List<Sheep> sheepList; // Liste der Schafe auf dem Board

    public Board(int rows, int cols) {
        cells = new Cell[rows][cols];
        root = new Group();
        sheepList = new ArrayList<Sheep>();
        initializeBoard(rows, cols);
    }

    private void initializeBoard(int rows, int cols) {
        Image grassImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/grass.jpg"));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Erstelle ein Rechteck für die Zelle
                Rectangle cellRectangle = new Rectangle(40, 40);
                cellRectangle.setX(col * 40);
                cellRectangle.setY(row * 40);

                // Setze das Grasbild als Füllung
                cellRectangle.setFill(new ImagePattern(grassImage));

                // Füge einen Rahmen hinzu
                cellRectangle.setStroke(Color.BLACK);
                cellRectangle.setStrokeWidth(1);

                // Erstelle eine neue Cell und füge sie hinzu
                Cell cell = new Cell(row, col, cellRectangle);
                cells[row][col] = cell;

                // Füge das Rechteck zur Root-Gruppe hinzu
                root.getChildren().add(cellRectangle);
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
        if (canPlaceSheep(sheep, row, col)) {
            Image sheepImage = new Image(getClass().getResourceAsStream("/at/ac/fhcampuswien/pictures/sheep.jpg"));

            for (int i = 0; i < sheep.getSize(); i++) {
                int r = sheep.isHorizontal() ? row : row + i;
                int c = sheep.isHorizontal() ? col + i : col;

                Cell cell = getCell(r, c);
                if (cell != null) {
                    cell.setOccupied(true);

                    // Erstelle ein ImageView für das Schaf
                    ImageView sheepView = new ImageView(sheepImage);
                    int cellSize = 40;
                    int imageSize = 38;
                    int offset = (cellSize - imageSize) / 2;

                    sheepView.setX(c * cellSize + offset);
                    sheepView.setY(r * cellSize + offset);

                    root.getChildren().add(sheepView); // Füge das Bild zur Root-Gruppe hinzu
                }
            }

            // Füge das platzierte Schaf zur Liste hinzu
            sheep.setPosition(row, col); // Speichere Startposition im Schaf (falls nötig)
            sheepList.add(sheep);
        } else {
            System.out.println("Schaf kann nicht hier platziert werden.");
        }
    }



    public boolean isCellOccupied(int row, int col) {
        Cell cell = getCell(row, col);
        return cell != null && cell.isOccupied();
    }

    public void setCellColor(int row, int col, String newImagePath) {
        Cell cell = getCell(row, col);
        if (cell != null) {
            Image newImage = new Image(getClass().getResourceAsStream(newImagePath));
            cell.getRectangle().setFill(new ImagePattern(newImage)); // Setze das neue Bild als Füllung
        }
    }
}
