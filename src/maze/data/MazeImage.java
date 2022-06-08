package maze.data;

import javax.imageio.ImageIO;
import java.awt.*;
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

    public int getWidth() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    public int getHeight() {
        return bottomRight.getY() - topLeft.getY() + 1;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean withinBounds(int x, int y) {
        return (x >= topLeft.getX() && x <= bottomRight.getX())
            && (y >= topLeft.getY() && y <= bottomRight.getY());
    }

    public boolean withinBounds(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return withinBounds(x, y);
    }

    public void resize(int width, int height){
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = newImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        this.image = newImage;
    }
}
