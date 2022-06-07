package tests;

import database.DatabaseConnection;
import database.MazeAttribute;
import maze.data.Maze;

import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;

import static database.DatabaseConnection.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {

    private static DatabaseConnection connection;

    public static void createDummyMazes(DatabaseConnection connection) {
        Maze mikesMaze = new Maze(4, 4, false);
        mikesMaze.setMazeGrid(DummyMazes.random);
        mikesMaze.setData("Mike Gmail")
                .title("Mike's Maze")
                .description("It's a very fun and good maze");
        connection.save(mikesMaze);

        Maze himsMaze = new Maze(4, 4, false);
        himsMaze.setMazeGrid(DummyMazes.random);
        himsMaze.setData("Him Jogan")
                .title("Him's Cloud Maze")
                .description("It's stored exclusively in the cloud");
        connection.save(himsMaze);
    }

    @BeforeAll
    public static void PopulateDatabase () {
        connection = new DatabaseConnection();
        createDummyMazes(connection);
    }

//    @Test
//    public void RetrieveByTitle() {
//        ArrayList<Maze> mazes = connection.retrieve(MazeAttribute.Author, "Mike Gmail");
//        assertEquals(mazes.size(), 1);
//    }

    @Test
    public void AssignsId() {
        Maze roboMaze = new Maze(4, 4, false);
        roboMaze.setMazeGrid(DummyMazes.random);
        roboMaze.setData("Robot Kevin").title("Robot Maze");

        assertEquals(roboMaze.mazeData.getId(), 0);

        connection.save(roboMaze);

        assertNotEquals(roboMaze.mazeData.getId(), 0);
    }
}
