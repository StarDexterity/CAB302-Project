package tests;

public final class DummyMazes {

    // Disable constructor
    private DummyMazes(){};

    public static int[][] empty = new int[][] {{6, 14, 14, 10}, {7, 15, 15, 11}, {7, 15, 15, 11}, {5, 13, 13, 9}};
    public static int[][] full = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    public static int[][] random = new int[][]{{2, 4, 14, 10}, {5, 10, 3, 3}, {6, 9, 3, 3}, {5, 12, 9, 1}};
    public static int[][] lateFail = new int[][]{{6, 14, 14, 10}, {7, 15, 15, 11}, {7, 15, 15, 9}, {5, 13, 9, 0}};
    public static int[][] fullCoverage = new int[][]{{4, 12, 12, 12, 10}, {6, 12, 12, 12, 9}, {5, 12, 12, 12, 10}, {6, 12, 12, 12, 9}, {5, 12, 12, 12, 8}};
    public static int[][] x_long = new int[][]{{2, 6, 14, 10}, {3, 7, 15, 11}, {3, 7, 15, 11}, {3, 7, 15, 11}, {3, 7, 15, 11}, {5, 13, 9, 1}};
    public static int[][] y_wide = new int[][]{{4, 12, 12, 12, 12, 10}, {6, 14, 14, 14, 14, 11}, {7, 15, 15, 15, 15, 9}, {5, 13, 13, 13, 13, 8}};
    public static int[][] badData = new int [][]{{1, 2, 3},{1, 2, 3, 4 , 5}};
    public static int[][] emptySet = new int [][]{{},{}};

}