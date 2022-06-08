package database;

import maze.data.Maze;
import maze.data.MazeData;
import maze.data.MazeImage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This static class provides functionality for interacting with the maze database,
 * including storing, retrieving, updating and deleting records from the maze table.
 */
public class DatabaseConnection {
    public static String url = "jdbc:mariadb://localhost:3307";;
    public static String username = "root";
    public static String password = "secret";

    private Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    public Connection testConnection() {
        return connection;
    }

    /**
     * Deletes a maze record from the database
     * @param id The id of the maze record to be deleted
     */
    public void delete(int id){

    }

    /**
     * Saves the given {@link Maze} to the database.
     * If the maze doesn't have an ID, creates an entry in the database and gives it one
     * @param maze The {@link Maze} object to be saved
     */
    public void save(Maze maze) {
        try {
            MazeData mazeData = maze.mazeData;

            if (mazeData.getId() == 0) {
                // If the maze doesn't have an ID, create a new entry in the database.

                PreparedStatement insert = connection.prepareStatement("INSERT INTO Maze\n" +
                        "(author, title, description, creationDate, lastEditDate, mazeGrid, nCols, nRows)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                insert.clearParameters();
                insert.setString(1, mazeData.getAuthor());
                insert.setString(2, mazeData.getTitle());
                insert.setString(3, mazeData.getDescription());
                insert.setTimestamp(4, Timestamp.from(mazeData.getCreationDate()));
                insert.setTimestamp(5, Timestamp.from(mazeData.getLastEditDate()));
                insert.setBlob(6, mazeGridToBlob(maze.getMazeGrid()));
                insert.setInt(7, maze.getCols());
                insert.setInt(8, maze.getRows());
                insert.executeUpdate();

                ResultSet result = insert.getGeneratedKeys();
                result.next();
                maze.mazeData.setId(result.getInt(1));

            } else {
                // If the maze has an ID, update its entry in the database.

                PreparedStatement insert = connection.prepareStatement("UPDATE Maze\n" +
                        "SET author = ?, title = ?, description = ?, creationDate = ?, lastEditDate = ?, mazeData = ?, nCols = ?, nRows = ?;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Maze retrieveMaze(int id) throws SQLDataException {
        try {
            PreparedStatement select = connection.prepareStatement("SELECT * FROM Maze WHERE mazeID = ?");

            select.clearParameters();
            select.setInt(1, id);
            select.executeUpdate();

            ResultSet result = select.getResultSet();

            result.next();

            int mazeID = result.getInt(1);
            String author = result.getString(2);
            String title = result.getString(3);
            String description = result.getString(4);
            Instant creationDate = result.getTimestamp(5).toInstant();
            Instant lastEditDate = result.getTimestamp(6).toInstant();
            int[][] mazeGrid = blobToMazeGrid(result.getBlob(7));
            int nCols = result.getInt(8);
            int nRows = result.getInt(9);

            MazeData mazeData = new MazeData(mazeID, author, title, description, creationDate, lastEditDate);

            ArrayList logos = retrieveLogos();

            return new Maze(nCols, nRows, mazeGrid, mazeData, logos);

        } catch (SQLDataException e) {
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<MazeImage> retrieveLogos() {
        return new ArrayList<MazeImage>();
    }

    public ArrayList<MazeData> retrieveMazeCatalogue() throws SQLException {
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("SELECT mazeID, author, title, description, creationDate, lastEditDate FROM Maze");

        ArrayList<MazeData> mazes = new ArrayList<>();

        while (result.next()) {
            int mazeID = result.getInt(1);
            String author = result.getString(2);
            String title = result.getString(3);
            String description = result.getString(4);
            Instant creationDate = result.getTimestamp(5).toInstant();
            Instant lastEditDate = result.getTimestamp(6).toInstant();

            mazes.add(new MazeData(mazeID, author, title, description, creationDate, lastEditDate));
        }

        return mazes;
    }

    /**
     *
     * @param password
     * @param username
     * @param url
     */
    public static void editDatabaseProperties(String password, String username, String url){

    }

    public static Blob mazeGridToBlob(int[][] mazeGrid) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

            objectStream.writeObject(mazeGrid);
            byte[] data = byteStream.toByteArray();

            return new SerialBlob(data);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public static int[][] blobToMazeGrid(Blob blob) {
        try {

            byte[] data = blob.getBytes(1, (int) blob.length());

            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            return (int[][]) objectStream.readObject();

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}
