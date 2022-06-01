package maze.enums;

public enum SolveStatus {
    SOLVED("Solved"),
    UNSOLVED("Not solved"),
    UNSOLVABLE("Unsolvable");

    private String name;

    SolveStatus(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static SolveStatus getOption(String s) {
        for (SolveStatus option : SolveStatus.values()) {
            if (option.name == s) return option;
        }
        return null;
    }

}
