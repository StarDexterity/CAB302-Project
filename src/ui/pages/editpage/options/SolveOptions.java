package ui.pages.editpage.options;

import maze.data.Maze;
import maze.enums.SolveStatus;
import maze.helper.MazeSolver;
import maze.interfaces.MazeListener;
import ui.helper.GridBagHelper;
import ui.pages.EditPage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SolveOptions extends JPanel {
    private final JLabel deadEndLabel;
    private final JLabel deadEndStatus;
    private final JLabel explorationLabel;
    private final JLabel explorationStatus;
    private final JLabel solveLabel;
    private final JLabel solveStatus;
    private final JButton solve;

    private Maze maze;

    public SolveOptions(EditPage editPage) {

        //The status labels will need to be reactive in later stages of the project
        deadEndLabel = new JLabel("Dead end %");
        deadEndStatus = new JLabel("0");
        explorationLabel = new JLabel("Solution %");
        explorationStatus = new JLabel("0");
        solveLabel = new JLabel("Solve status");
        solveStatus = new JLabel("Not solved");
        solve = new JButton("Solve");

        //MY ATTEMPT AT GETTING THE BUTTON TO WORK - Connor
        solve.addActionListener(e -> {
            if (maze == null) return;

            MazeSolver.solve(maze);
            explorationStatus.setText(Double.toString(MazeSolver.TotalPassThrough(editPage.currentMaze)));
            deadEndStatus.setText(Double.toString(MazeSolver.TotalDeadEnds(editPage.currentMaze)));
        });

        Border innerBorder = BorderFactory.createTitledBorder("Solve status");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,5,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        gbc.insets = new Insets(0, 0, 5, 5);
        int y = 0;

        // row 1
        GridBagHelper.addToPanel(this, deadEndLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, deadEndStatus, gbc, 2, y, 1, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, explorationLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, explorationStatus, gbc, 2, y, 1, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, solveLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, solveStatus, gbc, 2, y, 1, 1);

        // row 4
        y++;
        GridBagHelper.addToPanel(this, solve, gbc, 2, y, 1, 1);
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        this.maze.addListener(new MazeListener() {
            @Override
            public void solveStatusChanged(SolveStatus status) {
                solveStatus.setText(status.getName());
            }
        });
    }
}