package tests;

import database.DatabaseConnection;
import maze.data.Maze;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {

    private static DatabaseConnection connection;

    public static void createDummyMazes(DatabaseConnection connection) throws SQLException {
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
    public static void PopulateDatabase () throws SQLException {
        connection = new DatabaseConnection();

        Statement statement = connection.testConnection().createStatement();

        statement.execute("CREATE DATABASE IF NOT EXISTS TestMazeCo;");
        statement.execute("USE TestMazeCo;");
        statement.execute("DROP TABLE IF EXISTS Maze;");
        statement.execute("CREATE TABLE Maze (" +
                "mazeID INT AUTO_INCREMENT PRIMARY KEY," +
                "author VARCHAR(32) NOT NULL," +
                "mazeData INT NOT NULL," +
                "creationDate TIMESTAMP NOT NULL," +
                "lastEditDate TIMESTAMP NOT NULL," +
                "nCols INT NOT NULL," +
                "nRows INT NOT NULL," +
                "title VARCHAR(32)," +
                "description TEXT" +
                ");");

        createDummyMazes(connection);
    }

//    @Test
//    public void RetrieveByTitle() {
//        ArrayList<Maze> mazes = connection.retrieve(MazeAttribute.Author, "Mike Gmail");
//        assertEquals(mazes.size(), 1);
//    }

    @Test
    public void AssignsId() throws SQLException {
        Maze roboMaze = new Maze(4, 4, false);
        roboMaze.setMazeGrid(DummyMazes.random);
        roboMaze.setData("Robot Kevin").title("Robot Maze");

        assertEquals(roboMaze.mazeData.getId(), 0);

        connection.save(roboMaze);

        assertNotEquals(roboMaze.mazeData.getId(), 0);
    }
}
