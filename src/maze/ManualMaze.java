package maze;

import java.util.LinkedList;

public class ManualMaze extends Maze{
    public ManualMaze(int nCols, int nRows) {
        super(nCols, nRows);

        this.nCols = nCols;
        this.nRows = nRows;

        // initialize the internal maze data structure
        mazeGrid = new int[nRows][nCols];

        solution = new LinkedList<>();

        MazeGenerator.generateMaze(this,0,0);
    }
}
