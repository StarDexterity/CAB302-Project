import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
public class Maze {
    private int id;
    private String title;
    private String author;
    private String description;
    private Dimension dimension;
    private Date creationDate;
    private Date lastEditDate;
    private int mazeRow;
    private int mazeColumn;
    private ArrayList<MazeImage> logos;

    // constructors
    public Maze(Dimension dimension) {
        this.dimension = dimension;
    }

    public Maze(int id,
                String title,
                String author,
                String description,
                Dimension dimension,
                Date creationDate,
                Date lastEditDate,
                int mazeRow,
                int mazeColumn,
                ArrayList<MazeImage> logos) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.dimension = dimension;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.mazeRow = mazeRow;
        this.mazeColumn = mazeColumn;
        this.logos = logos;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public ArrayList<MazeImage> getLogos() {
        return logos;
    }

    public void setLogos(ArrayList<MazeImage> logos) {
        this.logos = logos;
    }

    // public methods

    /**
     * Generates the borders of the maze. Can be used for both manual and automatic maze generation.
     */
    public void generateBorders() {

    }

    /**
     * Randomly generates the maze object. Is called from the constructor as well as on subsequent regenerations as required.
     */
    public void randomlyGenerate() {

    }

    /**
     * Places an Image in the maze. This operation is only successful if the given Image is well contained within the maze.
     * @param image
     */
    public void placeImage(MazeImage image) {

    }

    // private methods

    /**
     * Validates whether the requested image can be legally placed within the maze.
     * @param image An @{@link MazeImage} object
     * @return
     */
    private boolean validateImage(MazeImage image) {
        return false;
    }
}
