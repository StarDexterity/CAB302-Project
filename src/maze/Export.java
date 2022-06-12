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

/**
 * Handles the export feature of the program and will export the selected mazes to JPG or PNG
 */

public class Export {

    private static String path;

    static MazeDisplayOptions displayOptions = new MazeDisplayOptions();

    /**
     * Draws the maze being exported to a users device
     * @param maze Maze data
     * @param pathCheck If file directory has been selected by the user
     * @param ifSolution If user selected for the solution to be displayed
     * @param ifGrid If user selected for the grid to be displayed
     * @param Imagetype The image type selected by the user (JPG or PNG)
     * @param fileName The name written for it when exporting
     * @param colour The colour for the solution line

     */
    public static void exportMaze(Maze maze, boolean pathCheck, boolean ifSolution, boolean ifGrid, String Imagetype, String fileName, Color colour) throws IOException {

        displayOptions.setSolution(ifSolution);
        displayOptions.setGrid(ifGrid);
        displayOptions.setSolutionColour(colour);

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

            if (pathCheck == false) {
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    path = fc.getSelectedFile().getAbsolutePath();
                    ImageIO.write(bufferedImage, "png", new File(path+"/" + fileName));
                }
            }
            else {
                ImageIO.write(bufferedImage, "png", new File(path+"/" + fileName));
            }

        }

        if (Imagetype == "jpg"){
            // save as PNG
            fileName = fileName + ".jpg";

            if ((pathCheck == false)){
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    path = fc.getSelectedFile().getAbsolutePath();
                    ImageIO.write(bufferedImage, "jpg", new File(path+"/" + fileName));
                }
            }
            else {
                ImageIO.write(bufferedImage, "jpg", new File(path+"/" + fileName));
            }


        }
    }
}