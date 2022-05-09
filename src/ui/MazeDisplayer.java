package ui;

import maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.LinkedList;

/**
 * This maze generator and solver was taken from:
 * https://rosettacode.org/wiki/Maze_solving#Animated_version
 * This is currently more of a placeholder for the algorithm
 * for the purposes of displaying the prototype properly.
 */

public class MazeDisplayer extends JPanel {
    /**
     * Indicates the direction of one or more neighboring vertices a vertex connects with (shares an edge)
     * Can be used like bits aka
     *          (If a vertex has both South and East neighbors this can be represented with a bit value of 6)
     */


    /**
     * Number of columns in the maze
     */
    final int nCols;
    /**
     * Number of rows in the maze
     */
    final int nRows;

    // These two values are for rendering
    final int cellSize = 25;
    final int margin = 25;

    /**
     * The internal representation of the maze, represented as a matrix of vertices connected by the direction enumerable
     */
    final int[][] mazeGrid;

    /**
     * Stores the solution to the maze. This is empty until solve is called.
     */
    LinkedList<Integer> solution;

    public MazeDisplayer(Maze maze) {
        // graphics code
        setPreferredSize(new Dimension(650, 650));
        setBackground(Color.white);

        mazeGrid = maze.mazeGrid;
        nCols = maze.nCols;
        nRows = maze.nRows;


 /*       addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new Thread(() -> {
                    solve(0);
                }).start();
            }
        });*/


    }

    /**
     * Draws the maze to the screen
     * @param gg
     */
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setStroke(new BasicStroke(5));
        g.setColor(Color.black);

        // draw maze
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {

                int x = margin + c * cellSize;
                int y = margin + r * cellSize;

                if ((mazeGrid[r][c] & 1) == 0) // N
                    g.drawLine(x, y, x + cellSize, y);

                if ((mazeGrid[r][c] & 2) == 0) // S
                    g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);

                if ((mazeGrid[r][c] & 4) == 0) // E
                    g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);

                if ((mazeGrid[r][c] & 8) == 0) // W
                    g.drawLine(x, y, x, y + cellSize);
            }
        }

        // draw pathfinding animation
        int offset = margin + cellSize / 2;

        Path2D path = new Path2D.Float();
        path.moveTo(offset, offset);

        for (int pos : solution) {
            int x = pos % nCols * cellSize + offset;
            int y = pos / nCols * cellSize + offset;
            path.lineTo(x, y);
        }

        g.setColor(Color.orange);
        g.draw(path);

        g.setColor(Color.blue);
        g.fillOval(offset - 5, offset - 5, 10, 10);

        g.setColor(Color.green);
        int x = offset + (nCols - 1) * cellSize;
        int y = offset + (nRows - 1) * cellSize;
        g.fillOval(x - 5, y - 5, 10, 10);

    }


    void animate() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }

    /* // we don't need this
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Default.Maze Generator");
            f.setResizable(true);
            f.add(new MazeGenerator(20, ), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
     */
}