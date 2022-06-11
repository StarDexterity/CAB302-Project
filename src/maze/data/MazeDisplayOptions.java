package maze.data;

import java.awt.*;

/**
 * Stores all the configurable display options for drawing a maze
 */
public class MazeDisplayOptions {
    // size settings
    private int cellSize = 25;
    private int margin = 25;

    // thickness settings
    private int mazeLineThickness = 2;
    private int solutionLineThickness = 3;
    private int gridLineThickness = 2;

    // display options
    private boolean isSolution = false;
    private boolean isGrid = false;

    // Colour settings
    private Color backgroundColour = Color.white;
    private Color mazeColour = Color.black;
    private Color solutionColour = Color.ORANGE;
    private Color gridColour = Color.lightGray;
    private Color startColour = Color.blue;
    private Color endColour = Color.green;
    private Color selectedColour = new Color(0, 128, 128, 128);


    /**
     * Default constructor
     */
    public MazeDisplayOptions() {

    }

    // getters
    public int getCellSize() {
        return cellSize;
    }

    public int getMargin() {
        return margin;
    }

    public int getMazeLineThickness() {
        return mazeLineThickness;
    }

    public int getSolutionLineThickness() {
        return solutionLineThickness;
    }

    public int getGridLineThickness() {
        return gridLineThickness;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public boolean isGrid() {
        return isGrid;
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    public Color getMazeColour() {
        return mazeColour;
    }

    public Color getSolutionColour() {
        return solutionColour;
    }

    public Color getGridColour() {
        return gridColour;
    }

    public Color getStartColour() {
        return startColour;
    }

    public Color getEndColour() {
        return endColour;
    }

    public Color getSelectedColour() {
        return selectedColour;
    }

    // setters
    public void setSolution(boolean solution) {
        isSolution = solution;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    public void setSolutionColour(Color solutionColour) {
        this.solutionColour = solutionColour;
    }
}
