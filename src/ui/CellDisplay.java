package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellDisplay extends JComponent {

    /**
     * Margin between the border of the display and the walls
     */
    private int margin = 15;

    /**
     * distance away from (either in the x or y coordinate) from the perpendicular wall,
     * without this the walls would be touching
     */
    private int gap = 10;

    /**
     * The length of the wall (long side)
     */
    private int wallLength = 100;

    /**
     * The thickness of the wall (short side)
     */
    private int wallThickness = 7;

    // rectangle boxes of the four walls, helps for painting and checking for mouse events
    private Rectangle topWall;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle bottomWall;

    // at the moment, these four booleans control the walls that are 'on' and 'off'
    private boolean leftWallEnabled = true;
    private boolean topWallEnabled = false;
    private boolean rightWallEnabled = true;
    private boolean bottomWallEnabled = true;

    // color settings
    private Color disabledColor = new Color(192, 192, 192, 200);
    private Color enabledColor = Color.BLACK;

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

        // set mouse events
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);

                Point p = e.getPoint();

                if (contains(topWall, p, 10)) {
                    topWallEnabled = !topWallEnabled;
                }
                else if (contains(leftWall, p, 10)) {
                    leftWallEnabled = !leftWallEnabled;
                }
                else if (contains(bottomWall, p, 10)) {
                    bottomWallEnabled = !bottomWallEnabled;
                }
                else if (contains(rightWall, p, 10)) {
                    rightWallEnabled = !rightWallEnabled;
                }

                repaint();
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
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
        repaint();
    }

    public void setRightWallEnabled(boolean rightWallEnabled) {
        this.rightWallEnabled = rightWallEnabled;
        repaint();
    }

    public void setBottomWallEnabled(boolean bottomWallEnabled) {
        this.bottomWallEnabled = bottomWallEnabled;
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

        // Draws each wall using its respective boolean,
        // to determine whether to draw it enabled or disabled
        g.setColor(topWallEnabled ? enabledColor : disabledColor);
        g.fill(topWall);

        g.setColor(bottomWallEnabled ? enabledColor : disabledColor);
        g.fill(bottomWall);

        g.setColor(leftWallEnabled ? enabledColor : disabledColor);
        g.fill(leftWall);

        g.setColor(rightWallEnabled ? enabledColor : disabledColor);
        g.fill(rightWall);
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
}