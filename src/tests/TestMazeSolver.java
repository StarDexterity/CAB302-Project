package tests;

import maze.data.Maze;
import maze.enums.Direction;
import maze.enums.SolveStatus;
import maze.helper.MazeSolver;
import maze.data.Position;
import org.junit.jupiter.api.*;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static tests.DummyMazes.*;

public class TestMazeSolver {

    private LinkedList<Position> nullSolution = new LinkedList<>();

    private Maze createAndSolve(int nCols, int nRows, int[][] mazeGrid) {
        Maze maze = new Maze(nCols, nRows, false);
        maze.setMazeGrid(mazeGrid);
        MazeSolver.solve(maze);
        return maze;
    }

    @Test
    public void SolveEmpty() {
        Maze maze = createAndSolve(4, 4, empty);
        assertNotEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void NotSolveFull() {
        Maze maze = createAndSolve(4, 4, full);
        assertEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void SolveRandom() {
        Maze maze = createAndSolve(4, 4, random);
        assertNotEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void NotSolveLateFail() {
        Maze maze = createAndSolve(4, 4, lateFail);
        assertEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void SolveFullCoverage() {
        Maze maze = createAndSolve(5, 5, fullCoverage);
        assertNotEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void SolveXLong() {
        Maze maze = createAndSolve(4, 6, x_long);
        assertNotEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void SolveYWide() {
        Maze maze = createAndSolve(6, 4, y_wide);
        assertNotEquals(maze.getSolution(), nullSolution);
    }

    @Test
    public void MazeBecomesUnsolved() {
        Maze maze = createAndSolve(4, 4, random);
        assertEquals(maze.getSolveStatus(), SolveStatus.SOLVED);
        maze.setPath(0, 0, Direction.N, false);
        assertEquals(maze.getSolveStatus(), SolveStatus.UNSOLVED);
        MazeSolver.solve(maze);
        assertEquals(maze.getSolveStatus(), SolveStatus.SOLVED);
    }
}
