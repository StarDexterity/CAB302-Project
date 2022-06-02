package maze.helper;

import maze.data.Position;
import maze.enums.Direction;
import maze.data.Maze;
import maze.enums.SolveStatus;

import java.util.LinkedList;

/**
 * This static class finds the solution or a property of a given @{@link Maze} object.
 * Its methods take a Default.Maze object as a parameter, and return a value that describes some property of the maze,
 * including, if it is solvable, percentage of dead ends, and percentage of path to solve.
 */
public final class MazeSolver {

    // Disable constructor
    private MazeSolver(){}

    /**
     * For a given maze, validates if the maze has a solution
     * @param maze A @{@link Maze} object. The solution to the maze is stored within this object
     * @return A bool value indicating whether the maze was successfully solved.
     */
    public static boolean solve(Position pos, Maze maze) {
        maze.getSolution().clear();
        setAllUnvisited(maze);

        boolean solvable = recursiveSolve(pos, maze);
        maze.setSolveStatus(solvable ? SolveStatus.SOLVED : SolveStatus.UNSOLVABLE);
        return solvable;
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

        // set this cell as visited
        mazeGrid[y][x] |= 16;

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

                // if maze is solved, halt and return true
                if (MazeSolver.recursiveSolve(newPos, maze))
                    return true;


                // backtracks from current cell to the last one
                solution.removeLast();
            }
        }
        // All cells have been visited and a solution hasn't been found
        return false;
    }

    private static void setAllUnvisited(Maze maze) {
        int nCols = maze.getCols();
        int nRows = maze.getRows();
        int[][] mazeGrid = maze.getMazeGrid();

        for (int y = 0; y < nRows; y++) {
            for (int x = 0; x < nCols; x++) {
                mazeGrid[y][x] &= ~(1 << 4);
            }
        }
    }

    /**
     * For a given maze, finds the total cells with dead ends
     * @return
     */
    public static double TotalDeadEnds(Maze maze) {

        int array[][] = maze.getMazeGrid();
        double count = 0;

        for (int j = 0; j<array[0].length; j++) {			//loop through array[this][]
            for (int i = 0; i < array.length; i++) {	//loop through array[][this]
                array[i][j] = array[i][j] & 0b1111;
                if (array[j][i]== 8){
                    count++;
                }
                if (array[j][i]== 4){
                    count++;
                }
                if (array[j][i]== 2){
                    count++;
                }
                if (array[j][i]== 1){
                    count++;
                }
            }
        }

        double deadEnd =((((count)/(maze.getnRows() * maze.getnCols()))*100));
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
