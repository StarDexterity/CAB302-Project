package tests;

import maze.Maze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tests.DummyMazeData.*;

public class TestMaze {

    @Test
    public void DoesNotSetBadData() {
        Maze maze = new Maze(4, 4, false);
        assertThrows(Exception.class, () -> {
            maze.setMazeGrid(badData);
        });
    }

    @Test
    public void DoesNotSetEmptySet() {
        Maze maze = new Maze(4, 4, false);
        assertThrows(Exception.class, () -> {
            maze.setMazeGrid(emptySet);
        });
    }

    @Test
    public void SetRandom() {
        Maze maze = new Maze(4, 4, false);
        assertDoesNotThrow(() -> {
            maze.setMazeGrid(random);
        });
    }

    @Test
    public void DoesNotSetIncorrectDimensions() {
        Maze maze = new Maze(99, 99, false);
        assertThrows(Exception.class, () -> {
            maze.setMazeGrid(random);
        });
    }

}
