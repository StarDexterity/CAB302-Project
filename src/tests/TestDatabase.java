package tests;

import database.MazeAttribute;
import maze.Maze;

import maze.MazeData;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;

import java.util.ArrayList;

import static database.Database.retrieve;
import static database.Database.save;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {

    @BeforeAll
    public static void PopulateDatabase () {
        Maze mikesMaze = new Maze(4, 4, false);
        mikesMaze.setMazeGrid(DummyMazes.random);
        mikesMaze.setData("Mike Gmail")
                .title("Mike's Maze")
                .description("It's a very fun and good maze");
        save(mikesMaze);

        Maze himsMaze = new Maze(4, 4, false);
        himsMaze.setMazeGrid(DummyMazes.random);
        himsMaze.setData("Him Jogan")
                .title("Him's Cloud Maze")
                .description("It's stored exclusively in the cloud");
        save(himsMaze);

    }

    @Test
    public void RetrieveByTitle() {
        ArrayList<Maze> mazes = retrieve(MazeAttribute.Author, "Mike Gmail");
        assertEquals(mazes.size(), 1);
    }

    @Test
    public void AssignsId() {
        Maze roboMaze = new Maze(4, 4, false);
        roboMaze.setMazeGrid(DummyMazes.random);
        roboMaze.setData("Robot Kevin").title("Robot Maze");
        assertEquals(roboMaze.mazeData.getId(), 0);
        save(roboMaze);
        assertNotEquals(roboMaze.mazeData.getId(), 0);
    }
}
