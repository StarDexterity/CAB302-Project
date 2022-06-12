package tests;

import database.DatabaseConnection;
import maze.data.Maze;

import maze.data.MazeData;
import maze.data.MazeImage;
import maze.data.Position;

import org.junit.jupiter.api.*;

import java.io.File;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static ui.dialog.DatabaseErrorHandler.handle;

public class TestDatabase {

    private static DatabaseConnection connection;

    public static void createDummyMazes() {
        Maze mikesMaze = new Maze(4, 4, false);
        mikesMaze.setMazeGrid(DummyMazes.random);
        mikesMaze.setData("Mike Gmail")
                .title("Mike's Maze")
                .description("It's a very fun and good maze");
        try {
            connection.save(mikesMaze);
        } catch (SQLException e) {
            handle(e, false);
        }

        Maze himsMaze = new Maze(4, 4, false);
        himsMaze.setMazeGrid(DummyMazes.full);
        himsMaze.setData("Him Jogan")
                .title("Him's Cloud Maze")
                .description("It's stored exclusively in the cloud");

        // Image
        Position topLeft = new Position(2,2);
        Position bottomRight = new Position(3,3);

        File file = new File("src/tests/TestImage.jpg");

        MazeImage mazeImage = new MazeImage(topLeft, bottomRight, file);
        himsMaze.placeImage(mazeImage);

        try {
            connection.save(himsMaze);
        } catch (SQLException e) {
            handle(e, false);
        }

        Maze longOnMazeShortOnData = new Maze(4, 6, false);
        longOnMazeShortOnData.setMazeGrid(DummyMazes.x_long);
        try {
            connection.save(longOnMazeShortOnData);
        } catch (SQLException e) {
            handle(e, false);
        }
//
        Maze failMaze = new Maze(4, 4, false);
        failMaze.setMazeGrid(DummyMazes.lateFail);
        failMaze.setData("Moon Microsystems, Inc").title("It almost works!");
        try {
            connection.save(failMaze);
        } catch (SQLException e) {
            handle(e, false);
        }
    }

    @BeforeAll
    public static void PopulateDatabase () throws SQLException {

        Connection testConnection = DriverManager.getConnection("jdbc:mariadb://localhost:3307", "root", "secret");
        Statement reset = testConnection.createStatement();
        reset.execute("DROP DATABASE IF EXISTS testmazeco");
        reset.close();
        testConnection.close();

        DatabaseConnection.instantiate();

        connection = new DatabaseConnection();

        createDummyMazes();
    }

    @Test
    public void TestRetrieveMaze() throws SQLException {
        Maze maze = connection.retrieveMaze(1);
        assertEquals("Mike Gmail", maze.mazeData.getAuthor());
        assertEquals("Mike's Maze", maze.mazeData.getTitle());

        maze = connection.retrieveMaze(2);
        assertEquals("Him Jogan", maze.mazeData.getAuthor());
        assertEquals("Him's Cloud Maze", maze.mazeData.getTitle());

        assertEquals(2, maze.getImages().get(0).getTopLeft().getX());
        assertEquals(2, maze.getImages().get(0).getTopLeft().getY());
        assertEquals(3, maze.getImages().get(0).getBottomRight().getX());
        assertEquals(3, maze.getImages().get(0).getBottomRight().getY());
    }

    @Test
    public void RetrievesMazeGrid() throws SQLDataException {
        Maze maze = null;
        try {
            maze = connection.retrieveMaze(1);
        } catch (SQLException e) {
            handle(e, false);
        }
        assertEquals(Arrays.deepToString(maze.getMazeGrid()), Arrays.deepToString(DummyMazes.random));
    }

    @Test
    public void TestRetrieveMazeThrows() {
        assertThrows(SQLDataException.class, () -> {
            Maze maze = connection.retrieveMaze(9999);
        });
    }

    @Test
    public void AssignsId() {
        Maze roboMaze = new Maze(4, 4, false);
        roboMaze.setMazeGrid(DummyMazes.random);
        roboMaze.setData("Robot Kevin").title("Robot Maze").description("Beep, Boop!");

        assertEquals(roboMaze.mazeData.getId(), 0);

        try {
            connection.save(roboMaze);
        } catch (SQLException e) {
            handle(e, false);
        }

        assertNotEquals(roboMaze.mazeData.getId(), 0);
    }

    @Test
    public void TestUpdate() throws SQLException {
        Maze updatableMaze = new Maze(4, 4, false);
        updatableMaze.setMazeGrid(DummyMazes.fullCoverage);
        updatableMaze.setData("Alice");
        try {
            connection.save(updatableMaze);
        } catch (SQLException e) {
            handle(e, false);
        }

        int mazeId = updatableMaze.mazeData.getId();

        Maze retrievedMaze = connection.retrieveMaze(mazeId);
        retrievedMaze.setData("Bob");
        connection.save(retrievedMaze);

        Maze updatedMaze = connection.retrieveMaze(mazeId);
        assertEquals("Bob", updatedMaze.mazeData.getAuthor());
    }

    @Test
    public void TestDelete() throws SQLException {
        Maze expendableMaze = new Maze(4, 4, false);
        expendableMaze.setMazeGrid(DummyMazes.empty);
        try {
            connection.save(expendableMaze);
        } catch (SQLException e) {
            handle(e, false);
        }

        int mazeId = expendableMaze.mazeData.getId();

        assertDoesNotThrow(() -> {
            connection.retrieveMaze(mazeId);
        });
        connection.delete(mazeId);
        assertThrows(SQLException.class, () -> {
            connection.retrieveMaze(mazeId);
        });
    }

    @Test
    public void ViewRetrieveMazeCatalog() throws SQLException {
        ArrayList<MazeData> mazes = connection.retrieveMazeCatalogue();
        for (MazeData m : mazes) {
            System.out.println(m.getAuthor());
            System.out.println(m.getTitle());
            System.out.println(m.getDescription());
            System.out.println(m.getCreationDate());
            System.out.println(m.getLastEditDate());
        }
    }
}
