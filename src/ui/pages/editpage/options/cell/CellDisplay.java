package ui.pages.editpage.options.cell;

import maze.data.Maze;
import maze.data.Position;
import maze.enums.Direction;
import maze.interfaces.MazeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CellDisplay extends JComponent {

    /**
     * Margin between the border of the display and the walls
     */
    private final int margin = 20;

    /**
     * distance away from (either in the x or y coordinate) from the perpendicular wall,
     * without this the walls would be touching
     */
    private final int gap = 12;

    /**
     * The length of the wall (long side)
     */
    private final int wallLength = 100;

    /**
     * The thickness of the wall (short side)
     */
    private final int wallThickness = 8;

    /**
     * Padding of the interaction rectangle
     */
    private final int hoverPadding = 10;

    private Maze maze;
    public Position selectedCell;

    // rectangle boxes of the four walls, helps for painting and checking for mouse events
    private final Rectangle northWall;
    private final Rectangle westWall;
    private final Rectangle eastWall;
    private final Rectangle southWall;

    // color settings
    private final Color disabledColour = new Color(192, 192, 192, 200);
    private final Color enabledColour = Color.BLACK;

    public CellDisplay() {
        super();

        // top and bottom wall rectangle definitions
        northWall = new Rectangle(margin + wallThickness + gap, margin, wallLength, wallThickness);

        southWall = new Rectangle(margin + wallThickness + gap,
                margin + gap * 2 + wallThickness + wallLength,
                wallLength, wallThickness);

        // left and right wall rectangle definitions
        westWall = new Rectangle(margin, margin + gap + wallThickness, wallThickness, wallLength);

        eastWall = new Rectangle(margin + gap * 2 + wallThickness + wallLength,
                margin + gap + wallThickness,
                wallThickness, wallLength);

        // set mouse events
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Point p = e.getPoint();

                if (contains(northWall, p, hoverPadding)) {
                    togglePath(Direction.N);
                } else if (contains(westWall, p, hoverPadding)) {
                    togglePath(Direction.W);
                } else if (contains(southWall, p, hoverPadding)) {
                    togglePath(Direction.S);
                } else if (contains(eastWall, p, hoverPadding)) {
                    togglePath(Direction.E);
                }

                repaint();
                revalidate();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                // get mouse position
                Point p = e.getPoint();

                if (contains(northWall, p, hoverPadding)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else if (contains(westWall, p, hoverPadding)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else if (contains(southWall, p, hoverPadding)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else if (contains(eastWall, p, hoverPadding)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }

                repaint();
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);

        addKeyBindings();
    }

    private void addKeyBindings() {
        // key bindings
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // single actions
        inputMap.put(KeyStroke.getKeyStroke('w'),"ToggleNorthWall");
        inputMap.put(KeyStroke.getKeyStroke('s'),"ToggleSouthWall");
        inputMap.put(KeyStroke.getKeyStroke('a'),"ToggleWestWall");
        inputMap.put(KeyStroke.getKeyStroke('d'),"ToggleEastWall");
        // bulk actions
        inputMap.put(KeyStroke.getKeyStroke('c'),"ClearAllWalls");
        inputMap.put(KeyStroke.getKeyStroke('f'),"FillAllWalls");

        actionMap.put("ToggleNorthWall",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        togglePath(Direction.N);
                    }
                }
        );

        actionMap.put("ToggleSouthWall",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        togglePath(Direction.S);
                    }
                }
        );

        actionMap.put("ToggleWestWall",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        togglePath(Direction.W);
                    }
                }
        );

        actionMap.put("ToggleEastWall",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        togglePath(Direction.E);
                    }
                }
        );

        actionMap.put("ClearAllWalls",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setAll(true);
                    }
                }
        );

        actionMap.put("FillAllWalls",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setAll(false);
                    }
                }
        );

    }

   public void setPath(Direction dir, boolean isPath) {
        if (selectedCell == null) return;
        maze.setPath(selectedCell, dir, isPath);
   }

   public void togglePath(Direction dir) {
        if (selectedCell == null) return;
        maze.setPath(selectedCell, dir, !maze.isPath(selectedCell, dir));
   }


    public void setAll(boolean isPath) {
        if (selectedCell == null) return;
        maze.setAllPaths(selectedCell, isPath);
    }


    @Override
    public Dimension getPreferredSize() {
        int calcSize = margin * 2 + gap * 2 + wallThickness * 2 + wallLength;
        return new Dimension(calcSize, calcSize);
    }

    /**
     * Draws the maze to the screen
     *
     * @param gg Used to paint
     */
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getBackground());

        // Draws each wall using maze
        g.setColor(getWallColour(Direction.N));
        g.fill(northWall);

        g.setColor(getWallColour(Direction.S));
        g.fill(southWall);

        g.setColor(getWallColour(Direction.W));
        g.fill(westWall);

        g.setColor(getWallColour(Direction.E));
        g.fill(eastWall);
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        maze.addListener(new MazeListener() {
            @Override
            public void mazeChanged() {
                repaint();
                revalidate();
            }
        });
    }

    public void setSelectedCell(Position selectedCell) {
        this.selectedCell = selectedCell;
        repaint();
        revalidate();
    }

    /**
     * Helper function checks if a point is contained within a rectangle + padding,
     *
     * @param r Rectangle to check in
     * @param p Point to check for
     * @param pad additional padding to the rectangle
     * @return boolean value to indicate if the point is in the rectangle
     */
    private boolean contains(Rectangle r, Point p, int pad) {
        Rectangle t = new Rectangle(r.x, r.y, r.width, r.height);
        t.grow(pad, pad);
        return t.contains(p);
    }

    private Color getWallColour(Direction dir) {
        // if maze or selected cell is null return disabled colour
        if (maze == null || selectedCell == null) return disabledColour;
        return !maze.isPath(selectedCell, dir) ? enabledColour : disabledColour;
    }
}