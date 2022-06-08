package maze;

import maze.data.Maze;
import maze.data.Position;

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
    private boolean solved = true;
    private boolean grid = true;
    static private String title;

    static int cellSize = 25;
    static int margin = 25;
    static LinkedList<Position> solution;


    //maze values to export selected maze
    public static void mazeValues(Maze maze) {
        nCols = maze.getCols();
        nRows = maze.getRows();
        mazeGrid = maze.getMazeGrid();
        solution = maze.getSolution();
    }

    //placeholder export (will replace with maze display)
    public static void displayMaze(Maze maze, String Imagetype) throws IOException {

        //get maze values
        mazeValues(maze);

        int width = nCols * cellSize + margin * 2;
        int height = nRows * cellSize + margin * 2;

        // BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D export = bufferedImage.createGraphics();

        export.setColor(Color.white);
        export.fillRect(0, 0, width, height);

        Color mazeColor = new Color(0, 0, 0);
        Color solutionColor = new Color(255, 0, 0);
        Color startColor = new Color(0, 0, 255);
        Color endColor = new Color(0, 255, 0);

        //grid (will need if)
        export.setStroke(new BasicStroke(2));
        export.setColor(new Color(192, 192, 192, 200));

        for (int i = 0; i < nRows + 1; i++) {
            int rowHt = cellSize;
            export.drawLine(0 + margin, (i * rowHt) + margin, (cellSize * nCols) + margin, (i * rowHt) + margin);
        }
        for (int i = 0; i < nCols + 1; i++) {
            int rowWid = cellSize;
            export.drawLine((i * rowWid) + margin, 0 + margin, (i * rowWid) + margin, (cellSize * nRows) + margin);
        }

        export.setColor(mazeColor);

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

        //solution line (will need if)
        int offset = margin + cellSize / 2;
        Path2D path = new Path2D.Float();
        path.moveTo(offset, offset);

        for (Position pos : solution) {
            int x = pos.getX() * cellSize + offset;
            int y = pos.getY() * cellSize + offset;
            path.lineTo(x, y);
        }

        export.setColor(solutionColor);
        export.draw(path);

        //start and end points (will need if)
        export.setColor(startColor);
        export.fillOval(offset - 5, offset - 5, 10, 10);

        export.setColor(endColor);
        int x = offset + (nCols - 1) * cellSize;
        int y = offset + (nRows - 1) * cellSize;
        export.fillOval(x - 5, y - 5, 10, 10);


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