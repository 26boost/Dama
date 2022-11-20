package main;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    public static Pedina[][] board;
    private Timer timer;

     Canvas() {
        // Panel
        this.setPreferredSize(new Dimension(500, 500));
        this.setFocusable(true);

        // Frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Board
        board = new Pedina[8][8];
        for (int i = 0; i < board.length - 6; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new Pedina(Color.BLACK);

        for (int i = 6; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new Pedina(Color.WHITE);
    }

    public void start() {
        timer = new Timer(0,(ae)-> {
            repaint();
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
    }
}
