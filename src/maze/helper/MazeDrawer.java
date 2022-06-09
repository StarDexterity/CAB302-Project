package maze.helper;

import maze.data.*;
import maze.enums.SelectionType;

import ui.pages.editpage.MazeDisplay;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Maze drawer class draws a maze to a buffered image
 */
public class MazeDrawer {
    public static BufferedImage drawMaze(Maze maze, MazeDisplayOptions displayOptions) {

        // gets important maze data for rendering
        int nCols = maze.getCols();
        int nRows = maze.getRows();
        int[][] mazeGrid = maze.getMazeGrid();
        LinkedList<Position> solution = maze.getSolution();
        int cellSize = displayOptions.getCellSize();
        int margin = displayOptions.getMargin();

        int sizeW = nCols * cellSize + margin * 2;
        int sizeH = nRows * cellSize + margin * 2;

        // BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(sizeW, sizeH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bufferedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        Position selectedCell = displayOptions.getSelectionCell();
        MazeImage selectedImage = displayOptions.getSelectedImage();
        SelectionType selectionType = displayOptions.getSelectedType();

        g.setColor(Color.white);
        g.fillRect(0, 0, sizeW, sizeH);

        // gets colours
        Color solutionLineColor = displayOptions.getSolutionColour();

        // gets if statements
        boolean showGrid = displayOptions.isGrid();
        boolean showSolution = displayOptions.isSolution();

        // draws the grid if showGrid grid option is enabled
        if (showGrid) {
            g.setStroke(new BasicStroke(2));
            g.setColor(new Color(192, 192, 192, 200));

            for (int i = 0; i < nRows + 1; i++) {
                int rowHt = cellSize;
                g.drawLine(0 + margin, (i * rowHt) + margin, (cellSize * nCols) + margin, (i * rowHt) + margin);
            }
            for (int i = 0; i < nCols + 1; i++) {
                int rowWid = cellSize;
                g.drawLine((i * rowWid) + margin, 0 + margin, (i * rowWid) + margin, (cellSize * nRows) + margin);
            }
        }

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.black);

        // draw maze
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {

                int x = margin + c * cellSize;
                int y = margin + r * cellSize;

                if ((mazeGrid[r][c] & 1) == 0) // N
                    g.drawLine(x, y, x + cellSize, y);

                if ((mazeGrid[r][c] & 2) == 0) // S
                    g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);

                if ((mazeGrid[r][c] & 4) == 0) // E
                    g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);

                if ((mazeGrid[r][c] & 8) == 0) // W
                    g.drawLine(x, y, x, y + cellSize);
            }
        }


        // draw images
        for (MazeImage image : maze.getImages()) {
            // get top left coordinate
            Position topLeft = image.getTopLeft();

            // get width and height of image in pixels
            int width = image.getWidth() * cellSize;
            int height = image.getHeight() * cellSize;

            // get x and y position of image top left corner in pixels
            int xPos = (topLeft.getX() * cellSize) + margin;
            int yPos = (topLeft.getY() * cellSize) + margin;

            image.resize(width, height);
            g.drawImage(image.getImage(), xPos, yPos, null);
            g.drawRect(xPos, yPos, width, height);
        }

        // draw pathfinding animation
        int offset = margin + cellSize / 2;

        Path2D path = new Path2D.Float();
        path.moveTo(offset, offset);

        // draws the solution if showSolution is true
        if (showSolution) {
            for (Position pos : solution) {
                int x = pos.getX() * cellSize + offset;
                int y = pos.getY() * cellSize + offset;
                path.lineTo(x, y);
            }
        }


        g.setColor(solutionLineColor);
        g.draw(path);

        g.setColor(Color.blue);
        g.fillOval(offset - 5, offset - 5, 10, 10);

        g.setColor(Color.green);
        int x = offset + (nCols - 1) * cellSize;
        int y = offset + (nRows - 1) * cellSize;
        g.fillOval(x - 5, y - 5, 10, 10);

        // draws selected object
        g.setColor(new Color(0, 128, 128, 128));
        if (selectionType == SelectionType.CELL) {
            g.fillRect(selectedCell.getX() * cellSize + margin,
                    selectedCell.getY() * cellSize + margin,
                    cellSize, cellSize);
        }
        else if (selectionType == SelectionType.IMAGE) {
            g.fillRect(selectedImage.getTopLeft().getX() * cellSize + margin,
                    selectedImage.getTopLeft().getY() * cellSize + margin,
                    selectedImage.getWidth() * cellSize,
                    selectedImage.getHeight() * cellSize);
        }

        return bufferedImage;
    }

//    public static BufferedImage drawMaze(Maze maze) {
//        return drawMaze(maze, new MazeDisplayOptions());
//    }
}
