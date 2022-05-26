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
     * @param maze A @{@link Maze} object. The solution to the maze is stored within this object
     * @return A bool value indicating whether the maze was successfully solved.
     */
    public static boolean solve(int pos, Maze maze) {
        maze.solution = new LinkedList<>();
        return recursiveSolve(pos, maze);
    }

    /**
     * Recursive depth first search solving algorithm, using black magic
     * @param pos
     * @param maze
     * @return
     */
    private static boolean recursiveSolve(int pos, Maze maze) {
        if (pos == maze.nCols * maze.nRows - 1)

            return true;

        // x position is the remainder after the position is divided by the number of columns
        int x = pos % maze.nCols;
        // y position is the position divided by the number of rows
        int y = pos / maze.nCols;

        // Directions in order of search (N, S, E, W)
        for (Direction dir : Direction.values()) {
            // next x pos is x plus dx
            int nx = x + dir.dx;
            // next y pos is y plus dy
            int ny = y + dir.dy;

            // If the next cell is within bounds of maze,
            // is connected to this cell
            // and hasn't been visited, visit it
            if (maze.withinBounds(nx, ny) && (maze.mazeGrid[y][x] & dir.bit) != 0
                    && (maze.mazeGrid[ny][nx] & 16) == 0) {

                // condensing coordinate
                int newPos = ny * maze.nCols + nx;

                maze.solution.add(newPos);

                // sets the 5th bit to mark visit
                maze.mazeGrid[ny][nx] |= 16;

                // if maze is solved, halt and return true
                if (MazeSolver.recursiveSolve(newPos, maze))
                    return true;


                // backtracks from current cell to the last one
                maze.solution.removeLast();
                maze.mazeGrid[ny][nx] &= ~16;
            }
        }
        // All cells have been visited and a solution hasn't been found
        return false;
    }

    /**
     * For a given maze, finds the total cells with dead ends
     * @return
     */
    public static String TotalDeadEnds(Maze maze) {
        LinkedList solution = maze.solution;
        double count = 0;
        for (Object o : solution) {
            count++;
        }
        String deadEnd =Double.toString((100-((count+1)/(maze.nRows*maze.nCols))*100));
        return deadEnd;
    }

    /**
     * For a given maze, finds the total cells required to pass through, for the optimal solution
     */
    public static String TotalPassThrough(Maze maze) {
        LinkedList solution = maze.solution;
        double count = 0;
        for (Object o : solution) {
            count++;
        }

        String passThrough = Double.toString(((count+1)/(maze.nRows*maze.nCols))*100);
        System.out.println(count+","+ maze.nCols* maze.nRows);
        return passThrough;
    }

}
