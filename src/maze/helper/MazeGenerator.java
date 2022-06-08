package maze.helper;

import maze.data.Position;
import maze.enums.Direction;
import maze.data.Maze;
import maze.enums.GenerationOption;

import java.util.*;

// good resource: https://stackoverflow.com/questions/38502/whats-a-good-algorithm-to-generate-a-maze

/**
 * Maze generator class presents one function that allows mazes to be generated based on a given option using
 * a variety of private implementations of randomised graph traversal algorithms to produce
 * varying results of fun mazes.
 */
public final class MazeGenerator {
    // Disable constructor
    private MazeGenerator(){}

    /**
     * Generates a maze with the supplied generation option
     * @param nCols The number of columns the maze will have
     * @param nRows The number of rows the maze will have
     * @param option The generation style for the maze
     * @return
     */
    public static int[][] generateMaze(int nCols, int nRows, GenerationOption option) {
        // initialise mazeGrid array with given column and row size
        int[][] mazeGrid = new int[nRows][nCols];

        // if number of columns or rows is 0, return mazeGrid early
        if (nCols == 0 || nRows == 0) return mazeGrid;

        // origin point to maze generation algorithms will be random
        Random r = new Random();
        int x = r.nextInt(nCols);
        int y = r.nextInt(nRows);

        switch (option) {
            case DFS -> generateMazeDFS(x, y, nCols, nRows, mazeGrid);
            case PRIM -> generateMazePrim(x, y, nCols, nRows, mazeGrid);
            case ALDOUS -> generateMazeAldousBroder(x, y, nCols, nRows, mazeGrid);
            case EMPTY -> generateEmptyMaze(nCols, nRows, mazeGrid);
        }
        return mazeGrid;
    }

    /**
     * Generates an empty maze
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param grid The internal maze grid
     */
    private static void generateEmptyMaze(int nCols, int nRows, int[][] grid) {
        // gets an array of the directions, then picks one at random
        Direction[] dirs = Direction.values();

        // this can be more efficient
        for (int y = 0; y < nRows; y++) {
            for (int x = 0; x < nCols; x++) {
                // for each direction (N, E, S, W) in a random order
                for (Direction dir : dirs) {
                    // gets the coordinates of a neighbor of the current vertex,
                    // in the direction of dir
                    int nextX = x + dir.dx;
                    int nextY = y + dir.dy;

                    // If the neighbor is within the bounds of the maze
                    if (Maze.withinBounds(nextX, nextY, nCols, nRows)) {
                        // Set this vertex bit and the neighbors to the opposite direction of this bit
                        // This operation connects these two vertices together
                        grid[y][x] |= dir.bit;
                        grid[nextY][nextX] |= dir.opposite.bit;
                    }
                }
            }
        }

    }


    /**
     * Generates a maze, using a randomised recursive backtracking DFS based algorithm
     * @param x x-position
     * @param y y-position
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param grid The internal maze grid
     */
    private static void generateMazeDFS(int x, int y, int nCols, int nRows, int[][] grid) {
        // gets an array of the directions, then picks one at random
        Direction[] dirs = Direction.values();
        Collections.shuffle(Arrays.asList(dirs));

        // for each direction in a random order
        for (Direction dir : dirs) {
            // gets the coordinates of the neighbor in given direction,
            int nX = x + dir.dx;
            int nY = y + dir.dy;

            // If the neighbor is within the bounds of the maze, and hasn't been visited
            if (Maze.withinBounds(nX, nY, nCols, nRows) && grid[nY][nX] == 0) {
                // connect these two vertices together
                grid[y][x] |= dir.bit;
                grid[nY][nX] |= dir.opposite.bit;

                // Call this function on the neighboring cell
                generateMazeDFS(nX, nY, nCols, nRows, grid);
            }
        }
    }

    // TODO: Delete poor generation function
    /**
     * Generates a maze using a randomised BFS based algorithm (does not produce a good result)
     * @param startX Starting x position
     * @param startY Starting y position
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param grid The internal maze grid
     */
    private static void generateMazeBFS(int startX, int startY, int nCols, int nRows, int[][] grid) {
        LinkedList<Position> queue = new LinkedList<>();
        queue.addLast(new Position(startX, startY));

        while (!queue.isEmpty()) {
            // get the current cell position
            Position current = queue.pop();
            int x = current.getX();
            int y = current.getY();

            // gets an array of the directions, then picks one at random
            Direction[] dirs = Direction.values();
            Collections.shuffle(Arrays.asList(dirs));

            // for each direction in a random order
            for (Direction dir : dirs) {
                // gets the coordinates of a neighbor of the current vertex,
                // in the direction of dir
                int nX = x + dir.dx;
                int nY = y + dir.dy;

                // If the neighbor is within the bounds of the maze, and has not been visited
                if (Maze.withinBounds(nX, nY, nCols, nRows) && grid[nY][nX] == 0) {
                    // Set this vertex bit and the neighbors to the opposite direction of this bit
                    // This operation connects these two vertices together
                    grid[y][x] |= dir.bit;
                    grid[nY][nX] |= dir.opposite.bit;

                    // add the next position to the queue
                    queue.addLast(new Position(nX, nY));
                }
            }
        }
    }

