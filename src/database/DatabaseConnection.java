package database;

import maze.data.Maze;
import maze.data.MazeData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    public void save(Maze maze){
        if (maze.mazeData.getId() == 0) {

        }
    }

    /**
     * Updates any changes from the maze to the appropriate record in the database
     * @param maze The @{@link Maze} object to be updated
     */
    public void update(Maze maze) {

    }

    public Maze retrieve(int id) {
        return null;
    }

    public ArrayList<MazeData> retrieveMazes() {
        return null;
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
