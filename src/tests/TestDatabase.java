package tests;

import maze.Maze;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {

    @BeforeEach
    public void ConstructMaze() {
        Maze maze = new Maze(4,4,false);
    }

    @Test
    public void TestNull () {

    }
}
