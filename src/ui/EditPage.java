package ui;

import maze.Maze;

import javax.swing.*;
import java.awt.*;

public class EditPage extends JPanel {
    private App app;
    private OptionsPanel optionsPanel;
    private JScrollPane scrollPane;
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

        optionsPanel = new OptionsPanel(this);
        scrollPane = new JScrollPane();
        mazeDisplay = new MazeDisplay();
        scrollPane.setViewportView(mazeDisplay);

        // layout code
        add(optionsPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setMaze(Maze maze) {
        currentMaze = maze;
        App.currentMaze = currentMaze;
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

