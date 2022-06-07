package maze.data;

import maze.enums.SelectionType;

public class Selection {
    public Position selectedCell;
    public MazeImage selectedImage;
    public SelectionType selectionType;

    public Selection(Position selectedCell, MazeImage selectedImage, SelectionType selectionType) {
        this.selectedCell = selectedCell;
        this.selectedImage = selectedImage;
        this.selectionType = selectionType;
    }
}
