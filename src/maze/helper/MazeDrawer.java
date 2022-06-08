package maze.helper;

import maze.data.Maze;
import maze.data.MazeDisplayOptions;

import java.awt.image.BufferedImage;

/**
 * Maze drawer class draws a maze to a buffered image
 */
public class MazeDrawer {
    public static BufferedImage drawMaze(Maze maze, MazeDisplayOptions displayOptions) {
        return null;
    }

    public static BufferedImage drawMaze(Maze maze) {
        return drawMaze(maze, new MazeDisplayOptions());
    }
}
