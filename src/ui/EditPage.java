package ui;

import maze.Maze;

import javax.swing.*;
import java.awt.*;

public class EditPage extends JPanel {
    private App app;
    private OptionsPanel optionsPanel;
    private MazeDisplay currentMaze;


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
    }

    public void setMaze(Maze maze) {
        if(currentMaze != null) remove(currentMaze);
        currentMaze = new MazeDisplay(maze, false);
        add(currentMaze, BorderLayout.CENTER);
    }
}

