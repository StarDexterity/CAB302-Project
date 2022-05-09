import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
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

    private BitSet[] mazeRows;
    private BitSet[] mazeColumns;

    private ArrayList<MazeImage> logos;

    // constructors

    //Minimum possible constructor
    public Maze(Dimension dimension){
        this.dimension = dimension;
        this.mazeRows = new BitSet[dimension.height + 1];
        this.mazeColumns = new BitSet[dimension.width + 1];
    }

    /**
     * For creating a new Maze
     * @param title
     * @param author
     * @param description
     * @param dimension
     * @param creationDate
     * @param lastEditDate
     * @param mazeRows
     * @param mazeColumns
     * @param logos
     */
    //TODO: Make a better constructor that allows defaults.
    // Choose when to create the maze object, whether once the maze is generated,
    // created with a preview screen, or created immediately.
    public Maze(
                int id,
                String title,
                String author,
                String description,
                Dimension dimension,
                Date creationDate,
                Date lastEditDate,
                BitSet[] mazeRows,
                BitSet[] mazeColumns,
                ArrayList<MazeImage> logos) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.dimension = dimension;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        // Create BitSet to store maze walls with enough room to store all the walls in the maze + 1 for the borders
        // <MazeRows, MazeColumns init here (e.g. all 1 or all 0)>
        this.mazeRows = mazeRows;
        this.mazeColumns = mazeColumns;

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

    //TODO: Remove
    public void devSetMazeData(BitSet[] mazeRows, BitSet[] mazeColumns) {
        this.mazeRows = mazeRows;
        this.mazeColumns = mazeColumns;
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
     *
     * @return
     */
    //Why can't I use Pair! Foolish corretto
    public boolean canPass(Dimension position, Direction direction) {
        return !(switch (direction){
            case NORTH -> mazeRows[position.height + 1].get(dimension.width);
            case SOUTH -> mazeRows[position.height].get(dimension.width);
            case EAST -> mazeColumns[position.width + 1].get(dimension.height);
            case WEST -> mazeColumns[position.width].get(dimension.height);
        });
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
