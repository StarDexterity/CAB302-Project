package maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;

/**
 * This child class is a Default.Maze class with images for the start and end positions of the maze. It extends @{@link Maze}
 */
public class PictureMaze extends Maze {
    private MazeImage startImage;
    private MazeImage endImage;

    // constructor


    public PictureMaze(int id, String title, String author, String description, int nRows, int nCols, Date creationDate, Date lastEditDate, ArrayList<MazeImage> logos, MazeImage startImage, MazeImage endImage) {
        super(id, title, author, description, nRows, nCols, creationDate, lastEditDate, logos);
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
