package tests;

import database.DatabaseConnection;
import maze.data.Maze;

import maze.data.MazeData;
import org.junit.jupiter.api.*;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

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
    public static void PopulateDatabase () throws SQLException {
        connection = new DatabaseConnection();

        Statement create = connection.testConnection().createStatement();

        create.execute("CREATE DATABASE IF NOT EXISTS TestMazeCo;");
        create.execute("USE TestMazeCo;");
        create.execute("DROP TABLE IF EXISTS Maze;");
        create.execute("CREATE TABLE Maze (\n" +
                "\tmazeID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "\tauthor VARCHAR(32) NOT NULL,\n" +
                "\ttitle VARCHAR(32) NOT NULL,\n" +
                "\tdescription TEXT NOT NULL,\n" +
                "\tcreationDate TIMESTAMP NOT NULL,\n" +
                "\tlastEditDate TIMESTAMP NOT NULL,\n" +
                "\tmazeGrid BLOB NOT NULL,\n" +
                "\tnCols INT NOT NULL,\n" +
                "\tnRows INT NOT NULL\n" +
                ");");

        createDummyMazes(connection);
    }

    @Test
    public void TestRetrieveMaze() throws SQLException {
        Maze maze = connection.retrieveMaze(1);
        assertEquals("Mike Gmail", maze.mazeData.getAuthor());
        assertEquals("Mike's Maze", maze.mazeData.getTitle());
    }

    @Test
    public void RetrievesMazeGrid() throws SQLDataException {
        Maze maze = connection.retrieveMaze(1);
        assertEquals(Arrays.deepToString(maze.getMazeGrid()), Arrays.deepToString(DummyMazes.random));
    }

    @Test
    public void TestRetrieveMazeThrows() {
        assertThrows(SQLDataException.class, () -> {
            Maze maze = connection.retrieveMaze(9999);
        });
    }

    @Test
    public void TestRetrieveMazeCatalog() throws SQLException {
        ArrayList<MazeData> mazes = connection.retrieveMazeCatalogue();
        for (MazeData m : mazes) {
            System.out.println(m.getTitle());
            System.out.println(m.getCreationDate());
            System.out.println(m.getLastEditDate());
        }
    }

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
