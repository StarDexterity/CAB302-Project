package tests;

import maze.data.Maze;
import maze.data.MazeImage;
import maze.data.Position;
import maze.enums.GenerationOption;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestInsertImage {
    BufferedImage test = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
    Position topLeft = new Position(2,2);
    Position bottomRight = new Position(3,3);
    URL imageUrl = this.getClass().getResource("TestImage.jpg");
    File file = Paths.get(imageUrl.toURI()).toFile();
    MazeImage m = new MazeImage(topLeft, bottomRight, file);

    public TestInsertImage() throws URISyntaxException {
    }

    @Test
    public void addImageToList(){
        Maze maze = new Maze(5,5, GenerationOption.ALDOUS);
        maze.placeImage(m);
        assertEquals(maze.images.isEmpty(),false);
    }

    @Test
    public void addImageCellEnabled(){
        Maze maze = new Maze(5,5, GenerationOption.ALDOUS);
        maze.placeImage(m);
        assertEquals(maze.isEnabled(topLeft), false);
    }

    @Test
    public void removeImageCellEnabled(){
        Maze maze = new Maze(5,5,GenerationOption.ALDOUS);
        maze.removeImage(m);
        assertEquals(maze.isEnabled(topLeft), true);
    }

    @Test
    public void removeImageFromList(){
        Maze maze = new Maze(5,5, GenerationOption.ALDOUS);
        maze.removeImage(m);
        assertEquals(maze.images.isEmpty(),true);
    }

    @Test
    public void testResize(){
        m.resize(50,50);
        assertEquals(m.getWidth(), 2);
        assertEquals(m.getHeight(),2);
    }
}

