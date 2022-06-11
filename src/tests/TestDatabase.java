package tests;

import database.DatabaseConnection;
import maze.data.Maze;

import maze.data.MazeData;
import maze.data.MazeImage;
import maze.data.Position;
import org.javatuples.Triplet;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
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
        Position topLeft = new Position(0,4);
        Position bottomRight = new Position(4,0);

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
        System.out.println(maze.getImages().get(0).getTopLeft().getX());
        System.out.println(maze.getImages().get(0).getTopLeft().getY());
        System.out.println(maze.getImages().get(0).getBottomRight().getX());
        System.out.println(maze.getImages().get(0).getBottomRight().getY());
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
    public void TestRetrieveMazeCatalog() throws SQLException {
        ArrayList<MazeData> mazes = connection.retrieveMazeCatalogue();
        for (MazeData m : mazes) {
            System.out.println(m.getAuthor());
            System.out.println(m.getTitle());
            System.out.println(m.getDescription());
            System.out.println(m.getCreationDate());
            System.out.println(m.getLastEditDate());
        }
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
}
