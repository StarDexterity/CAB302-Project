package maze.enums;

/**
 * List of different possible solve states of a maze
 */
public enum SolveStatus {
    SOLVED("Solved"),
    UNSOLVED("Not solved"),
    UNSOLVABLE("Unsolvable");

    private String name;

    /**
     * Sets the solve state of a maze
     * @param name
     */
    SolveStatus(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
