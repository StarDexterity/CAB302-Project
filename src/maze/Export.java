package maze;

import maze.data.Maze;
import maze.data.MazeDisplayOptions;
import maze.data.Position;
import maze.helper.MazeDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Export {
    private static int nRows;
    private static int nCols;
    private static int[][] mazeGrid;
    private static boolean solved = false;
    private static boolean grid = false;
    static private String title = "mazeImage";

    static int cellSize = 25;
    static int margin = 25;
    static LinkedList<Position> solution;

    static MazeDisplayOptions displayOptions = new MazeDisplayOptions();

    //placeholder export (will replace with maze display)
    public static void exportMaze(Maze maze, boolean ifSolution, boolean ifGrid, String Imagetype, String fileName) throws IOException {

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


        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileName.isBlank()) {
            fileName = "mazeImage";
        }

        if (Imagetype == "png") {
            // save as PNG
            fileName = fileName + ".png";

            int returnVal = fc.showOpenDialog(null);

            // if file chooser aproved
            String path;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile().getAbsolutePath();
                ImageIO.write(bufferedImage, "png", new File(path+"/" + fileName));
            }


        }

        if (Imagetype == "jpg"){
            // save as PNG
            fileName = fileName + ".jpg";

            int returnVal = fc.showOpenDialog(null);
            String path;
            if (returnVal == JFileChooser.APPROVE_OPTION){
                path = fc.getSelectedFile().getAbsolutePath();
                ImageIO.write(bufferedImage, "jpg", new File(path+"/" + fileName));
            }


        }
    }
}