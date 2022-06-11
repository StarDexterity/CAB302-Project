package database;

import maze.data.Maze;
import maze.data.MazeData;
import maze.data.MazeImage;
import maze.data.Position;
import org.javatuples.Triplet;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

import java.util.Properties;

/**
 * This class provides functionality for interacting with the maze database,
 * including storing, retrieving, updating and deleting records from the maze table.
 */
public class DatabaseConnection {

    private static final String DRIVER = "jdbc:mariadb://";
    private static Connection connection;
    private static String schema;

    /**
     * Instantiates the connection with the database and
     * stores the connection in a static variable to be used globally.
     * @throws SQLException if the application cannot connect to the database.
     */
    public static void instantiate() throws SQLException {
        Triplet<String, String, String> values = readProperties();
        String url = values.getValue0();
        String username = values.getValue1();
        String password = values.getValue2();

        // Likely to throw SQLException
        connection = DriverManager.getConnection(url, username, password);

        Statement create = connection.createStatement();

        create.execute("CREATE DATABASE IF NOT EXISTS " + schema + ";");
        create.execute("USE " + schema + ";");
        create.execute("CREATE TABLE IF NOT EXISTS Maze (\n" +
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
        create.execute("CREATE TABLE IF NOT EXISTS MazeImage (\n" +
                "\timageID INT NOT NULL,\n" +
                "\tmazeID INT NOT NULL,\n" +
                "\timageData LONGBLOB NOT NULL,\n" +
                "\ttopLeftX INT NOT NULL, \n" +
                "\ttopLeftY INT NOT NULL,\n" +
                "\tbottomRightX INT NOT NULL,\n" +
                "\tbottomRightY INT NOT NULL,\n" +
                "\tPRIMARY KEY (imageID, mazeID),\n" +
                "\tFOREIGN KEY (mazeID) REFERENCES Maze(mazeID) ON DELETE CASCADE\n" +
                ");");

        create.close();
    }

    /**
     * Reads properties from db.props.
     * @return
     */
    public static Triplet readProperties() {
        Properties props = new Properties();
        InputStream input = null;
        Triplet<String, String, String> values = Triplet.with("", "", "");

        try {
            input = new FileInputStream("db.props");

            // load a properties file
            props.load(input);

            // get the property value and set
            values = values.setAt0(DRIVER + props.getProperty("url"));
            values = values.setAt1(props.getProperty("username"));
            values = values.setAt2(props.getProperty("password"));
            schema = props.getProperty("schema");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return values;
        }
    }

    /**
     * Checks if the application is connected to the database. If not, attempts to instantiate a new connection.
     * @throws SQLException if the application cannot connect to the database.
     * The {@link DatabaseConnection} object allows access to run queries on the global {@link Connection} object.
     */
    public DatabaseConnection() throws SQLException {
        if (connection == null) {
            System.err.println("No database connection instantiated. Instantiating new connection.");
            instantiate();
        }
    }

    /**
     * Deletes a maze record from the database
     * @param mazeID The id of the maze record to be deleted
     */
    public void delete(int mazeID) throws SQLException{
            PreparedStatement delete = connection.prepareStatement("DELETE FROM Maze WHERE mazeID = ?");
            delete.clearParameters();
            delete.setInt(1, mazeID);
            delete.executeUpdate();

            delete.close();
    }

    /**
     * Saves the given {@link Maze} to the database.
     * If the maze doesn't have an ID, creates an entry in the database and gives it one
     * @param maze The {@link Maze} object to be saved
     */
    public void save(Maze maze) throws SQLException{

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
            insert.setBlob(6, objectToBlob(maze.getMazeGrid()));
            insert.setInt(7, maze.getCols());
            insert.setInt(8, maze.getRows());
            insert.executeUpdate();

            ResultSet generatedID = insert.getGeneratedKeys();
            generatedID.next();
            maze.mazeData.setId(generatedID.getInt(1));

            insert.close();
            generatedID.close();

            insertImages(maze);


        } else {
            // If the maze has an ID, update its entry in the database.
            PreparedStatement update = connection.prepareStatement("UPDATE Maze\n" +
                    "SET author = ?, title = ?, description = ?, creationDate = ?, lastEditDate = ?, mazeGrid = ?, nCols = ?, nRows = ?\n" +
                    "WHERE mazeID = ?;");
            update.setString(1, mazeData.getAuthor());
            update.setString(2, mazeData.getTitle());
            update.setString(3, mazeData.getDescription());
            update.setTimestamp(4, Timestamp.from(mazeData.getCreationDate()));
            update.setTimestamp(5, Timestamp.from(Instant.now()));
            update.setBlob(6, objectToBlob(maze.getMazeGrid()));
            update.setInt(7, maze.getCols());
            update.setInt(8, maze.getRows());
            update.setInt(9, mazeData.getId());
            update.executeUpdate();

            update.close();

            PreparedStatement deleteImage = connection.prepareStatement("DELETE FROM MazeImage\n" +
                    "WHERE mazeID = ?");
            deleteImage.clearParameters();
            deleteImage.setInt(1, mazeData.getId());
            deleteImage.executeUpdate();

            insertImages(maze);
        }
    }

