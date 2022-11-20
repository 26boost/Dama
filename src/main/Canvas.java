package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private final int SCALE = 80;

    public static Pin[][] board;
    private static ArrayList<int[]> greenCells;
    private static int[] selectedPin;
    private static int turnCounter;
    private static boolean whiteTurn;

    Canvas() {
        // Panel
        this.setPreferredSize(new Dimension(900, 700));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
        this.setBackground(new Color(0x8A8383));

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
                board[i][j] = new Pin(Color.BLACK, j, i);

        for (int i = 6; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new Pin(Color.WHITE, j, i);

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

                g2.fillRect(i * SCALE, j * SCALE, SCALE, SCALE);
            }
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(0, 0, 8 * SCALE, 8 * SCALE);

        // Fill Board
        g2.setStroke(new BasicStroke(1));
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    g2.setColor(board[i][j].getColor());
                    g2.fillOval(j * SCALE + 5, i * SCALE + 5, SCALE - 10, SCALE - 10);

                    if (board[i][j].getColor() == Color.WHITE)
                        g2.setColor(new Color(0x1A1A1A));
                    else
                        g2.setColor(new Color(0xBEBEBE));
                    g2.drawOval(j * SCALE + 5, i * SCALE + 5, SCALE - 10, SCALE - 10);

                    if (board[i][j] instanceof Queen) {
                        g2.setColor(new Color(0xE5E535));
                        g2.fillOval(j * SCALE + 30, i * SCALE + 30, SCALE - 60, SCALE - 60);
                    }
                }
            }

        // Show available moves if any
        g2.setColor(new Color(0x59009A00, true));
        for (int[] greenCell : greenCells)
            g2.fillRect(greenCell[1] * SCALE, greenCell[0] * SCALE, SCALE, SCALE);
    }


    // Mouse Listener

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int eX = e.getX() / SCALE;
        int eY = e.getY() / SCALE;

        // Position update
        for (int[] greenCell : greenCells) {
            if (greenCell[0] == eY && greenCell[1] == eX) {
                int x = selectedPin[1];
                int y = selectedPin[0];

                board[y][x].moveTo(greenCell[1], greenCell[0]);

                // Eating mechanism
                if (y + 2 == eY || y - 2 == eY)
                    board[(y + eY) / 2][(x + eX) / 2] = null;

                // Upgrade Mechanism
                if (eY == 0 || eY == 7) {
                    if (whiteTurn)
                        board[eY][eX] = new Queen(Color.WHITE, eX, eY);
                    else
                        board[eY][eX] = new Queen(Color.BLACK, eX, eY);
                    board[y][x] = null;
                }

                greenCells = new ArrayList<>();
                turnCounter++;
                return;
            }
        }

        // Available moves
        Color color;
        if (whiteTurn) color = Color.WHITE;
        else color = Color.BLACK;

        if (new Ellipse2D.Double(eX * SCALE + 5, eY * SCALE + 5, SCALE - 10, SCALE - 10).contains(e.getPoint()) &&
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