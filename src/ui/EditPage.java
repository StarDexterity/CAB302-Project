package ui;

import maze.data.Maze;

import javax.swing.*;
import java.awt.*;

public class EditPage extends JPanel {
    private App app;
    private OptionsPanel optionsPanel;
    private JScrollPane scrollPane;
    public MazeDisplay mazeDisplay;

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

        mazeDisplay = new MazeDisplay();
        optionsPanel = new OptionsPanel(this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(mazeDisplay);

        // layout code
        add(optionsPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setMaze(Maze maze) {
        currentMaze = maze;
        mazeDisplay.setMaze(maze);

        // It just works ;)
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    public void setShowGrid(boolean value) {
        mazeDisplay.setShowGrid(value);
    }

    public void setShowSolution(boolean value) {
        mazeDisplay.setShowSolution(value);
    }

}

