package main;

import java.awt.*;
import java.util.ArrayList;

public class Pin {

    private final Color color;
    private final int[] pos;

    Pin(Color color, int[] pos) {
        this.pos = pos;
        this.color = color;
    }

    public ArrayList<int[]> getAvailableMoves() {
        ArrayList<int[]> availableCells = new ArrayList<>();

        // Basic Movement
        if (color == Color.WHITE) {
            if (pos[1] - 1 >= 0 && Canvas.board[pos[0] - 1][pos[1] - 1] == null)
                availableCells.add(new int[]{pos[0] - 1, pos[1] - 1});
            if (pos[1] + 1 <= 7 && Canvas.board[pos[0] - 1][pos[1] + 1] == null)
                availableCells.add(new int[]{pos[0] - 1, pos[1] + 1});
        }
        else {
            if (pos[1] - 1 >= 0 && Canvas.board[pos[0] + 1][pos[1] - 1] == null)
                availableCells.add(new int[]{pos[0] + 1, pos[1] - 1});
            if (pos[1] + 1 <= 7 && Canvas.board[pos[0] + 1][pos[1] + 1] == null)
                availableCells.add(new int[]{pos[0] + 1, pos[1] + 1});
        }

        // Advanced Movement (eating mechanic)

        return availableCells;
    }

    public void moveTo(int x, int y) {
        Canvas.board[y][x] = this;
        Canvas.board[pos[0]][pos[1]] = null;

        pos[0] = y;
        pos[1] = x;
    }

    public Color getColor() {
        return color;
    }
}