    private void insertImages (Maze maze) throws SQLException {
        PreparedStatement insertImage = connection.prepareStatement("INSERT INTO MazeImage\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);");

        for (MazeImage image : maze.getImages()) {
            ByteArrayOutputStream imageData = new ByteArrayOutputStream();
            try {
                ImageIO.write(image.getImage(), "png", imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            insertImage.clearParameters();
            insertImage.setInt(1, image.getId());
            insertImage.setInt(2, maze.mazeData.getId());
            insertImage.setBlob(3, objectToBlob(imageData));
            insertImage.setInt(4, image.getTopLeft().getX());
            insertImage.setInt(5, image.getTopLeft().getY());
            insertImage.setInt(6, image.getBottomRight().getX());
            insertImage.setInt(7, image.getBottomRight().getY());
            insertImage.executeUpdate();
        }
        insertImage.close();
    }

    public Maze retrieveMaze(int mazeID) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM Maze WHERE mazeID = ?");

        select.clearParameters();
        select.setInt(1, mazeID);
        select.executeUpdate();

        ResultSet result = select.getResultSet();

        result.next();

        String author = result.getString(2);
        String title = result.getString(3);
        String description = result.getString(4);
        Instant creationDate = result.getTimestamp(5).toInstant();
        Instant lastEditDate = result.getTimestamp(6).toInstant();
        int[][] mazeGrid = (int[][]) blobToObject(result.getBlob(7));
        int nCols = result.getInt(8);
        int nRows = result.getInt(9);

        MazeData mazeData = new MazeData(mazeID, author, title, description, creationDate, lastEditDate);

        select.close();
        result.close();

        Maze maze = new Maze(nCols, nRows, mazeGrid, mazeData);

        placeImages(maze);

        return maze;
    }

    private static void placeImages(Maze maze) throws SQLException {
        PreparedStatement selectImages = connection.prepareStatement("SELECT * FROM MazeImage\n" +
                "WHERE mazeID = ?;");

        selectImages.clearParameters();
        selectImages.setInt(1, maze.mazeData.getId());
        selectImages.executeUpdate();

        ResultSet resultImages = selectImages.getResultSet();

        while (resultImages.next()) {
            int id = resultImages.getInt(1);
            ByteArrayInputStream imageData = blobToByteStream(resultImages.getBlob(3));
            Position topLeft = new Position(resultImages.getInt(4), resultImages.getInt(5));
            Position bottomRight = new Position(resultImages.getInt(6), resultImages.getInt(7));

            MazeImage mazeImage = new MazeImage(id, topLeft, bottomRight, imageData);
            maze.placeImage(mazeImage);
        }
    }

    public ArrayList<MazeData> retrieveMazeCatalogue() throws SQLException{
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

        select.close();
        result.close();

        return mazes;
    }

    public static Blob objectToBlob(Object object) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

            objectStream.writeObject(object);
            byte[] data = byteStream.toByteArray();

            return new SerialBlob(data);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public static Blob objectToBlob(ByteArrayOutputStream byteStream) {
        try {
            byte[] data = byteStream.toByteArray();

            return new SerialBlob(data);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    private static ByteArrayInputStream blobToByteStream(Blob blob) {
        try {
            byte[] data = blob.getBytes(1, (int) blob.length());

            return new ByteArrayInputStream(data);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public static Object blobToObject(Blob blob) {
        try {
            ByteArrayInputStream byteStream = blobToByteStream(blob);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            return objectStream.readObject();

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}
