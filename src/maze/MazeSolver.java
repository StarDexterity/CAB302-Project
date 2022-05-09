package maze;

import java.util.LinkedList;

/**
 * This static class finds the solution or a property of a given @{@link Maze} object.
 * Its methods take a Default.Maze object as a parameter, and return a value that describes some property of the maze,
 * including, if it is solvable, percentage of dead ends, and percentage of path to solve.
 */
public class MazeSolver {
    /**
     * For a given maze, validates if the maze has a solution
     * @param maze A @{@link Maze} object
     * @return
     */
    public static boolean isSolution(Maze maze, int pos) {
        LinkedList<Integer> solution = new LinkedList<>();

        if (pos == maze.nCols * maze.nRows - 1)
            return true;

        int c = pos % maze.nCols;
        int r = pos / maze.nCols;

        for (Direction dir : Direction.values()) {
            int nc = c + dir.dx;
            int nr = r + dir.dy;
            if (maze.withinBounds(nc, nr) && (maze.mazeGrid[r][c] & dir.bit) != 0
                    && (maze.mazeGrid[nr][nc] & 16) == 0) {

                int newPos = nr * maze.nCols + nc;

                solution.add(newPos);
                maze.mazeGrid[nr][nc] |= 16;

                //animate();

                if (isSolution(maze, newPos))
                    return true;

                //animate();

                solution.removeLast();
                maze.mazeGrid[nr][nc] &= ~16;
            }
        }

        return false;
    }

    /**
     * For a given maze, finds the total cells with dead ends
     * @param maze A @{@link Maze} object
     * @return
     */
    public static int TotalDeadEnds(Maze maze) {
        return 0;
    }

    /**
     * For a given maze, finds the total cells required to pass through, for the optimal solution
     * @param maze A @{@link Maze} object
     */
    public static int TotalPassThrough(Maze maze) {
        return 0;
    }
    /**
     * Solves the maze, please add a more helpful description later
     * @param pos
     * @return
     */

}
