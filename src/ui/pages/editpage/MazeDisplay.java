package ui.pages.editpage;

import maze.data.*;
import maze.enums.SelectionType;
import maze.helper.MazeDrawer;
import maze.interfaces.MazeListener;
import ui.pages.editpage.options.image.InsertImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
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
     * Stores a reference to the current maze object being rendered
     */
    private Maze maze;

    private boolean showSolution;
    private boolean showGrid;

    public static boolean addImage;

    // color settings
    // TODO: Hook these up below
    public Color solutionLineColor = Color.ORANGE;

    // selected cell coordinates
    Selection selection = new Selection();

    MazeDisplayOptions displayOptions = new MazeDisplayOptions();


    public void changeSolutionColor(Color color){
        displayOptions.setSolutionColour(color);
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
                    Position oldSelectedCell = selection.selectedCell;
                    MazeImage oldSelectedImage = selection.selectedImage;
                    selection.selectedCell = null;
                    selection.selectedImage = null;
                    selection.selectionType = SelectionType.NONE;

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
                                    selection.selectionType = SelectionType.IMAGE;
                                    selection.selectedImage = image;
                                }
                            }
                        }
                        if (!withinImage) {
                            // only make a selection if a different cell is selected
                            if (!newSelectedCell.equals(oldSelectedCell)) {
                                selection.selectionType = SelectionType.CELL;
                                selection.selectedCell = newSelectedCell;
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
        displayOptions.setSolution(showSolution);
        repaint();
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
        displayOptions.setGrid(showGrid);
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
                selection.selectionType = SelectionType.IMAGE;
                selection.selectedImage = image;
                selectionChanged();
            }
        });


        repaint();
        revalidate();
    }

    public void deselect() {
        selection.selectedCell = null;
        selection.selectedImage = null;
        selection.selectionType = SelectionType.NONE;
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
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        BufferedImage bufferedImage = MazeDrawer.drawMaze(maze, displayOptions, selection);
        g.drawImage(bufferedImage, null, 0, 0);


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
            listeners.get(i).selectedCellChanged(new Selection(selection.selectedCell, selection.selectedImage, selection.selectionType));
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