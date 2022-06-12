package maze.data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

/**
 * This class holds the information of an image to be placed within a maze, including its file path, coordinate within the maze, and its dimensions.
 */
public class MazeImage {
    private int id;
    private Position topLeft;
    private Position bottomRight;
    private BufferedImage image;

    // constructor

    /**
     * Constructor for the MazeImage class
     * @param topLeft The top left position of the image
     * @param bottomRight The bottom right position of the image
     * @param file The image file to be inserted into the maze
     */
    public MazeImage(Position topLeft, Position bottomRight, File file) {
        // Random seed for id
        this.id = Instant.now().getNano();
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor to create MazeImages from database
     * @param topLeft The top left position of the image
     * @param bottomRight The bottom right position of the image
     * @param imageData The data of the image to be inserted into the maze
     */
    public MazeImage(int id, Position topLeft, Position bottomRight, ByteArrayInputStream imageData) {
        this.id = id;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;

        try {
            image = ImageIO.read(imageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
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

    /**
     * Checking if the selected cell is within the bounds of the Maze
     * @param x The x co-ordinate of the selected cell
     * @param y The y co-ordinate of the selected cell
     * @return a boolean value stating whether or not the selected area is within the bounds of the maze
     */
    public boolean withinBounds(int x, int y) {
        return (x >= topLeft.getX() && x <= bottomRight.getX())
            && (y >= topLeft.getY() && y <= bottomRight.getY());
    }

    /**
     * Checking if the selected cell is within the bounds of the Maze
     * @param pos The Position of the selected cell
     * @return a boolean value stating whether or not the selected area is within the bounds of the maze
     */
    public boolean withinBounds(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return withinBounds(x, y);
    }

    /**
     * Takes an image and resizes it to the specified width and height.
     * @param width Wanted new width of the image
     * @param height Wanted new height of the image
     */
    public void resize(int width, int height){
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = newImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        this.image = newImage;
    }
}
