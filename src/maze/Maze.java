package maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 */
public class Maze {
    private int id;
    private String title;
    private String author;
    private String description;
    private Date creationDate;
    private Date lastEditDate;

    public int[][] mazeGrid;
    public int nCols;
    public int nRows;
    public LinkedList<Integer> solution;

    private ArrayList<MazeImage> logos;

    // constructors

    //Minimum possible constructor
    public Maze(int nCols, int nRows, boolean automatic) {
        this.nCols = nCols;
        this.nRows = nRows;

        // initialize the internal maze data structure
        mazeGrid = new int[nRows][nCols];

        solution = new LinkedList<>();

        // Generates the maze automatically if wanted (starting position is always 0, 0)
        if(automatic == true) {
            MazeGenerator.generateMaze(this, 0, 0);
        }
    }

    /**
     * For creating a new Default.Maze
     * @param title
     * @param author
     * @param description
     * @param creationDate
     * @param lastEditDate
     * @param nRows
     * @param nCols
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
                int nRows,
                int nCols,
                Date creationDate,
                Date lastEditDate,
                ArrayList<MazeImage> logos) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        // Create BitSet to store maze walls with enough room to store all the walls in the maze + 1 for the borders
        // <MazeRows, MazeColumns init here (e.g. all 1 or all 0)>
        this.nRows = nRows;
        this.nCols = nCols;

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

    /**
     * Is the supplied x and y position of a vertex within the bounds of the maze
     * @param x
     * @param y
     * @return
     */
    public boolean withinBounds(int x, int y) {
        return (x >= 0 && x < nCols) && (y >= 0 && y < nRows);
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
