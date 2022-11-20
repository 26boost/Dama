package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    public static Pin[][] board;
    private static ArrayList<int[]> greenCells;
    private static int[] selectedPin;
    private static int turnCounter;
    private static boolean whiteTurn;

    Canvas() {
        // Panel
        this.setPreferredSize(new Dimension(500, 500));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);

        // Frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Board
        board = new Pin[8][8];
        for (int i = 0; i < board.length - 6; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new Pin(Color.BLACK, new int[]{i, j});

        for (int i = 6; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new Pin(Color.WHITE, new int[]{i, j});

        // Variables
        greenCells = new ArrayList<>();
        turnCounter = 0;
    }

    public void start() {
        Timer timer = new Timer(0,(ae)-> {
            repaint();

            whiteTurn = turnCounter % 2 == 0;
        });
        timer.setDelay(5); // ms
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint Board
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                if ((i + j) % 2 == 0)
                    g2.setColor(new Color(0x504D4D));
                else
                    g2.setColor(new Color(0x2A2A2A));

                g2.fillRect(i * 50, j * 50, 50, 50);
            }

        // Fill Board
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    g2.setColor(board[i][j].getColor());
                    g2.fillOval(j * 50 + 5, i * 50 + 5, 40, 40);
                }
            }

        // Show available moves if any
        g2.setColor(new Color(0x59009A00, true));
        for (int[] greenCell : greenCells)
            g2.fillRect(greenCell[1] * 50, greenCell[0] * 50, 50, 50);
    }


    // Mouse Listener

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int eX = e.getX() / 50;
        int eY = e.getY() / 50;

        // Position update
        for (int[] greenCell : greenCells) {
            if (greenCell[0] == eY && greenCell[1] == eX) {
                board[selectedPin[0]][selectedPin[1]].moveTo(greenCell[1], greenCell[0]);
                greenCells = new ArrayList<>();
                turnCounter++;
                return;
            }
        }

        // Available moves
        Color color;
        if (whiteTurn) color = Color.WHITE;
        else color = Color.BLACK;

        if (new Ellipse2D.Double(eX * 50 + 5, eY * 50 + 5, 40, 40).contains(e.getPoint()) &&
                board[eY][eX] != null &&
                board[eY][eX].getColor() == color) {
            greenCells = board[eY][eX].getAvailableMoves();
            selectedPin = new int[]{eY, eX};
        }
        else
            greenCells = new ArrayList<>();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
