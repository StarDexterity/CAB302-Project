package maze.enums;

/**
 * An enum that provides cardinal directions
 */
public enum Direction {
    //N = north; S = south; E = east; W = west;
    N(1, 0, -1),
    S(2, 0, 1),
    E(4, 1, 0),
    W(8, -1, 0);

    /**
     * This enum can have multiple values,
     *          the bit of the enum is the combination of flags that enum has.
     *          For example, if the enum has a bit value of 7
     *              It has N and E and S neighbors
     */
    public final int bit;


    /**
     * The displacement of x
     *          e.g. E has a dx of 1:
     *              because the neighbor of this vertex to the east is in index position of this vertex in the x + 1
     */
    public final int dx;


    /**
     * The displacement of y
     *          e.g. N has a dy of -1:
     *              because the neighbor of this vertex to the north is in the index position of this vertex in the y - 1
     */
    public final int dy;

    // Together dx and dy point in the direction of the neighboring vertex

    /**
     * The opposite direction, to the current direction (e.g. N.opposite = S)
     */
    public Direction opposite;

    // If one vertex a connects to another b, than b must connect to a using the opposite direction enum bit
    static {
        N.opposite = S;
        S.opposite = N;
        E.opposite = W;
        W.opposite = E;
    }

    /**
     * Sets the enum values
     * @param bit The bit value to be changed
     * @param dx The distance in the x direction
     * @param dy The distance in the y direction
     */
    Direction(int bit, int dx, int dy) {
        this.bit = bit;
        this.dx = dx;
        this.dy = dy;
    }
}