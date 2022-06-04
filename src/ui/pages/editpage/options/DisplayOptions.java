package ui.pages.editpage.options;

import maze.data.Maze;
import maze.enums.SolveStatus;
import ui.helper.GridBagHelper;
import ui.helper.UIHelper;
import ui.pages.EditPage;
import ui.pages.editpage.MazeDisplay;
import ui.pages.editpage.options.cell.CellDisplay;
import ui.pages.editpage.options.image.InsertImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class DisplayOptions extends JPanel {
    private final JCheckBox showSolution;
    private final JCheckBox showGrid;

    public JButton colorButton;
    public JButton addImage;

    InsertImage insertImage = new InsertImage();

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
                editPage.mazeDisplay.changeSolutionColor(changeColor);
            }
        });

        addImage = new JButton("Add Image");
        addImage.addActionListener(e -> {
            if (CellDisplay.selectedCell != null){
                InsertImage.currentMaze = editPage.currentMaze;
                InsertImage.imageCell=CellDisplay.selectedCell;
                editPage.mazeDisplay.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (CellDisplay.selectedCell != null) {
                            InsertImage.bottomRight = CellDisplay.selectedCell;
                            insertImage.getImage();
                            editPage.currentMaze = InsertImage.currentMaze;
                            editPage.mazeDisplay.addImage(true);
                            editPage.mazeDisplay.removeMouseListener(this);
                        }
                    }
                });

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
        GridBagHelper.addToPanel(this, addImage, gbc,1,0,1,1 );
    }

    public void setMaze(Maze maze) {
        maze.addListener(new Maze.MazeListener() {
            @Override
            public void solveStatusChanged(SolveStatus status) {
                switch (status) {
                    case SOLVED -> {
                        showSolution.setEnabled(true);
                        colorButton.setEnabled(true);
                    }
                    case UNSOLVED, UNSOLVABLE -> {
                        showSolution.doClick();
                        showSolution.setEnabled(false);
                        colorButton.setEnabled(false);
                    }
                }
            }
        });
    }
}
