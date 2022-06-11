package maze.data;

import maze.enums.Direction;
import maze.enums.GenerationOption;
import maze.enums.SolveStatus;
import maze.helper.MazeGenerator;
import maze.interfaces.MazeListener;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 */
public class Maze {

    /**
     * The internal data representation of the maze. The maze is represented as a 2d array of integers, where each integer represents a cell of the maze.
     * The first 4 bits of the integer are used to store whether the cell is connected to the neighboring cell in directions (N, E, S, W),
     * stored respectively in bits (0, 1, 2, 3).
     * The 5th is used by the solving algorithm to determine whether the cell has been visited
     * The 6th is used to determine whether the cell is within the bounds of an image, to exclude it from solving
     */
    private final int[][] mazeGrid;

    /**
     * Number of columns in the maze
     */
    private final int nCols;

    /**
     * Number of rows in the maze
     */
    private final int nRows;

    /**
     * The solve status of the maze (is the maze solved or not)
     */
    private SolveStatus solveStatus;

    /**
     * The starting position of the maze, for display and solving purposes
     */
    private final Position startPosition;

    /**
     * The end position of the maze, for display and solving purposes
     */
    private final Position endPosition;

    /**
     * A sequence of cells a user must travel to solve the maze
     */
    private LinkedList<Position> solution;

    /**
     * Metadata of the maze
     */
    public MazeData mazeData;

    /**
     * Collection of images displayed in the maze
     */
    private ArrayList<MazeImage> images;

    // constructors
    /**
     * Constructs a maze, with bare essentials
     */
    public Maze() {
        this(4, 4, false);
    }

    /**
     * Constructs a maze object, using the @{@link GenerationOption} enum to choose the algorithm used to generate the maze. Used when creating a new maze.
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
     * Legacy constructor, generates a maze object automatically or manually (DFS or blank generation)
     * @param nCols Number of columns
     * @param nRows Number of rows
     * @param automatic Should maze be generated automatically
     */
    public Maze(int nCols, int nRows, boolean automatic) {
        this(nCols, nRows, (automatic) ? GenerationOption.DFS : GenerationOption.EMPTY);
    }

