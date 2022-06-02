package maze.data;

import maze.enums.Direction;
import maze.enums.GenerationOption;
import maze.enums.SolveStatus;
import maze.helper.MazeGenerator;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 */
public class Maze {

    //TODO: Make private;
    private final int[][] mazeGrid;
    private final int nCols;
    private final int nRows;
    private SolveStatus solveStatus;

    private LinkedList<Position> solution;

    public MazeData mazeData;

    private ArrayList<MazeImage> logos;

    // constructors

    /**
     * Constructs a maze object, using the @{@link GenerationOption} enum to choose the algorithm used to generate the maze
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param option Generation option
     */
    public Maze(int nCols, int nRows, GenerationOption option) {
        this.nCols = nCols;
        this.nRows = nRows;

        // initialise solution
        solution = new LinkedList<>();

        mazeData = new MazeData();

        // Generates the maze using the given GenerationOption
        mazeGrid = MazeGenerator.generateMaze(nCols, nRows, option);
    }

    /**
     * Legacy constructor
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param automatic Should maze be generated automatically
     */
    public Maze(int nCols, int nRows, boolean automatic) {
        this.nCols = nCols;
        this.nRows = nRows;

        solution = new LinkedList<>();

        mazeData = new MazeData();

        // Generates the maze with DFS or empty, depending on boolean value
        if (automatic) {
            mazeGrid = MazeGenerator.generateMaze(nCols, nRows, GenerationOption.DFS);
        }
        else {
            mazeGrid = MazeGenerator.generateMaze(nCols, nRows, GenerationOption.EMPTY);
        }

    }

    // From database
     public Maze(int nCols, int nRows, int[][] mazeGrid, MazeData mazeData, ArrayList<MazeImage> logos) {
        this.nCols = nCols;
        this.nRows = nRows;
        this.mazeGrid = mazeGrid;
        this.mazeData = mazeData;
        this.logos = logos;
    }

    // getters and setters
    public int[][] getMazeGrid() {
        return mazeGrid;
    }

    //TODO: Throw exception if data is not correct dimensions
    public void setMazeGrid (int[][] mazeGrid) throws IndexOutOfBoundsException {
        //this.mazeGrid = mazeGrid;
        // cannot change mazeGrid, must scan through instead
    }

    public int getCols() {
        return nCols;
    }

    public int getRows() {
        return nRows;
    }

    public LinkedList<Position> getSolution() {
        return solution;
    }

    public void setSolution(LinkedList<Position> solution) {
        this.solution = solution;
    }

    // public methods
    /**
     * Places an Image in the maze. This operation is only successful if the given Image is well contained within the maze.
     * @param image Image to place
     */
    public void placeImage(MazeImage image) {
        mazeChanged();
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

    public static boolean withinBounds(int x, int y, int nCols, int nRows) {
        return (x >= 0 && x < nCols) && (y >= 0 && y < nRows);
    }

    public static boolean withinBounds(Position p, int nCols, int nRows) {
        int x = p.getX();
        int y = p.getY();
        return withinBounds(x, y, nCols, nRows);
    }

    /**
     * Predicate function checks whether for a cell coordinate (x, y) if it can pass in the given direction
     * @param x
     * @param y
     * @param dir
     * @return
     */
    public boolean isPath(int x, int y, Direction dir) {
        return ((mazeGrid[y][x] & dir.bit) != 0);
    }

    public boolean isPath(Position pos, Direction dir) {
        int x = pos.getX();
        int y = pos.getY();
        return isPath(x, y, dir);
    }

    /**
     * Sets a single wall of the cell at the given cell coordinate (x, y) in the direction d, to the given value
     * @param x
     * @param y
     * @param dir
     * @param isPath
     */
    public void setPath(int x, int y, Direction dir, boolean isPath) {
        // next x and y coordinates
        int nX = x + dir.dx;
        int nY = y + dir.dy;

        // If the neighbor is within the bounds of the maze
        if (withinBounds(nX, nY)) {
            // depending on the value, set or clear this bit and the neighbours opposite bit
            if (isPath) {
                mazeGrid[y][x] |= dir.bit;
                mazeGrid[nY][nX] |= dir.opposite.bit;
            } else {
                mazeGrid[y][x] &= ~(dir.bit);
                mazeGrid[nY][nX] &= ~(dir.opposite.bit);
            }
        }
        mazeChanged();
    }

    public void setPath(Position pos, Direction dir, boolean isPath) {
        int x = pos.getX();
        int y = pos.getY();
        setPath(x, y, dir, isPath);
    }

    public void setAll(int x, int y, boolean isPath) {
        for (Direction dir : Direction.values()) {
            // next x and y coordinates
            int nX = x + dir.dx;
            int nY = y + dir.dy;

            // If the neighbor is within the bounds of the maze
            if (withinBounds(nX, nY)) {
                // depending on the value, set or clear this bit and the neighbours opposite bit
                if (isPath) {
                    mazeGrid[y][x] |= dir.bit;
                    mazeGrid[nY][nX] |= dir.opposite.bit;
                } else {
                    mazeGrid[y][x] &= ~(dir.bit);
                    mazeGrid[nY][nX] &= ~(dir.opposite.bit);
                }
            }
        }
        mazeChanged();
    }

    public void setAll(Position pos, boolean isPath) {
        int x = pos.getX();
        int y = pos.getY();
        setAll(x, y, isPath);
    }

    // Based on the Build Pattern. Have to use this method to edit maze data
    public MazeData setData(String author) {
        mazeData.updateData(author);
        return this.mazeData;
    }

    public SolveStatus getSolveStatus() {
        return solveStatus;
    }

    public void setSolveStatus(SolveStatus solveStatus) {
        this.solveStatus = solveStatus;
        solveStatusChange();
    }

    // Observer design pattern
    public interface MazeListener {
        default void mazeChanged() {}
        default void solveStatusChanged(SolveStatus status) {}
    }

    private final ArrayList<MazeListener> listeners = new ArrayList<>();

    public void addListener(MazeListener ml) {
        listeners.add(ml);
    }

    private void mazeChanged() {
        setSolveStatus(SolveStatus.UNSOLVED);
        for (MazeListener l : listeners) {
            l.mazeChanged();
        }
    }

    private void solveStatusChange() {
        for (MazeListener l: listeners) {
            l.solveStatusChanged(solveStatus);
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
