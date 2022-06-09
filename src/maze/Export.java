package maze;

import maze.data.Maze;
import maze.data.MazeDisplayOptions;
import maze.data.Position;
import maze.helper.MazeDrawer;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Export {
    private static int nRows;
    private static int nCols;
    private static int[][] mazeGrid;
    private static boolean solved = false;
    private static boolean grid = false;
    static private String title;

    static int cellSize = 25;
    static int margin = 25;
    static LinkedList<Position> solution;

    static MazeDisplayOptions displayOptions = new MazeDisplayOptions();

    //maze values to export selected maze
    public static void mazeValues(Maze maze) {
        nCols = maze.getCols();
        nRows = maze.getRows();
        mazeGrid = maze.getMazeGrid();
        solution = maze.getSolution();
    }

    //placeholder export (will replace with maze display)
    public static void displayMaze(Maze maze, boolean ifSolution, boolean ifGrid, String Imagetype) throws IOException {

        //get maze values
        mazeValues(maze);

        displayOptions.setSolution(ifSolution);
        displayOptions.setGrid(ifGrid);



        // BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = MazeDrawer.drawMaze(maze, displayOptions);

        Graphics2D export = bufferedImage.createGraphics();

        export.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        export.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        export.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        export.drawImage(bufferedImage, null, 0, 0);




        // deletes this graphics
        export.dispose();


        if (Imagetype == "png"){
            // save as PNG
            String fileName = title + ".png";
            File file = new File("mazeImage.png");
            ImageIO.write(bufferedImage, "png", file);
        }

        if (Imagetype == "jpg"){
            // save as PNG
            String fileName = title + ".jpg";
            File file = new File("mazeImage.jpg");
            ImageIO.write(bufferedImage, "jpg", file);
        }

        // save as PDF (may not implement) (needs to be implemented differently to other exports)

    }
}