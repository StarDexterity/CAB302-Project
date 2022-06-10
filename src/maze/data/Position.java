package maze.data;

/**
 * Holds a cartesian coordinate, representing the position in the internal maze grid of a cell.
 * Used by some classes to better store coordinates intermediately for convenience.
 * For example, by some maze generator functions within a queue
 */
public class Position {
    private int x;
    private int y;

    /**
     * The parent position to this position,
     *  used by the bfs solving algorithm to reconstruct a path
     */
    private Position prev;

    public static final Position ZERO = new Position(0, 0);

    //TODO: delete?
    public static Position getBottomRight(Maze maze) {
        return new Position(maze.getCols() - 1, maze.getRows() - 1);
    }

    /**
     * Constructs a new position from an x and y coordinate
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new position as a clone of a given position
     * @param p
     */
    public Position(Position p) {
        this.x = p.getX();
        this.y = p.getY();
        this.prev = p.getPrev();
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public Position getPrev() { return prev; }

    public void setX(int value) { x = value; }

    public void setY(int value) { y = value; }

    public void setPrev(Position pos) { prev = pos; }

    /**
     * Checks that the supplied position is equal to the position of this object
     * @param obj a Position
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position pos) {
            return (this.x == pos.x && this.y == pos.y);
        }
        return false;
    }

    /**
     * Turns this object into a String
     */
    @Override

    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
