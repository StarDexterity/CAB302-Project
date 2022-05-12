package ui;

import maze.Maze;

import javax.swing.*;
import java.awt.*;

public class EditPage extends JPanel {
    private App app;
    private OptionsPanel optionsPanel;
    private MazeDisplay mazeDisplay;

    /**
     * Stores the current maze object being edited
     */
    public Maze currentMaze;


    public EditPage(App app) {
        super();
        this.app = app;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        optionsPanel = new OptionsPanel();

        // layout code
        add(optionsPanel, BorderLayout.WEST);

        // placeholder code to test grid and solution options
        JButton toggleGridTemp = new JButton("Toggle grid");
        toggleGridTemp.addActionListener(e -> mazeDisplay.setShowGrid(!mazeDisplay.isShowGrid()));

        JButton toggleSolutionTemp = new JButton("Toggle solution");
        toggleSolutionTemp.addActionListener(e -> mazeDisplay.setShowSolution(!mazeDisplay.isShowSolution()));

        add(toggleGridTemp, BorderLayout.SOUTH);
        add(toggleSolutionTemp, BorderLayout.EAST);
    }

    public void setMaze(Maze maze) {
        if (mazeDisplay != null) remove(mazeDisplay);
        currentMaze = maze;
        mazeDisplay = new MazeDisplay(maze, false);
        add(mazeDisplay, BorderLayout.CENTER);

        // It just works ;)
        revalidate();
        repaint();
    }
}

