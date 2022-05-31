package maze;

/**
 * This child class is a Default.Maze class with images for the start and end positions of the maze. It extends @{@link Maze}
 */
public class PictureMaze extends Maze {
    private MazeImage startImage;
    private MazeImage endImage;

    // constructor


    public PictureMaze(int nCols, int nRows, boolean automatic, MazeImage startImage, MazeImage endImage) {
        super(nRows, nCols, automatic);
        this.startImage = startImage;
        this.endImage = endImage;
    }

    // getters and setters
    public MazeImage getStartImage() {
        return startImage;
    }

    public void setStartImage(MazeImage startImage) {
        this.startImage = startImage;
    }

    public MazeImage getEndImage() {
        return endImage;
    }

    public void setEndImage(MazeImage endImage) {
        this.endImage = endImage;
    }
}
