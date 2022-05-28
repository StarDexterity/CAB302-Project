package maze;

import java.util.ArrayList;
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

    // public methods (testable)

    /**
     * Places an Image in the maze. This operation is only successful if the given Image is well contained within the maze.
     * @param image
     */
    public void placeImage(MazeImage image) {
        notifyListeners();
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

    /**
     * Predicate function checks whether for a cell coordinate (x, y) if it can pass in the given direction
     * @param x
     * @param y
     * @param d
     * @return
     */
    public boolean isWall(int x, int y, Direction d) {
        return ((mazeGrid[y][x] & d.bit) != 0);
    }

    /**
     * Sets a single wall of the cell at the given cell coordinate (x, y) in the direction d, to the given value
     * @param x
     * @param y
     * @param d
     * @param value
     */
    public void setWall(int x, int y, Direction d, boolean value) {
        // next x and y coordinates
        int nX = x + d.dx;
        int nY = y + d.dy;

        // If the neighbor is within the bounds of the maze
        if (withinBounds(nX, nY)) {
            // depending on the value, set or clear this bit and the neighbours opposite bit
            if (value) {
                mazeGrid[y][x] |= d.bit;
                mazeGrid[nY][nX] |= d.opposite.bit;
            } else {
                mazeGrid[y][x] &= ~(d.bit);
                mazeGrid[nY][nX] &= ~(d.opposite.bit);
            }
        }
        notifyListeners();
    }

    // Observer design pattern
    public interface MazeListener {
        void mazeAltered();
    }

    private ArrayList<MazeListener> listeners = new ArrayList<MazeListener>();

    public void addListener(MazeListener ml) {
        listeners.add(ml);
    }

    private void notifyListeners() {
        for (MazeListener l : listeners) {
            l.mazeAltered();
        }
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
