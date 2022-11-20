package main;

import java.awt.*;
import java.util.ArrayList;

public class Pin {

    private final Color color;
    private int x;
    private int y;

    Pin(Color color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public ArrayList<int[]> getAvailableMoves() {                   // da migliorare
        ArrayList<int[]> availableCells = new ArrayList<>();

        if (color == Color.WHITE) {
            // Basic Movement
            if (x - 1 >= 0 && Canvas.board[y - 1][x - 1] == null)    // Top Left
                availableCells.add(new int[]{y - 1, x - 1});
            if (x + 1 <= 7 && Canvas.board[y - 1][x + 1] == null)    // Top Right
                availableCells.add(new int[]{y - 1, x + 1});

            // Advanced Movement
            if (x - 1 >= 0 && y >= 2 &&
                    Canvas.board[y - 1][x - 1] != null && Canvas.board[y - 1][x - 1].getColor() != this.color &&
                    Canvas.board[y - 2][x - 2] == null)    // Top Left
                availableCells.add(new int[]{y - 2, x - 2});
            if (x + 1 <= 7 && y >= 2 &&
                    Canvas.board[y - 1][x + 1] != null && Canvas.board[y - 1][x + 1].getColor() != this.color &&
                    Canvas.board[y - 2][x + 2] == null)    // Top Right
                availableCells.add(new int[]{y - 2, x + 2});
        }
        else {
            // Basic Movement
            if (x - 1 >= 0 && Canvas.board[y + 1][x - 1] == null)    // Bottom Left
                availableCells.add(new int[]{y + 1, x - 1});
            if (x + 1 <= 7 && Canvas.board[y + 1][x + 1] == null)    // Bottom Right
                availableCells.add(new int[]{y + 1, x + 1});

            // Advanced Movement
            if (x - 1 >= 0 && y <= 5 &&
                    Canvas.board[y + 1][x - 1] != null && Canvas.board[y + 1][x - 1].getColor() != this.color &&
                    Canvas.board[y + 2][x - 2] == null)    // Bottom Left
                availableCells.add(new int[]{y + 2, x - 2});
            if (x + 1 <= 7 && y <= 5 &&
                    Canvas.board[y + 1][x + 1] != null && Canvas.board[y + 1][x + 1].getColor() != this.color &&
                    Canvas.board[y + 2][x + 1] == null)    // Bottom Right
                availableCells.add(new int[]{y + 2, x + 2});
        }

        return availableCells;
    }

    public void moveTo(int x, int y) {
        Canvas.board[y][x] = this;
        Canvas.board[this.y][this.x] = null;

        this.y = y;
        this.x = x;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
