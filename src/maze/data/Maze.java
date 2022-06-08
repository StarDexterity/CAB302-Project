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

    private final int[][] mazeGrid;
    private final int nCols;
    private final int nRows;
    private SolveStatus solveStatus;

    private final Position startPosition;
    private final Position endPosition;

    private LinkedList<Position> solution;

    public MazeData mazeData;

    private ArrayList<MazeImage> images;

    // constructors
    /**
     * Constructs a maze, with bare essentials
     */
    public Maze() {
        this(4, 4, false);
    }

    /**
     * Constructs a maze object, using the @{@link GenerationOption} enum to choose the algorithm used to generate the maze
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param option Generation option
     */
    public Maze(int nCols, int nRows, GenerationOption option) {
        this.nCols = nCols;
        this.nRows = nRows;

        // set start and end position of maze
        startPosition = Position.ZERO;
        endPosition = new Position(nCols - 1, nRows - 1);

        // initialise solution
        solution = new LinkedList<>();
        solveStatus = SolveStatus.UNSOLVED;

        // initialise logos
        images = new ArrayList<>();

        // initialise maze data
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
        this(nCols, nRows, (automatic) ? GenerationOption.DFS : GenerationOption.EMPTY);
    }

    // From database
     public Maze(int nCols, int nRows, int[][] mazeGrid, MazeData mazeData, ArrayList<MazeImage> logos) {
        this(nCols, nRows, true);
        /* soz just for testing
        this.nCols = nCols;
        this.nRows = nRows;
        this.mazeGrid = mazeGrid;
        this.mazeData = mazeData;
        this.logos = logos;
        */
    }

    // getters and setters
    public int[][] getMazeGrid() {
        return mazeGrid;
    }

    public void setMazeGrid (int[][] mazeGrid) {
        for (int y = 0; y < nRows; y++) {
            for (int x = 0; x < nCols; x++) {
                this.mazeGrid[y][x] = mazeGrid[y][x];
            }
        }
    }

    public int getCols() {
        return nCols;
    }

    public int getRows() {
        return nRows;
    }

    /**
     * Returns a clone of the start position
     * @return
     */
    public Position getStart() { return new Position(startPosition); }

    /**
     * Returns a clone of the end position
     * @return
     */
    public Position getEnd() { return new Position(endPosition); }

    public LinkedList<Position> getSolution() {
        return solution;
    }

    public void setSolution(LinkedList<Position> solution) {
        this.solution = solution;
    }

    public ArrayList<MazeImage> getImages() {
        return images;
    }

    // public methods

    /**
     * Is the supplied x and y position of a vertex within the bounds of the maze
     * @param x
     * @param y
     * @return
     */
    public boolean withinBounds(int x, int y) {
        return (x >= 0 && x < nCols) && (y >= 0 && y < nRows);
    }

    public boolean withinBounds(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        return withinBounds(x, y);
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

    public boolean isVisited(int x, int y) {
        return ((mazeGrid[y][x] & (1 << 4)) != 0);
    }

    public boolean isVisited(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return isVisited(x, y);
    }

    public boolean isEnabled(int x, int y) {
        return (mazeGrid[y][x] & (1 << 5)) == 0;
    }

    public boolean isEnabled(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return isEnabled(x, y);
    }

    public void setVisited(int x, int y, boolean value) {
        if (value) {
            mazeGrid[y][x] |= (1 << 4);
        }
        else {
            mazeGrid[y][x] &= ~(1 << 4);
        }
    }

    public void setVisited(Position pos, boolean value) {
        int x = pos.getX();
        int y = pos.getY();
        setVisited(x, y, value);
    }

    public void setAllUnvisited() {
        for (int y = 0; y < nRows; y++) {
            for (int x = 0; x < nCols; x++) {
                setVisited(x, y, false);
            }
        }
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

    public void setAllPaths(int x, int y, boolean isPath) {
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

    public void setAllPaths(Position pos, boolean isPath) {
        int x = pos.getX();
        int y = pos.getY();
        setAllPaths(x, y, isPath);
    }

    public void setCellEnabled(int x, int y, boolean isEnabled) {
        if (isEnabled) {
            mazeGrid[y][x] &= ~(1 << 5);
        }
        else {
            mazeGrid[y][x] |= (1 << 5);
        }
    }

    public void setCellEnabled(Position pos, boolean isEnabled) {
        int x = pos.getX();
        int y = pos.getY();
        setCellEnabled(x, y, isEnabled);
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

    // Image methods
    /**
     * Places an Image in the maze. This operation is only successful if the given Image is well contained within the maze.
     * //@param image Image to place
     */
    public void placeImage(MazeImage image) {
        if (!validateImage(image))  return;

        int x1 = image.getTopLeft().getX();
        int y1 = image.getTopLeft().getY();

        int x2 = image.getBottomRight().getX();
        int y2 = image.getBottomRight().getY();

        // disable all cells in the area of the placed image
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                setCellEnabled(x, y,false);
            }
        }

        images.add(image);

        addedImage(image);
        mazeChanged();
    }


    public void removeImage(MazeImage image) {
        int x1 = image.getTopLeft().getX();
        int y1 = image.getTopLeft().getY();

        int x2 = image.getBottomRight().getX();
        int y2 = image.getBottomRight().getY();

        // enable all cells in the area of the removed image
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                setCellEnabled(x, y,true);
            }
        }

        images.remove(image);

        removedImage(image);
        mazeChanged();
    }

    // Observer design pattern
    public interface MazeListener {
        default void mazeChanged() {}
        default void solveStatusChanged(SolveStatus status) {}
        default void addedImage(MazeImage image) {}
        default void removedImage(MazeImage image) {}
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

    private void addedImage(MazeImage image) {
        for (MazeListener l: listeners) {
            l.addedImage(image);
        }
    }

    private void removedImage(MazeImage image) {
        for (MazeListener l: listeners) {
            l.removedImage(image);
        }
    }


    // private methods

    /**
     * Validates whether the requested image can be legally placed within the maze.
     * @param image An @{@link MazeImage} object
     * @return
     */
    private boolean validateImage(MazeImage image) {
        Position topLeft = image.getTopLeft();
        Position botomRight = image.getBottomRight();

        return (withinBounds(image.getTopLeft())
            && withinBounds(image.getBottomRight()));
    }
}