    /**
     * Generates a maze using a randomised Prim based algorithm
     * The algorithm starts after one cell has been randomly selected as the origin,
     * and added to the frontier list.
     * 1. Choose an arbitrary cell from the frontier list,
     *      remove it from the frontier list and add it to the maze list.
     * 2. Add all neighboring cells that have not yet been visited to the frontier list
     * 3. If the current cell does not have an edge, and is adjacent from one or more visited cells
     * pick a neighboring cell at random, and connect them
     * 4. Repeat steps 1 to 3 until the frontier list is empty
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param grid The internal maze grid
     */
    private static void generateMazePrim(int startX, int startY, int nCols, int nRows, int[][] grid) {
        // init random
        Random r = new Random();

        // stores all cells in the maze
        LinkedList<Position> mazeList = new LinkedList<>();

        // stores all neighbour cells of cells in the maze
        LinkedList<Position> frontierList = new LinkedList<>();

        // the origin
        Position origin = new Position(startX, startY);
        frontierList.add(origin);

        while (!frontierList.isEmpty()) {
            // get the current cell position
            Position current = frontierList.get(r.nextInt(frontierList.size()));
            int x = current.getX();
            int y = current.getY();

            // remove current cell from frontier and add it to the maze
            frontierList.remove(current);
            mazeList.add(current);

            // gets an array of the directions, then shuffles them
            Direction[] dirs = Direction.values();
            Collections.shuffle(Arrays.asList(dirs));

            for (Direction dir : dirs) {
                // gets the coordinates of a neighbor
                int nX = x + dir.dx;
                int nY = y + dir.dy;
                Position neighbor = new Position(nX, nY);

                // If the neighbor is within the bounds of the maze
                if (Maze.withinBounds(nX, nY, nCols, nRows)) {
                    // if the neighbor is part of the maze
                    if (mazeList.contains(neighbor)) {
                        // and this cell has no paths
                        if (grid[y][x] == 0) {
                            // connect these two cells together
                            grid[y][x] |= dir.bit;
                            grid[nY][nX] |= dir.opposite.bit;
                        }
                    }
                    else if (!frontierList.contains(neighbor)) {
                        // if the cell is not part of the maze and not already part of the frontier, add it
                        frontierList.add(neighbor);
                    }
                }

            }
        }
    }

    /**
     * Generates a maze using a randomised Aldous Broder based algorithm
     * 1. Choose a cell, any cell
     * 2. Choose a neighbor of the cell and travel to it.
     * If the neighbor has not yet been visited, connect the cells and add the new cell to the visited list
     * 3. Repeat step 2 until all cell have been visited.
     * @param nCols Number columns
     * @param nRows Number of rows
     * @param grid The internal maze grid
     */
    private static void generateMazeAldousBroder(int startX, int startY, int nCols, int nRows, int[][] grid) {
        LinkedList<Position> visited = new LinkedList<>();
        int total = nCols * nRows;

        // initialize position with starting values
        int x = startX;
        int y = startY;
        visited.add(new Position(x, y));

        while (visited.size() != total) {
            // gets an array of the directions, then picks one at random
            Direction[] dirs = Direction.values();
            Collections.shuffle(Arrays.asList(dirs));
            Direction dir = dirs[0];

            // gets the coordinates of a neighbour,
            int nX = x + dir.dx;
            int nY = y + dir.dy;

            // If the neighbor is within the bounds of the maze
            if (Maze.withinBounds(nX, nY, nCols, nRows)) {

                // if the neighbour has not been visited, mark it as visited and add it to the visited list
                if (grid[nY][nX] == 0) {
                    // connect these two vertices together
                    grid[y][x] |= dir.bit;
                    grid[nY][nX] |= dir.opposite.bit;

                    // add the next position to the queue
                    visited.add(new Position(nX, nY));
                }

                // set current cell to next cell
                x = nX;
                y = nY;
            }
        }
    }
}
