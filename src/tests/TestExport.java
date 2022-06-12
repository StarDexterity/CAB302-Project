package tests;

import maze.Export;
import maze.data.Maze;
import maze.data.MazeDisplayOptions;
import maze.data.Selection;
import maze.helper.MazeDrawer;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestExport {

    Maze maze = new Maze(5,5,true);
    MazeDisplayOptions m = new MazeDisplayOptions();
    Selection s = new Selection();

    @Test
    public void TestDraw() {
       BufferedImage b = MazeDrawer.drawMaze(maze, m, s);
       assertNotNull(b);
    }
}
