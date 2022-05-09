package maze;

import java.util.Arrays;
import java.util.Collections;

public class MazeGenerator {
    /**
     * @param x column no#
     * @param y row no#
     */
    public static void generateMaze(Maze maze, int x, int y) {

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
            if (maze.withinBounds(nextX, nextY) && maze.mazeGrid[nextY][nextX] == 0) {
                // Set this vertex bit and the neighbors to the opposite direction of this bit
                // This operation connects these two vertices together
                maze.mazeGrid[y][x] |= dir.bit;
                maze.mazeGrid[nextY][nextX] |= dir.opposite.bit;

                // Call the recursive call to continue generating the maze,
                // on the neighboring vertex
                generateMaze(maze, nextX, nextY);
            }
        }
    }
}
