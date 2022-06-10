package ui.pages.editpage;

import maze.data.Maze;
import maze.data.MazeImage;
import maze.data.Position;
import maze.data.Selection;
import maze.enums.SelectionType;
import maze.interfaces.MazeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This maze generator and solver was taken from:
 * https://rosettacode.org/wiki/Maze_solving#Animated_version
 * This is currently more of a placeholder for the algorithm
 * for the purposes of displaying the prototype properly.
 */

public class MazeDisplay extends JPanel implements Scrollable {

    // These two values are for rendering
    final int cellSize = 25;
    final int margin = 25;

    private final MazeDisplay ref = this;

    /**
     * Stores a reference to the current maze object being rendered
     */
    private Maze maze;

    private boolean showSolution;
    private boolean showGrid;

    public static boolean addImage;

    // color settings
    // TODO: Hook these up below
    public Color solutionLineColor = Color.ORANGE;
    private Color gridColor = new Color(192, 192, 192, 200);
    private Color mazeColor = Color.BLACK;
    private Color background = Color.WHITE;

    // selected cell coordinates
    // TODO: convert this into a Selection object
    private Position selectedCell;
    private MazeImage selectedImage;
    private SelectionType selectionType;


    public void changeSolutionColor(Color color){
        solutionLineColor = color;
        repaint();
        revalidate();
    }

    public MazeDisplay() {
        // graphics code
        setBackground(Color.white);

        // set a placeholder maze
        this.maze = new Maze();

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
                    // set to defaults
                    Position oldSelectedCell = selectedCell;
                    MazeImage oldSelectedImage = selectedImage;
                    selectedCell = null;
                    selectedImage = null;
                    selectionType = SelectionType.NONE;

                    // get mouse position in grid coordinates
                    int x = (e.getX() - margin) / cellSize;
                    int y = (e.getY() - margin) / cellSize;
                    Position newSelectedCell = new Position(x, y);

                    // is the cell position within maze bounds
                    if (maze.withinBounds(newSelectedCell)) {
                        boolean withinImage = false;

                        for (MazeImage image : maze.getImages()) {
                            // image is selected
                            if (image.withinBounds(newSelectedCell)) {
                                withinImage = true;
                                // only make a selection if a different image is selected
                                if (!image.equals(oldSelectedImage)) {
                                    selectionType = SelectionType.IMAGE;
                                    selectedImage = image;
                                }
                            }
                        }
                        if (!withinImage) {
                            // only make a selection if a different cell is selected
                            if (!newSelectedCell.equals(oldSelectedCell)) {
                                selectionType = SelectionType.CELL;
                                selectedCell = newSelectedCell;
                            }
                        }

                    }
                    selectionChanged();

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
        this.maze = maze;
        deselect();

        // when maze is altered in any way this component is repainted
        maze.addListener(new MazeListener() {
            @Override
            public void mazeChanged() {
                repaint();
                revalidate();
            }

            @Override
            public void removedImage(MazeImage image) {
                deselect();
            }

            @Override
            public void addedImage(MazeImage image) {
                selectionType = SelectionType.IMAGE;
                selectedImage = image;
                selectionChanged();
            }
        });


        repaint();
        revalidate();
    }

    public void deselect() {
        selectedCell = null;
        selectedImage = null;
        selectionType = SelectionType.NONE;
        selectionChanged();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(maze.getCols() * cellSize + margin * 2, maze.getRows() * cellSize + margin * 2);
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

        // gets important maze data for rendering
        int nCols = maze.getCols();
        int nRows = maze.getRows();
        int[][] mazeGrid = maze.getMazeGrid();
        LinkedList<Position> solution = maze.getSolution();


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
        void selectedCellChanged(Selection cce);
    }




    private ArrayList<MazeDisplayListener> listeners = new ArrayList<MazeDisplayListener>();

    public void addListener(MazeDisplayListener ml) { listeners.add(ml); }

    public void removeListener(MazeDisplayListener ml) { listeners.remove(ml); }

    private void selectionChanged() {
        repaint();
        revalidate();

        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).selectedCellChanged(new Selection(selectedCell, selectedImage, selectionType));
        }
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