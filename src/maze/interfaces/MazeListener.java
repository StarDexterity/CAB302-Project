package maze.interfaces;

import maze.data.MazeImage;
import maze.enums.SolveStatus;

/**
 * Listens and responds to changes in the maze
 */
public interface MazeListener {
    default void mazeChanged() {}
    default void solveStatusChanged(SolveStatus status) {}
    default void addedImage(MazeImage image) {}
    default void removedImage(MazeImage image) {}
}
