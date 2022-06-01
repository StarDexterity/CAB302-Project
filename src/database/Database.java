package database;

import maze.data.Maze;

import java.util.ArrayList;

/**
 * This static class provides functionality for interacting with the maze database,
 * including storing, retrieving, updating and deleting records from the maze table.
 */
public class Database {
    /**
     * Deletes a maze record from the database
     * @param id The id of the maze record to be deleted
     */
    public static void delete(int id){

    }

    /**
     * Saves the given Default.Maze to the database
     * @param maze The @{@link Maze} object to be saved
     */
    public static void save(Maze maze){
        maze.mazeData.assignId(1);
    }

    /**
     * Updates any changes from the maze to the appropriate record in the database
     * @param maze The @{@link Maze} object to be updated
     */
    public static void update(Maze maze) {

    }

    public static ArrayList<Maze> retrieve(MazeAttribute attribute, String searchTerm) {
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
