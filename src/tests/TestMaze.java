package tests;

import maze.data.Maze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestMaze {

    @Test
    public void NotWithinBounds(){
        Maze maze = new Maze(5,5,true);
        assertNotEquals(maze.withinBounds(6,6), true);
    }
}
