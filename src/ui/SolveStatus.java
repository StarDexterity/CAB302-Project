package ui;

import maze.MazeSolver;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolveStatus extends JPanel {
    private JLabel deadendLabel;
    private JLabel deadendStatus;
    private JLabel explorationLabel;
    private JLabel explorationStatus;
    private JLabel solveLabel;
    private JLabel solveStatus;
    private JButton solve;

    private EditPage editPage;

    public SolveStatus(EditPage editPage) {
        this.editPage = editPage;

        //The status labels will need to be reactive in later stages of the project
        deadendLabel = new JLabel("Dead end %");
        deadendStatus = new JLabel("0");
        explorationLabel = new JLabel("Solution %");
        explorationStatus = new JLabel("0");
        solveLabel = new JLabel("Solve status");
        solveStatus = new JLabel("Not solved");
        solve = new JButton("Solve");

        //MY ATTEMPT AT GETTING THE BUTTON TO WORK - Connor
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    //EditPage.m.solve(0);
                    solveStatus.setText("Solved");
                    DisplayOptions.showSolution.setEnabled(true);
                    DisplayOptions.showGrid.setEnabled(true);
                    DisplayOptions.colorButton.setEnabled(true);
                    explorationStatus.setText(MazeSolver.TotalPassThrough(editPage.currentMaze));
                    deadendStatus.setText(MazeSolver.TotalDeadEnds(editPage.currentMaze));
                }).start();
            }
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
        GridBagHelper.addToPanel(this, deadendLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, deadendStatus, gbc, 2, y, 1, 1);

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
}