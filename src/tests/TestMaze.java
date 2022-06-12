package tests;

import maze.data.Maze;
import maze.data.MazeImage;
import maze.data.Position;
import maze.enums.Direction;
import maze.helper.MazeSolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestMaze {

    Position topLeft = new Position(2,2);
    Position bottomRight = new Position(3,3);
    URL imageUrl = this.getClass().getResource("TestImage.jpg");
    File file = Paths.get(imageUrl.toURI()).toFile();
    MazeImage m = new MazeImage(topLeft, bottomRight, file);

    public TestMaze() throws URISyntaxException {
    }

    @Test
    public void NotWithinBounds(){
        Maze maze = new Maze(5,5,true);
        assertNotEquals(maze.withinBounds(6,6), true);
    }

    @Test
    public void CheckPath(){
        Maze maze = new Maze(5,5,false);
        boolean x =maze.isPath(1,1, Direction.E);
        assertEquals(x, true);
    }

    @Test
    public void CheckEnabled(){
        Maze maze = new Maze(5,5,true);
        maze.placeImage(m);
        assertEquals(maze.isEnabled(2,2),false);
    }

    @Test
    public void SettingPathPass(){
        Maze maze = new Maze(5,5,false);
        maze.setPath(1,1,Direction.E,false);
        boolean x = maze.isPath(1,1,Direction.E);
        assertEquals(x,false);
    }

    @Test
    public void SettingAllPaths(){
        Maze maze = new Maze(5,5,false);
        maze.setAllPaths(3,3,false);
        assertEquals(maze.isPath(3,3,Direction.N), false);
        assertEquals(maze.isPath(3,3,Direction.E), false);
        assertEquals(maze.isPath(3,3,Direction.S), false);
        assertEquals(maze.isPath(3,3,Direction.W), false);
    }

    @Test
    public void TestSetEnabled(){
        Maze maze = new Maze(5,5,true);
        maze.setCellEnabled(3,3,false);
        assertEquals(maze.isEnabled(3,3), false);

    }
}
