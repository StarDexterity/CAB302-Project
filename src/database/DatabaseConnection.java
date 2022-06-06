package database;

import maze.data.Maze;
import maze.data.MazeData;

import java.sql.*;
import java.util.ArrayList;

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
        } catch (SQLException sqle) {
            System.err.println(sqle);
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
     * Saves the given Default.Maze to the database
     * @param maze The @{@link Maze} object to be saved
     */
    public void save(Maze maze) throws SQLException {
        MazeData mazeData = maze.mazeData;

        if (mazeData.getId() == 0) {
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO Maze (author, mazeData, creationDate, lastEditDate, nCols, nRows) VALUES (?, ?, ?, ?, ?, ?)");
            insert.clearParameters();
            insert.setString(1, mazeData.getAuthor());
            insert.setInt(2, 1);
            insert.setTimestamp(3, Timestamp.from(mazeData.getCreationDate()));
            insert.setTimestamp(4, Timestamp.from(mazeData.getLastEditDate()));
            insert.setInt(5, maze.getCols());
            insert.setInt(6, maze.getRows());
            insert.executeUpdate();

            //batch here
        }
    }

    /**
     * Updates any changes from the maze to the appropriate record in the database
     * @param maze The @{@link Maze} object to be updated
     */
    public void update(Maze maze) {

    }

    public Maze retrieveIndividualMaze(int id) {
        return null;
    }

    public ArrayList<MazeData> retrieveMazes() throws SQLException {
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("SELECT mazeID, author, title, description, creationDate, lastEditDate FROM maze");

        while (result.next()) {

        }

    }

    /**
     *
     * @param password
     * @param username
     * @param url
     */
    public static void editDatabaseProperties(String password, String username, String url){

    }
}
