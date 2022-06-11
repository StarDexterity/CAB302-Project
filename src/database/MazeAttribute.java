package database;

//TODO: I think this is a dead class
/**
 * A list of columns for a table that stores Mazes. It is used by the Default.DatabaseConnection class to allow filtering of results.
 */
public enum MazeAttribute {
    ID,
    Dimension,
    Title,
    Author,
    Description,
    CreationDate,
    LastEditDate
}
