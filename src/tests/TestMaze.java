package tests;

import maze.Direction;
import maze.Maze;
import maze.MazeSolver;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static tests.DummyMazes.*;

public class TestMaze {

    private Maze createAndPopulate(int nCols, int nRows, int[][] mazeGrid) {
        Maze maze = new Maze(nCols, nRows, false);
        maze.setMazeGrid(mazeGrid);
        return maze;
    }

    // Testing setMazeGrid

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

    // Testing getSolutions

    @Test
    public void DoesNotReturnSolutionWhenUnsolved() {
        Maze maze = new Maze(4, 4, true);
        assertThrows(Exception.class, () -> {
            maze.getSolution();
        });
    }

    @Test
    public void ReturnsSolutionWhenSolved() {
        Maze maze = new Maze(4, 4, true);
        MazeSolver.solve(0, maze);
        assertDoesNotThrow( () -> {
            LinkedList<Integer> solution = maze.getSolution();
        });
    }

    // Testing withinBounds

    @Test
    public void AlwaysOutOfBounds() {
        Maze maze = new Maze(4, 4, true);
        assertFalse(maze.withinBounds(999, 999));
    }

    @Test
    public void AlwaysInBounds() {
        Maze maze = new Maze(4, 4, true);
        assertTrue(maze.withinBounds(1, 1));
    }

    @Test
    public void InScaledBounds() {
        Maze maze = new Maze(5, 5, true);
        assertTrue(maze.withinBounds(4, 4));
    }

    @Test
    public void OutOfScaledInBounds() {
        Maze maze = new Maze(4, 4, true);
        assertFalse(maze.withinBounds(4, 4));
    }

    // Testing isWall

    @Test
    public void AlwaysIsWallTrue() {
        Maze maze = new Maze(4, 4, true);
        assertTrue(maze.isWall(0, 0, Direction.W));
    }

    @Test
    public void RandomIsWallTrue() {
        Maze maze = createAndPopulate(4, 4, random);
        assertTrue(maze.isWall(0, 0, Direction.W));
    }

    @Test
    public void RandomIsWallFalse() {
        Maze maze = createAndPopulate(4, 4, random);
        assertFalse(maze.isWall(1, 1, Direction.W));
    }

    @Test
    public void ThrowsIsWallOutOfBounds() {
        Maze maze = new Maze(4, 4, true);
        assertThrows(Exception.class, () -> {
            maze.isWall(999,999, Direction.N);
        });
    }


}
