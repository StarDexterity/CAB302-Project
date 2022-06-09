package maze.data;

import maze.enums.SelectionType;

import java.awt.*;

public class MazeDisplayOptions {
    private int cellSize = 25;
    private int margin = 25;
    private int lineThickness = 2;

    private boolean isSolution = false;
    private boolean isGrid = false;

    private SelectionType selectedType;
    private Position selectedCell;
    private MazeImage selectedImage;

    private Color backgroundColour = Color.white;
    private Color mazeColour = Color.black;
    private Color solutionColour = Color.ORANGE;
    private Color gridColour = Color.lightGray;
    private Color startColour = Color.blue;
    private Color endColour = Color.green;


    /**
     * Default constructor
     */
    public MazeDisplayOptions() {

    }

    public SelectionType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(SelectionType selectedType) {
        this.selectedType = selectedType;
    }

    public Position getSelectionCell() {
        return selectedCell;
    }

    public void setSelectionCell(Position selectedCell) {
        this.selectedCell = selectedCell;
    }

    public MazeImage getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(MazeImage selectedImage) {
        this.selectedImage = selectedImage;
    }


    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean solution) {
        isSolution = solution;
    }

    public boolean isGrid() {
        return isGrid;
    }

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(Color backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public Color getMazeColour() {
        return mazeColour;
    }

    public void setMazeColour(Color mazeColour) {
        this.mazeColour = mazeColour;
    }

    public Color getSolutionColour() {
        return solutionColour;
    }

    public void setSolutionColour(Color solutionColour) {
        this.solutionColour = solutionColour;
    }

    public Color getGridColour() {
        return gridColour;
    }

    public void setGridColour(Color gridColour) {
        this.gridColour = gridColour;
    }

    public Color getStartColour() {
        return startColour;
    }

    public void setStartColour(Color startColour) {
        this.startColour = startColour;
    }

    public Color getEndColour() {
        return endColour;
    }

    public void setEndColour(Color endColour) {
        this.endColour = endColour;
    }
}
