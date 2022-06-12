package maze.helper;

import maze.data.*;
import maze.enums.SelectionType;


import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Maze drawer class draws a maze to a buffered image
 */
public class MazeDrawer {

    /**
     * Generates a buffered image with the supplied maze data
     * @param maze The maze generation data (nCols, nRows, Grid)
     * @param displayOptions Stores the configurable display options for drawing a maze
     * @param selection Gets the cell position, cell type and if applicable the image inserted
     * @return
     */
    public static BufferedImage drawMaze(Maze maze, MazeDisplayOptions displayOptions, Selection selection) {

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

        Position selectedCell = selection.selectedCell;
        MazeImage selectedImage = selection.selectedImage;
        SelectionType selectionType = selection.selectionType;

        g.setColor(displayOptions.getBackgroundColour());
        g.fillRect(0, 0, sizeW, sizeH);



        // draws the grid if showGrid grid option is enabled
        if (displayOptions.isGrid()) {
            g.setColor(displayOptions.getGridColour());
            g.setStroke(new BasicStroke(displayOptions.getGridLineThickness()));


            for (int i = 0; i < nRows + 1; i++) {
                int rowHt = cellSize;
                g.drawLine(0 + margin, (i * rowHt) + margin, (cellSize * nCols) + margin, (i * rowHt) + margin);
            }
            for (int i = 0; i < nCols + 1; i++) {
                int rowWid = cellSize;
                g.drawLine((i * rowWid) + margin, 0 + margin, (i * rowWid) + margin, (cellSize * nRows) + margin);
            }
        }

        // draw maze
        g.setColor(displayOptions.getMazeColour());
        g.setStroke(new BasicStroke(displayOptions.getMazeLineThickness()));
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
        if (displayOptions.isSolution()) {
            for (Position pos : solution) {
                int x = pos.getX() * cellSize + offset;
                int y = pos.getY() * cellSize + offset;
                path.lineTo(x, y);
            }

            g.setColor(displayOptions.getSolutionColour());
            g.setStroke(new BasicStroke(displayOptions.getSolutionLineThickness()));
            g.draw(path);
        }


        g.setColor(displayOptions.getStartColour());
        g.fillOval(offset - 5, offset - 5, 10, 10);

        g.setColor(displayOptions.getEndColour());
        int x = offset + (nCols - 1) * cellSize;
        int y = offset + (nRows - 1) * cellSize;
        g.fillOval(x - 5, y - 5, 10, 10);

        // draws selected object
        g.setColor(displayOptions.getSelectedColour());
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

    public static BufferedImage drawMaze(Maze maze, MazeDisplayOptions displayOptions) {
        return drawMaze(maze, displayOptions, new Selection());
    }
}
