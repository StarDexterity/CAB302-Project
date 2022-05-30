package tests;

import maze.Maze;
import maze.MazeSolver;
import org.junit.jupiter.api.*;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TestMazeSolve {

    private LinkedList<Integer> nullSolution = new LinkedList<>();

//    @BeforeEach
//    public void ConstructMaze() {
//
//    }

    @Test
    public void TestEmpty () {
        Maze maze = new Maze(4, 4, false);
        maze.setMazeGrid(DummyMazeData.empty);
        //TODO: Try make this default
        MazeSolver.solve(0, maze);
        assertNotEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void TestFull () {
        Maze maze = new Maze(4, 4, false);
        maze.setMazeGrid(DummyMazeData.full);
        MazeSolver.solve(0, maze);
        assertEquals(maze.getSolution(), nullSolution);
    }
}
