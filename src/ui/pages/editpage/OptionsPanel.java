package ui.pages.editpage;

import maze.data.Maze;
import ui.pages.editpage.options.DisplayOptions;
import ui.pages.editpage.options.SolveOptions;
import ui.pages.editpage.options.cell.CellEditor;
import ui.helper.GridBagHelper;
import ui.pages.EditPage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OptionsPanel extends JPanel {


    DisplayOptions displayOptions;
    SolveOptions solveStatus;
    CellEditor cellEditor;

    public OptionsPanel(EditPage editPage){

        Dimension dim  = getPreferredSize();
        dim.width = 300;
        setPreferredSize(dim);

        displayOptions = new DisplayOptions(editPage);
        solveStatus = new SolveOptions(editPage);
        cellEditor = new CellEditor(editPage);

        Border innerBorder = BorderFactory.createTitledBorder("Options");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,10,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents()
    {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        int y = 0;

        // row 1
        GridBagHelper.addToPanel(this, displayOptions, gbc, 0, y, 1, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, solveStatus, gbc, 0, y, 1, 1);


        // row 3
        y++;
        GridBagHelper.addToPanel(this, cellEditor, gbc, 0, y, 1, 1);

    }

    public void setMaze(Maze maze) {
        displayOptions.setMaze(maze);
        solveStatus.setMaze(maze);
        cellEditor.setMaze(maze);
    }
}