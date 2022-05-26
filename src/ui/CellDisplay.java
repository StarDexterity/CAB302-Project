package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CellDisplay extends JComponent {

    /**
     * Margin between the border of the display and the walls
     */
    private int margin = 20;

    /**
     * distance away from (either in the x or y coordinate) from the perpendicular wall,
     * without this the walls would be touching
     */
    private int gap = 12;

    /**
     * The length of the wall (long side)
     */
    private int wallLength = 100;

    /**
     * The thickness of the wall (short side)
     */
    private int wallThickness = 8;

    /**
     * Padding of the interaction rectangle
     */
    private int hoverPadding = 10;


    // rectangle boxes of the four walls, helps for painting and checking for mouse events
    private Rectangle topWall;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle bottomWall;

    // TODO: Pending deletion
    private Rectangle selectTopWall;
    private Rectangle selectLeftWall;
    private Rectangle selectRightWall;
    private Rectangle selectBottomWall;

    // at the moment, these four booleans control the walls that are 'on' and 'off'
    private boolean leftWallEnabled = true;
    private boolean topWallEnabled = false;
    private boolean rightWallEnabled = true;
    private boolean bottomWallEnabled = true;

    // TODO: Pending deletion
    private boolean hoverTopWall;
    private boolean hoverLeftWall;
    private boolean hoverRightWall;
    private boolean hoverBottomWall;

    // color settings
    private Color disabledColour = new Color(192, 192, 192, 200);
    private Color enabledColour = Color.BLACK;
    private Color background = Color.WHITE;
    private Color hoverColour = new Color(106, 116, 173, 128);

    public CellDisplay() {
        super();

        // This part might not exactly make sense, but don't worry about that :)

        // top and bottom wall rectangle definitions
        topWall = new Rectangle(margin + wallThickness + gap, margin, wallLength, wallThickness);

        bottomWall = new Rectangle(margin + wallThickness + gap,
                margin + gap * 2 + wallThickness + wallLength,
                wallLength, wallThickness);


        // left and right wall rectangle definitions
        leftWall = new Rectangle(margin, margin + gap + wallThickness, wallThickness, wallLength);

        rightWall = new Rectangle(margin + gap * 2 + wallThickness + wallLength,
                margin + gap + wallThickness,
                wallThickness, wallLength);

        selectTopWall = copyRect(topWall);
        selectTopWall.grow(10, 10);

        selectBottomWall = copyRect(bottomWall);
        selectBottomWall.grow(10, 10);

        selectLeftWall = copyRect(leftWall);
        selectLeftWall.grow(10, 10);

        selectRightWall = copyRect(rightWall);
        selectRightWall.grow(10, 10);

        // set mouse events
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Point p = e.getPoint();

                if (contains(topWall, p, hoverPadding)) {
                    topWallEnabled = !topWallEnabled;
                }
                else if (contains(leftWall, p, hoverPadding)) {
                    leftWallEnabled = !leftWallEnabled;
                }
                else if (contains(bottomWall, p, hoverPadding)) {
                    bottomWallEnabled = !bottomWallEnabled;
                }
                else if (contains(rightWall, p, hoverPadding)) {
                    rightWallEnabled = !rightWallEnabled;
                }

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                // get mouse position
                Point p = e.getPoint();

                hoverTopWall = false;
                hoverLeftWall = false;
                hoverRightWall = false;
                hoverBottomWall = false;

                if (contains(topWall, p, hoverPadding)) {
                    hoverTopWall = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                else if (contains(leftWall, p, hoverPadding)) {
                    hoverLeftWall = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                else if (contains(bottomWall, p, hoverPadding)) {
                    hoverBottomWall = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                else if (contains(rightWall, p, hoverPadding)) {
                    hoverRightWall = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                else {
                    setCursor(Cursor.getDefaultCursor());
                }

                repaint();
            }
        };


        addMouseListener(ma);
        addMouseMotionListener(ma);


        // key bindings, non functional
        // TODO: make this work
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println(e.getKeyChar());
                if (e.getKeyChar() == 'w') {
                    System.out.println("W");
                }
            }
        };

        addKeyListener(keyAdapter);
    }

    public boolean isLeftWallEnabled() {
        return leftWallEnabled;
    }

    public boolean isTopWallEnabled() {
        return topWallEnabled;
    }

    public boolean isRightWallEnabled() {
        return rightWallEnabled;
    }

    public boolean isBottomWallEnabled() {
        return bottomWallEnabled;
    }

    // these four setters are needed to make sure that the walls are repainted
    public void setLeftWallEnabled(boolean leftWallEnabled) {
        this.leftWallEnabled = leftWallEnabled;
        repaint();
    }

    public void setTopWallEnabled(boolean topWallEnabled) {
        this.topWallEnabled = topWallEnabled;
        notifyListeners();
        repaint();
    }

    public void setRightWallEnabled(boolean rightWallEnabled) {
        this.rightWallEnabled = rightWallEnabled;
        notifyListeners();
        repaint();
    }

    public void setBottomWallEnabled(boolean bottomWallEnabled) {
        this.bottomWallEnabled = bottomWallEnabled;
        notifyListeners();
        repaint();
    }
    public boolean isAllWallsEnabled() {
        return topWallEnabled && leftWallEnabled && bottomWallEnabled && rightWallEnabled;
    }

    public boolean isNoWallsEnabled() {
        return !topWallEnabled && !leftWallEnabled && !bottomWallEnabled && !rightWallEnabled;
    }

    public void setAllWalls(boolean isEnabled) {
        topWallEnabled = isEnabled;
        rightWallEnabled = isEnabled;
        bottomWallEnabled = isEnabled;
        leftWallEnabled = isEnabled;
        notifyListeners();
        repaint();
    }



    @Override
    public Dimension getPreferredSize() {
        int calcSize = margin * 2 + gap * 2 + wallThickness * 2 + wallLength;
        return new Dimension(calcSize, calcSize);
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
        g.setColor(getBackground());

        // draw each walls select

        // Draws each wall using its respective boolean,
        // to determine whether to draw it enabled or disabled
        g.setColor(getWallColour(topWallEnabled, hoverTopWall));
        g.fill(topWall);

        g.setColor(getWallColour(bottomWallEnabled, hoverBottomWall));
        g.fill(bottomWall);

        g.setColor(getWallColour(leftWallEnabled, hoverLeftWall));
        g.fill(leftWall);

        g.setColor(getWallColour(rightWallEnabled, hoverRightWall));
        g.fill(rightWall);
    }



    // observer methods
    // customer listener interface so other classes can keep track of this one without tight coupling
    public interface CellListener {
        void wallChanged();
    }

    private ArrayList<CellListener> listeners = new ArrayList<CellListener>();

    public void AddListener(CellListener cellListener) {
        listeners.add(cellListener);
    }

    private void notifyListeners() {
        for (CellListener l : listeners) {
            l.wallChanged();
        }
    }

    /**
     * Helper function checks if a point is contained within a rectangle + padding,
     * since there is no indication if the user is actually touching the wall
     * I figured a bit of leniency would be a good idea
     * @param r
     * @param p
     * @param pad
     * @return
     */
    private boolean contains(Rectangle r, Point p, int pad) {
        Rectangle t = new Rectangle(r.x, r.y, r.width, r.height);
        t.grow(pad, pad);
        return t.contains(p);
    }

    private Rectangle copyRect(Rectangle r) {
        return new Rectangle(r.x, r.y, r.width, r.height);
    }

    private Color getWallColour(boolean isEnabled, boolean isHovered) {
        Color c = (isEnabled) ? enabledColour : disabledColour;
        return c;
    }
}