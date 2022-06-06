package maze;

import maze.data.Maze;
import maze.data.Position;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Export {
    private static int nRows;
    private static int nCols;
    private static int[][] mazeGrid;

    static int cellSize = 25;
    static int margin = 25;

    //maze values to export selected maze
    public static void mazeValues(Maze maze){
        nCols = maze.getCols();
        nRows = maze.getRows();
        mazeGrid = maze.getMazeGrid();

        //LinkedList<Position> solution;

        //boolean solved = true;
        //boolean grid = true;

    }

    //placeholder export (will replace with maze display)
    public static void displayMaze(Maze maze) throws IOException {

        //get maze values
        mazeValues(maze);

        //placeholders (this should maybe change depending on the maze???)
        int width = nCols * cellSize + margin * 2;
        int height = nRows * cellSize + margin * 2;

        // BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D export = bufferedImage.createGraphics();


        export.setColor(Color.white);
        export.fillRect(0, 0, width, height);

        Color gridColor = new Color(0, 0, 0);
        Color solutionColor = new Color(255, 0, 0);
        Color startColor = new Color(0, 0, 255);
        Color endColor = new Color(0, 255, 0);


        export.setColor(gridColor);

        // draw maze
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {

                int x = margin + c * cellSize;
                int y = margin + r * cellSize;

                if ((mazeGrid[r][c] & 1) == 0) // N
                    export.drawLine(x, y, x + cellSize, y);

                if ((mazeGrid[r][c] & 2) == 0) // S
                    export.drawLine(x, y + cellSize, x + cellSize, y + cellSize);

                if ((mazeGrid[r][c] & 4) == 0) // E
                    export.drawLine(x + cellSize, y, x + cellSize, y + cellSize);

                if ((mazeGrid[r][c] & 8) == 0) // W
                    export.drawLine(x, y, x, y + cellSize);
            }
        }

        // deletes this graphics
        export.dispose();

        //could make this a dropdown as user submits???

        // save as PNG
        File file = new File("mazeImage.png");
        ImageIO.write(bufferedImage, "png", file);

        // save as JPG
        file = new File("mazeImage.jpg");
        ImageIO.write(bufferedImage, "jpg", file);
    }
}