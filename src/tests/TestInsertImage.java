package tests;

import maze.data.Maze;
import maze.data.MazeImage;
import maze.data.Position;
import maze.enums.GenerationOption;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestInsertImage {
    Position topLeft = new Position(2,2);
    Position bottomRight = new Position(3,3);
    URL imageUrl = this.getClass().getResource("TestImage.jpg");
    File file = Paths.get(imageUrl.toURI()).toFile();
    MazeImage m = new MazeImage(topLeft, bottomRight, file);
    Maze maze = new Maze(5,5, GenerationOption.ALDOUS);

    public TestInsertImage() throws URISyntaxException {
    }

    @Test
    public void addImageToList(){
        maze.placeImage(m);
        assertEquals(maze.getImages().isEmpty(),false);
    }

    @Test
    public void addImageCellEnabled(){
        maze.placeImage(m);
        assertEquals(maze.isEnabled(topLeft), false);
    }

    @Test
    public void removeImageCellEnabled(){
        maze.removeImage(m);
        assertEquals(maze.isEnabled(topLeft), true);
    }

    @Test
    public void removeImageFromList(){
        maze.removeImage(m);
        assertEquals(maze.getImages().isEmpty(),true);
    }

    @Test
    public void testResize(){
        m.resize(50,50);
        assertEquals(m.getWidth(), 2);
        assertEquals(m.getHeight(),2);
    }

    @Test
    public void testNotWithinBounds(){
        m.withinBounds(6,6);
        assertNotEquals(m.withinBounds(0,0), true);
    }
}

