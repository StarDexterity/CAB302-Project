package maze.helper;

import maze.data.Position;
import maze.enums.Direction;
import maze.data.Maze;
import maze.enums.SolveStatus;

import java.util.*;

/**
 * This static class finds the solution or a property of a given @{@link Maze} object.
 * Its methods take a Default.Maze object as a parameter, and return a value that describes some property of the maze,
 * including, if it is solvable, percentage of dead ends, and percentage of path to solve.
 */
public final class MazeSolver {

    // Disable constructor
    private MazeSolver() {}

    /**
     * For a given maze, validates if the maze has a solution
     * @param maze A @{@link Maze} object. The solution to the maze is stored within this object
     * @return A boolean value indicating whether the maze was successfully solved.
     */
    public static boolean solve(Maze maze) {
        maze.setAllUnvisited();

        boolean solvable = solveBFS(maze);
        maze.setAllUnvisited();

        maze.setSolveStatus(solvable ? SolveStatus.SOLVED : SolveStatus.UNSOLVABLE);
        return solvable;
    }

    /**
     * Solves a supplied maze through the use of a breadth first search, as this will find the most optimal solution if there is more than one
     * @param maze The maze the method is trying to solve
     * @return A boolean value stating whether the maze is solvable
     */
    private static boolean solveBFS(Maze maze) {
        // get start and end positions
        Position startPosition = maze.getStart();
        Position endPosition = maze.getEnd();

        // set up queue and add first position
        LinkedList<Position> queue = new LinkedList<>();
        queue.addLast(startPosition);

        // mark root position as visited
        maze.setVisited(startPosition, true);


        while (!queue.isEmpty()) {
            // get the current cell position
            Position current = queue.pop();

            int cX = current.getX();
            int cY = current.getY();

            // if at the end position
            if (endPosition.equals(current)) {
                maze.setSolution(reconstructPath(current));
                return true;
            }

            // for each direction
            for (Direction dir : Direction.values()) {
                // gets the coordinates of a neighbor of the current vertex,
                // in the direction of dir
                int nX = cX + dir.dx;
                int nY = cY + dir.dy;
                Position neighbor = new Position(nX, nY);

                // If the neighbor is within the bounds of the maze, and has not been visited
                if (maze.withinBounds(neighbor)
                        && (maze.isPath(current, dir))
                        && !maze.isVisited(neighbor)
                        && maze.isEnabled(neighbor)) {

                    // mark neighbor as visited
                    maze.setVisited(nX, nY, true);

                    // add neighbor to the end of queue
                    queue.addLast(neighbor);

                    // add parent of neighbor to hash table
                    neighbor.setPrev(current);

                }
            }
        }
        // if this point is reached, the whole maze has been traversed and no solution is found
        return false;
    }

    //TODO: JavaDoc - what is this function?

    /**
     * This function goes back through the the path the solving algorithm
     * took, and reconstructs the path that takes you to the end
     * @param endPosition
     * @return A LinkedList holding the solution to the maze
     */
    private static LinkedList<Position> reconstructPath(Position endPosition) {
        // init solution and add first position
        LinkedList<Position> solution = new LinkedList<>();
        solution.add(endPosition);

        while (endPosition.getPrev() != null) {
            solution.add(endPosition.getPrev());
            endPosition = endPosition.getPrev();
        }

        Collections.reverse(solution);
        return solution;
    }

    /**
     * For a given maze, finds the total cells with dead ends
     * @param maze The maze to check
     * @return A Double value to represent as a percentage
     */
    public static double TotalDeadEnds(Maze maze) {

        int[][] mazeGrid = maze.getMazeGrid();
        double count = 0;

        for (int y = 0; y < maze.getRows(); y++) {			//loop through array[this][]
            for (int x = 0; x < maze.getCols(); x++) {	//loop through array[][this]
                int b = mazeGrid[y][x] & 0b1111;
                if (countSetBits(b) == 3) count++;

            }
        }

        return ((((count) / (maze.getRows() * maze.getCols())) * 100));
    }

    /**
     * Counts the bits that are set in the supplied integer
     * @param n An integer
     * @return The total count of bits that are set
     */
    private static int countSetBits(int n) {
        if (n == 0) return 0;

        return countSetBits(n >> 1) + (n & 0x01);
    }

    /**
     * For a given maze, finds the total cells required to pass through, for the optimal solution
     * @param maze The maze to check
     * @return A Double value to represent as a percentage
     */
    public static double TotalPassThrough(Maze maze) {
        double count = maze.getSolution().size();

        return ((count) / (maze.getRows() * maze.getCols())) * 100;
    }
}
