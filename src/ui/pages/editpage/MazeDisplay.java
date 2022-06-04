package ui.pages.editpage;

import maze.data.Maze;
import maze.data.Position;
import ui.pages.editpage.options.cell.CellDisplay;
import ui.pages.editpage.options.image.InsertImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This maze generator and solver was taken from:
 * https://rosettacode.org/wiki/Maze_solving#Animated_version
 * This is currently more of a placeholder for the algorithm
 * for the purposes of displaying the prototype properly.
 */

public class MazeDisplay extends JPanel implements Scrollable {
    /**
     * Indicates the direction of one or more neighboring vertices a vertex connects with (shares an edge)
     * Can be used like bits aka
     *          (If a vertex has both South and East neighbors this can be represented with a bit value of 6)
     */


    /**
     * Number of columns in the maze
     */
    private int nCols;
    /**
     * Number of rows in the maze
     */
    private int nRows;

    // These two values are for rendering
    final int cellSize = 25;
    final int margin = 25;

    private final MazeDisplay ref = this;

    /**
     * The internal representation of the maze, represented as a matrix of vertices connected by the direction enumerable
     */
    private int[][] mazeGrid;

    /**
     * Stores the solution to the maze. This is empty until solve is called.
     */
    LinkedList<Position> solution;

    private boolean showSolution;
    private boolean showGrid;

    public boolean addImage;

    // color settings
    // TODO: Hook these up below
    public Color solutionLineColor = Color.ORANGE;
    private Color gridColor = new Color(192, 192, 192, 200);
    private Color mazeColor = Color.BLACK;
    private Color background = Color.WHITE;

    // selected cell coordinates
    private Position selectedCell;

    private boolean isCellSelected = false;

    public void changeSolutionColor(Color color){
        solutionLineColor = color;
        repaint();
        revalidate();
    }

    public MazeDisplay() {
        // graphics code
        setBackground(Color.white);



        // adapted from https://stackoverflow.com/questions/31171502/scroll-jscrollpane-by-dragging-mouse-java-swing
        MouseAdapter ma = new MouseAdapter() {

            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = null;
                if (e.getButton() == MouseEvent.BUTTON3) {
                    origin = new Point(e.getPoint());
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }

                // below code gets selected cell if any
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();

                    Position oldSelectedCell = selectedCell;
                    selectedCell = null;
                    isCellSelected = false;

                    if ((x > margin && x < margin + cellSize * nCols) && (y > margin && y < margin + cellSize * nRows)) {
                        int cellX = (int)Math.round((x - margin) / cellSize);
                        int cellY = (int)Math.round((y - margin) / cellSize);
                        Position newSelectedCell = new Position(cellX, cellY);

                        if (!newSelectedCell.equals(oldSelectedCell)) {
                            selectedCell = newSelectedCell;
                            isCellSelected = true;
                        }
                    }

                    selectedCellChanged();

                    repaint();
                    revalidate();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, ref);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        scrollRectToVisible(view);
                    }
                }
            }

        };

        addMouseListener(ma);

        addMouseMotionListener(ma);
    }

    public boolean isShowSolution() {
        return showSolution;
    }

    public boolean isShowGrid() {
        return showGrid;
    }


    public void setShowSolution(boolean showSolution) {
        this.showSolution = showSolution;
        repaint();
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
        repaint();
    }
    public void addImage (boolean addImage){
        this.addImage = addImage;
        repaint();
    }

    public void setMaze(Maze maze) {
        solution = maze.getSolution();

        mazeGrid = maze.getMazeGrid();
        nCols = maze.getCols();
        nRows = maze.getRows();

        // when maze is altered in any way this component is repainted
        maze.addListener(new Maze.MazeListener() {
            @Override
            public void mazeChanged() {
                repaint();
                revalidate();
            }
        });


        repaint();
        revalidate();
    }

    public void deselect() {
        selectedCell = null;
        isCellSelected = false;
        selectedCellChanged();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(nCols * cellSize + margin * 2, nRows * cellSize + margin * 2);
    }

    /**
     * Draws the maze to the screen
     *
     * @param gg
     */
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


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

        if (addImage){
            final int xCoord = InsertImage.imageCell.getX();
            final int yCoord = InsertImage.imageCell.getY();
            g.drawImage(InsertImage.newImg, (xCoord*cellSize)+margin, (yCoord*cellSize)+margin, null);
            System.out.println(InsertImage.newImg);
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

        // draws selected cell if any
        if (isCellSelected) {
            g.setColor(new Color(0, 128, 128, 128));
            g.fillRect(selectedCell.getX() * cellSize + margin, selectedCell.getY() * cellSize + margin, cellSize, cellSize);
        }

        g.dispose();
    }


    void animate() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }

    // Observer design pattern
    public interface MazeDisplayListener {
        void selectedCellChanged(CellChangeEvent cce);
    }

    public class CellChangeEvent {
        public Position selectedCell;
        public boolean isCellSelected;

        public CellChangeEvent(Position selectedCell, boolean isCellSelected) {
            this.isCellSelected = isCellSelected;
            this.selectedCell = selectedCell;
        }
    }

    private ArrayList<MazeDisplayListener> listeners = new ArrayList<MazeDisplayListener>();

    public void addListener(MazeDisplayListener ml) {
        listeners.add(ml);
    }

    private void selectedCellChanged() {
        for (MazeDisplayListener l : listeners) {
            l.selectedCellChanged(new CellChangeEvent(
                    selectedCell,
                    isCellSelected
            ));
        }
        repaint();
        revalidate();
    }


    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 25;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 25;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}