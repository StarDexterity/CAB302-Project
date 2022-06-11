package ui.pages;

import maze.data.Maze;
import ui.App;
import ui.dialog.SaveDialog;
import ui.pages.editpage.MazeDisplay;
import ui.pages.editpage.OptionsPanel;

import javax.swing.*;
import java.awt.*;

public class EditPage extends JPanel {
    private OptionsPanel optionsPanel;
    private JScrollPane scrollPane;
    public MazeDisplay mazeDisplay;

    public SaveDialog saveDialog;

    /**
     * Stores the current maze object being edited
     */
    public Maze currentMaze;


    public EditPage() {
        super();
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        mazeDisplay = new MazeDisplay();
        optionsPanel = new OptionsPanel(this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(mazeDisplay);

        // initialise dialogs
        saveDialog = new SaveDialog(new JFrame());

        // layout code
        add(optionsPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setMaze(Maze maze) {
        currentMaze = maze;
        mazeDisplay.setMaze(maze);
        optionsPanel.setMaze(maze);

        saveDialog.setMaze(maze);

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

