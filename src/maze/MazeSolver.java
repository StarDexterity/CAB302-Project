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
    public static boolean solve(Position pos, Maze maze) {
        maze.getSolution().clear();
        return recursiveSolve(pos, maze);
    }

    /**
     * Recursive depth first search solving algorithm, using black magic
     * @param pos
     * @param maze
     * @return
     */
    private static boolean recursiveSolve(Position pos, Maze maze) {
        int[][] mazeGrid = maze.getMazeGrid();
        int nRows = maze.getRows();
        int nCols = maze.getCols();
        LinkedList<Position> solution = maze.getSolution();

        int x = pos.getX();
        int y = pos.getY();

        if (x == nCols - 1 && y == nRows - 1) return true;


        // Directions in order of search (N, S, E, W)
        for (Direction dir : Direction.values()) {
            // next x pos is x plus dx
            int nx = x + dir.dx;
            // next y pos is y plus dy
            int ny = y + dir.dy;

            // If the next cell is within bounds of maze,
            // is connected to this cell
            // and hasn't been visited, visit it
            if (maze.withinBounds(nx, ny) && (mazeGrid[y][x] & dir.bit) != 0
                    && (mazeGrid[ny][nx] & 16) == 0) {

                // condensing coordinate
                Position newPos = new Position(nx, ny);

                solution.add(newPos);

                // sets the 5th bit to mark visit
                mazeGrid[ny][nx] |= 16;

                // if maze is solved, halt and return true
                if (MazeSolver.recursiveSolve(newPos, maze))
                    return true;


                // backtracks from current cell to the last one
                solution.removeLast();
                mazeGrid[ny][nx] &= ~16;
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
        double count = maze.getSolution().size();

        String deadEnd = Double.toString(( 100 - ((count + 1) / (maze.getRows() * maze.getCols())) * 100));
        return deadEnd;
    }

    /**
     * For a given maze, finds the total cells required to pass through, for the optimal solution
     */
    public static double TotalPassThrough(Maze maze) {
        double count = maze.getSolution().size();

        double passThrough = ((count+1)/(maze.getRows() * maze.getCols())) * 100;
        return passThrough;
    }
}
