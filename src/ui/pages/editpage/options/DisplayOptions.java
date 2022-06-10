package ui.pages.editpage.options;

import maze.data.Maze;
import maze.enums.SolveStatus;
import maze.interfaces.MazeListener;
import ui.helper.GridBagHelper;
import ui.helper.UIHelper;
import ui.pages.EditPage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DisplayOptions extends JPanel {
    private final JCheckBox showSolution;
    private final JCheckBox showGrid;

    public JButton colorButton;

    public DisplayOptions(EditPage editPage) {

        // create show solution check box and bind event
        showSolution = UIHelper.createCheckBox("Show Solution");
        showSolution.setEnabled(false);
        showSolution.addActionListener(e -> {
            JCheckBox src = (JCheckBox) e.getSource();
            editPage.setShowSolution(src.isSelected());
        });

        colorButton = new JButton("Choose Color");
        colorButton.setEnabled(false);
        colorButton.addActionListener(e -> {
            Color changeColor = JColorChooser.showDialog(null, "Change Color",Color.RED);
            if (changeColor != null){
                editPage.mazeDisplay.setSolutionColour(changeColor);
            }
        });

        // create show grid check box and bind event
        showGrid = UIHelper.createCheckBox("Show Grid");
        showGrid.addActionListener(e -> {
            JCheckBox src = (JCheckBox) e.getSource();
            editPage.setShowGrid(src.isSelected());
        });

        Border innerBorder = BorderFactory.createTitledBorder("Display options");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,5,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();

        GridBagHelper.addToPanel(this, showSolution, gbc, 0, 0, 1, 1);
        GridBagHelper.addToPanel(this, showGrid, gbc, 0, 1, 1, 1);
        GridBagHelper.addToPanel(this, colorButton, gbc, 0,2,1,1);
    }

    public void setMaze(Maze maze) {
        maze.addListener(new MazeListener() {
            @Override
            public void solveStatusChanged(SolveStatus status) {
                switch (status) {
                    case SOLVED -> {
                        showSolution.setEnabled(true);
                        if (!showSolution.isSelected()) {
                            showSolution.doClick();
                        }

                        colorButton.setEnabled(true);
                    }
                    case UNSOLVED, UNSOLVABLE -> {
                        if (showSolution.isSelected()) {
                            showSolution.doClick();
                        }
                        showSolution.setEnabled(false);
                        colorButton.setEnabled(false);
                    }
                }
            }
        });
    }
}