    /**
     * Constructs a new maze object with all the fields. Used when constructing maze object from the database.
     * @param nCols
     * @param nRows
     * @param mazeGrid
     * @param mazeData
     */
    public Maze(int nCols, int nRows, int[][] mazeGrid, MazeData mazeData) {
        this(nCols, nRows, false);
        this.setMazeGrid(mazeGrid);
        this.mazeData = mazeData;
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

    /**
     * Is the given cartesian coordinate within the bounds of the maze.
     * @param pos
     * @return
     */
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
     * Predicate function checks whether a cell at the given (x, y) coordinate in the grid can pass in the given direction
     * @param x The X component of the coordinate
     * @param y The Y component of the coordinate
     * @param dir The direction bit to check
     * @return
     */
    public boolean isPath(int x, int y, Direction dir) {
        return ((mazeGrid[y][x] & dir.bit) != 0);
    }

    /**
     * Predicate function checks whether a cell at the given (x, y) coordinate in the grid can pass in the given direction
     * @param pos The position of the cell within the grid
     * @param dir The direction bit to check
     * @return
     */
    public boolean isPath(Position pos, Direction dir) {
        int x = pos.getX();
        int y = pos.getY();
        return isPath(x, y, dir);
    }

    /**
     * Is the cell at grid coordinate (x, y) in the mazeGrid, visited by the BFS solver algorithm.
     * This information is used only by the solving algorithm and wiped before and after the solving algorithm
     * @param x The X component of the coordinate
     * @param y The Y component of the coordinate
     * @return
     */
    public boolean isVisited(int x, int y) {
        return ((mazeGrid[y][x] & (1 << 4)) != 0);
    }

    /**
     * Is the cell at grid coordinate (x, y) in the mazeGrid, visited by the BFS solver algorithm.
     * This information is used only by the solving algorithm and wiped before and after the solving algorithm
     * @param pos The position of the cell within the mazeGrid
     * @return
     */
    public boolean isVisited(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return isVisited(x, y);
    }

    /**
     * Is the enabled flag of the cell at grid position (x, y) in the mazeGrid on or off
     * @param x
     * @param y
     * @return
     */
    public boolean isEnabled(int x, int y) {
        return (mazeGrid[y][x] & (1 << 5)) == 0;
    }

    /**
     * Is the enabled flag of the cell at grid coordinate (x, y) in the mazeGrid on or off
     * @param pos
     * @return
     */
    public boolean isEnabled(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return isEnabled(x, y);
    }

    /**
     * Set or clear the visited bit of the cell at grid position (x, y) based on given value
     * @param x The X component of the grid coordinate within mazeGrid
     * @param y The Y component of the grid coordinate within mazeGrid
     * @param value Whether to set or clear the bit
     */
    public void setVisited(int x, int y, boolean value) {
        if (value) {
            mazeGrid[y][x] |= (1 << 4);
        }
        else {
            mazeGrid[y][x] &= ~(1 << 4);
        }
    }

    /**
     * Set or clear the visited bit of the cell at grid position (x, y) based on given value
     * @param pos The position of the cell within the mazeGrid
     * @param value Whether to set of clear the bit
     */
    public void setVisited(Position pos, boolean value) {
        int x = pos.getX();
        int y = pos.getY();
        setVisited(x, y, value);
    }

    /**
     * Clear the visited flag of each cell in the mazeGrid
     */
    public void setAllUnvisited() {
        for (int y = 0; y < nRows; y++) {
            for (int x = 0; x < nCols; x++) {
                setVisited(x, y, false);
            }
        }
    }

    /**
     * If isPath is true, sets a path in the given direction between the cell at grid position (x, y) and the neighboring cell in the given direction,
     * else clear the path.
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

    /**
     * If isPath is true, sets a path in the given direction between the cell at grid position (x, y) and the neighboring cell in the given direction,
     *      * else clear the path.
     * @param pos The position of the cell within the mazeGrid
     * @param dir The direction of the neighboring cell to connect/disconnect this cell to
     * @param isPath Whether to connect of disconnect the cells
     */
    public void setPath(Position pos, Direction dir, boolean isPath) {
        int x = pos.getX();
        int y = pos.getY();
        setPath(x, y, dir, isPath);
    }

    /**
     * set or clears all the directional bits within the integer at grid position (x, y) within the mazeGrid based off isPath,
     * and all adjacent neighboring cells opposite bits
     * @param x X component of coordinate
     * @param y Y component of coordinate
     * @param isPath if true, set all the directional bits, else clear
     */
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

    /**
     * set or clears all the directional bits within the integer at grid position (x, y) within the mazeGrid based off isPath,
     * and all adjacent neighboring cells opposite bits
     else clear all paths
     * @param pos The position of the cell within the mazeGrid
     * @param isPath If true, set all directional bits, else clear all directional bits
     */
    public void setAllPaths(Position pos, boolean isPath) {
        int x = pos.getX();
        int y = pos.getY();
        setAllPaths(x, y, isPath);
    }

    /**
     * Set or clears the enabled bit within the integer at grid position (x, y) within the mazeGrid based off isEnabled.
     * @param x The X component of the grid position
     * @param y The Y component of the grid position
     * @param isEnabled if true, set enabled bit, else clear bit
     */
    public void setCellEnabled(int x, int y, boolean isEnabled) {
        if (isEnabled) {
            mazeGrid[y][x] &= ~(1 << 5);
        }
        else {
            mazeGrid[y][x] |= (1 << 5);
        }
    }

    /**
     * Set or clears the enabled bit within the integer at grid position (x, y) within the mazeGrid based off isEnabled.
     * @param pos The position of the cell within the mazeGrid
     * @param isEnabled if true, set bit, else clear bit
     */
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
     * //@param The given image to place
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


    /**
     * Removes an image object from the maze.
     * @param image The given image to remove
     */
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
