package maze.data;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class holds the information of an image to be placed within a maze, including its file path, coordinate within the maze, and its dimensions.
 */
public class MazeImage {
    private Position topLeft;
    private Position bottomRight;
    private BufferedImage image;

    // constructor
    public MazeImage(Position topLeft, Position bottomRight, File file) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public BufferedImage getImage() {
        return image;
    }
}
