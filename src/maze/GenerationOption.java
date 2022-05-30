package maze;

public enum GenerationOption {
    DFS("DFS"),
    PRIM("Prim"),
    ALDOUS("Aldous-Broder"),
    EMPTY("Empty");

    private String name;

    GenerationOption(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static GenerationOption getOption(String s) {
        for (GenerationOption option : GenerationOption.values()) {
            if (option.name == s) return option;
        }
        return null;
    }

}
