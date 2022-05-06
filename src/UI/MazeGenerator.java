package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This maze generator and solver was taken from:
 * https://rosettacode.org/wiki/Maze_solving#Animated_version
 * This is currently more of a placeholder for the algorithm
 * for the purposes of displaying the prototype properly.
 */

public class MazeGenerator extends JPanel {
    /**
     * Indicates the direction of one or more neighboring vertices a vertex connects with (shares an edge)
     * Can be used like bits aka
     *          (If a vertex has both South and East neighbors this can be represented with a bit value of 6)
     */
    enum Direction {
        N(1, 0, -1),
        S(2, 0, 1),
        E(4, 1, 0),
        W(8, -1, 0);

        /**
         * This enum can have multiple values,
         *          the bit of the enum is the combination of flags that enum has.
         *          For example, if the enum has a bit value of 7
         *              It has N and E and S neighbors
         */
        final int bit;


        /**
         * The displacement of x
         *          e.g. E has a dx of 1:
         *              because the neighbor of this vertex to the east is in index position of this vertex in the x + 1
         */
        final int dx;


        /**
         * The displacement of y
         *          e.g. N has a dy of -1:
         *              because the neighbor of this vertex to the north is in the index position of this vertex in the y - 1
         */
        final int dy;

        // Together dx and dy point in the direction of the neighboring vertex

        /**
         * The opposite direction, to the current direction (e.g. N.opposite = S)
         */
        Direction opposite;

        // If one vertex a connects to another b, than b must connect to a using the opposite direction enum bit
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        /**
         *
         * @param bit
         * @param dx
         * @param dy
         */
        Direction(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

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
    final int[][] maze;

    /**
     * Stores the solution to the maze. This is empty until solve is called.
     */
    LinkedList<Integer> solution;

    public MazeGenerator(int columns, int rows) {
        // graphics code
        setPreferredSize(new Dimension(650, 650));
        setBackground(Color.white);

        // set the size of the maze
        nCols = columns;
        nRows = rows;

        // initialize the internal maze data structure
        maze = new int[nRows][nCols];

        solution = new LinkedList<>();

        // This uses recursion to generate the full maze one vertex per function call
        generateMaze(0, 0);


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

                if ((maze[r][c] & 1) == 0) // N
                    g.drawLine(x, y, x + cellSize, y);

                if ((maze[r][c] & 2) == 0) // S
                    g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);

                if ((maze[r][c] & 4) == 0) // E
                    g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);

                if ((maze[r][c] & 8) == 0) // W
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

    /**
     * @param x column no#
     * @param y row no#
     */
    void generateMaze(int x, int y) {
        // gets an array of the directions, then picks one at random
        Direction[] dirs = Direction.values();
        Collections.shuffle(Arrays.asList(dirs));

        // for each direction (N, E, S, W) in a random order
        for (Direction dir : dirs) {
            // gets the coordinates of a neighbor of the current vertex,
            // in the direction of dir
            int nextX = x + dir.dx;
            int nextY = y + dir.dy;

            // If the neighbor is within the bounds of the maze, and has not been visited (derived from a direction bit value of 0)
            if (withinBounds(nextX, nextY) && maze[nextY][nextX] == 0) {
                // Set this vertex bit and the neighbors to the opposite direction of this bit
                // This operation connects these two vertices together
                maze[y][x] |= dir.bit;
                maze[nextY][nextX] |= dir.opposite.bit;

                // Call the recursive call to continue generating the maze,
                // on the neighboring vertex
                generateMaze(nextX, nextY);
            }
        }
    }

    /**
     * Is the supplied x and y position of a vertex within the bounds of the maze
     * @param x
     * @param y
     * @return
     */
    boolean withinBounds(int x, int y) {
        return (x >= 0 && x < nCols) && (y >= 0 && y < nRows);
    }

    /**
     * Solves the maze, please add a more helpful description later
     * @param pos
     * @return
     */
    public boolean solve(int pos) {
        if (pos == nCols * nRows - 1)
            return true;

        int c = pos % nCols;
        int r = pos / nCols;

        for (Direction dir : Direction.values()) {
            int nc = c + dir.dx;
            int nr = r + dir.dy;
            if (withinBounds(nc, nr) && (maze[r][c] & dir.bit) != 0
                    && (maze[nr][nc] & 16) == 0) {

                int newPos = nr * nCols + nc;

                solution.add(newPos);
                maze[nr][nc] |= 16;

                animate();

                if (solve(newPos))
                    return true;

                animate();

                solution.removeLast();
                maze[nr][nc] &= ~16;
            }
        }

        return false;
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
            f.setTitle("Maze Generator");
            f.setResizable(true);
            f.add(new MazeGenerator(20, ), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
     */
}