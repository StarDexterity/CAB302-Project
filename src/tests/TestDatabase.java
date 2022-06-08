package tests;

import database.DatabaseConnection;
import maze.data.Maze;

import maze.data.MazeData;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;

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
        statement.execute("CREATE TABLE Maze (\n" +
                "\tmazeID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "\tauthor VARCHAR(32) NOT NULL,\n" +
                "\ttitle VARCHAR(32) NOT NULL,\n" +
                "\tdescription TEXT NOT NULL,\n" +
                "\tcreationDate TIMESTAMP NOT NULL,\n" +
                "\tlastEditDate TIMESTAMP NOT NULL,\n" +
                "\tmazeGrid BLOB NOT NULL,\n" +
                "    nCols INT NOT NULL,\n" +
                "    nRows INT NOT NULL\n" +
                ");");

        createDummyMazes(connection);
    }

    @Test
    public void TestRetrieveMaze() throws SQLException {
        Maze maze = connection.retrieveMaze(1);
        assertEquals(maze.mazeData.getAuthor(), "Mike Gmail");
    }

    @Test
    public void TestRetrieveMazeThrows() throws SQLException {
        assertThrows(SQLException.class, () -> {
            Maze maze = connection.retrieveMaze(9999);
        });
    }

    @Test
    public void TestRetrieveMazeCatalog() throws SQLException {
        ArrayList<MazeData> mazes = connection.retrieveMazeCatalogue();
        for (MazeData m : mazes) {
            System.out.println(m.getTitle());
            System.out.println(m.getCreationDate().toEpochMilli());
            System.out.println(m.getLastEditDate());
        }
    }

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
