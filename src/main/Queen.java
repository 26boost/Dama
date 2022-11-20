package main;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends Pin{

    Queen(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public ArrayList<int[]> getAvailableMoves() {
        ArrayList<int[]> availableCells = new ArrayList<>();

        // Basic Movement (da fixare)
        if (getX() - 1 >= 0 && Canvas.board[getY() - 1][getX() - 1] == null) // Top Left
            availableCells.add(new int[]{getY() - 1, getX() - 1});
        if (getX() + 1 <= 7 && Canvas.board[getY() - 1][getX() + 1] == null) // Top Right
            availableCells.add(new int[]{getY() - 1, getX() + 1});
        if (getX() - 1 >= 0 && Canvas.board[getY() + 1][getX() - 1] == null) // Bottom Left
            availableCells.add(new int[]{getY() + 1, getX() - 1});
        if (getX() + 1 <= 7 && Canvas.board[getY() + 1][getX() + 1] == null) // Bottom Right
            availableCells.add(new int[]{getY() + 1, getX() + 1});

        // Advanced Movement

        return availableCells;
    }
}
