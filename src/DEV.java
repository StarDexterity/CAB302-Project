import java.awt.*;
import java.util.BitSet;

public class DEV {
    public static void main(String[] args) {
        //test data
        Dimension dimension = new Dimension(2, 3);

        BitSet[] testMazeRows = new BitSet[dimension.height + 1]; // 4 rows
        testMazeRows[0] = BitSet.valueOf(new long[]{0b01});
        testMazeRows[1] = BitSet.valueOf(new long[]{0b10});
        testMazeRows[2] = BitSet.valueOf(new long[]{0b10});
        testMazeRows[3] = BitSet.valueOf(new long[]{0b01});

        BitSet[] testMazeColumns = new BitSet[dimension.width + 1]; // 3 columns
        testMazeColumns[0] = BitSet.valueOf(new long[]{0b111});
        testMazeColumns[1] = BitSet.valueOf(new long[]{0b001});
        testMazeColumns[2] = BitSet.valueOf(new long[]{0b111});

        Maze testMaze = new Maze(dimension);

        testMaze.devSetMazeData(testMazeRows, testMazeColumns);

        System.out.println(testMaze.canPass(new Dimension(0, 1), Direction.EAST)); //True
        System.out.println(testMaze.canPass(new Dimension(0, 1), Direction.WEST)); //False
        System.out.println(testMaze.canPass(new Dimension(1, 2), Direction.NORTH)); //False
        System.out.println(testMaze.canPass(new Dimension(1, 2), Direction.SOUTH)); //False
        System.out.println(testMaze.canPass(new Dimension(1, 2), Direction.EAST)); //True
    }
}
