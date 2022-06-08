package maze.data;

import maze.enums.SelectionType;

public class Selection {
    public Position selectedCell;
    public MazeImage selectedImage;
    public SelectionType selectionType;

    /**
     * Sets the enum variables
     * @param selectedCell The currently selected cell
     * @param selectedImage The currently selected image
     * @param selectionType Whether nothing, an image, or a cell is selcted
     */
    public Selection(Position selectedCell, MazeImage selectedImage, SelectionType selectionType) {
        this.selectedCell = selectedCell;
        this.selectedImage = selectedImage;
        this.selectionType = selectionType;
    }
}
