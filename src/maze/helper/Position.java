package maze.helper;

import maze.data.Maze;

/**
 * Holds a cartesian coordinate, representing the position in the internal maze grid of a cell.
 * Used by some classes to better store coordinates intermediately for convenience.
 * For example, by some maze generator functions within a queue
 */
public class Position {
    private int x;
    private int y;

    public static final Position ZERO = new Position(0, 0);

    public static Position getBottomRight(Maze maze) {
        return new Position(maze.getCols() - 1, maze.getRows() - 1);
    }


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int value) { x = value; }

    public void setY(int value) { y = value; }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = ((Position) obj);
            return (this.x == p.x && this.y == p.y);
        }
        return false;
    }
}
