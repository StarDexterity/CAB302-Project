package maze;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 */
public class Maze {

    //TODO: Make private;
    public int[][] mazeGrid;
    public int nCols;
    public int nRows;
    public LinkedList<Integer> solution;

    public MazeData mazeData;

    private ArrayList<MazeImage> logos;

    // constructors
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

        mazeData = new MazeData();
    }

    // From database
    public Maze(int nCols, int nRows, int[][] mazeGrid, MazeData mazeData, ArrayList<MazeImage> logos) {
        this.nCols = nCols;
        this.nRows = nRows;
        this.mazeGrid = mazeGrid;
        this.mazeData = mazeData;
        this.logos = logos;
    }

    // public methods (testable)

    //TODO: Throw exception if data is not correct dimensions
    public void setMazeGrid (int[][] mazeGrid) {
        this.mazeGrid = mazeGrid;
    }

    public LinkedList<Integer> getSolution() {
        return this.solution;
    }

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

    // Based on the Build Pattern. Have to use this method to edit maze data
    public MazeData setData(String author) {
        mazeData.updateData(author);
        return this.mazeData;
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


