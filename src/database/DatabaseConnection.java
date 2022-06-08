package database;

import maze.data.Maze;
import maze.data.MazeData;

import java.sql.*;
import java.time.Instant;
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
        } catch (SQLException sqlError) {
            System.err.println(sqlError);
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
    public void save(Maze maze) throws SQLException {
        MazeData mazeData = maze.mazeData;

        if (mazeData.getId() == 0) {
            // If the maze doesn't have an ID, create a new entry in the database.

            PreparedStatement insert = connection.prepareStatement("INSERT INTO Maze\n" +
                            "(author, title, description, creationDate, lastEditDate, mazeData, nCols, nRows)\n" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            insert.clearParameters();
            insert.setString(1, mazeData.getAuthor());
            insert.setString(2, mazeData.getTitle());
            insert.setString(3, mazeData.getDescription());
            insert.setTimestamp(4, Timestamp.from(mazeData.getCreationDate()));
            insert.setTimestamp(5, Timestamp.from(mazeData.getLastEditDate()));
            insert.setInt(6, 1);
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
    }

    //TODO: Delete
    /**
     * Updates any changes from the maze to the appropriate record in the database
     * @param maze The @{@link Maze} object to be updated
     */
    public void update(Maze maze) {

    }

    public Maze retrieveMaze(int id) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM Maze WHERE id = ?");

        select.clearParameters();
        select.setInt(1, id);
        select.executeUpdate();

        ResultSet result = select.getResultSet();

        while (result.next()) {
            int mazeID = result.getInt(1);
            String author = result.getString(2);
            String title = result.getString(3);
            String description = result.getString(4);
            Instant creationDate = result.getTimestamp(5).toInstant();
            Instant lastEditDate = result.getTimestamp(6).toInstant();



            MazeData Mazedata = new MazeData(mazeID, author, title, description, creationDate, lastEditDate);
        }


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
}
