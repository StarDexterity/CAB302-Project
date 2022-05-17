package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OptionsPanel extends JPanel {


    DisplayOptions displayOptions;
    RegenerateOptions regenerateOptions;
    SolveStatus solveStatus;
    SaveOptions saveOptions;

    public EditPage editPage;


    public OptionsPanel(EditPage editPage){
        this.editPage = editPage;

        Dimension dim  = getPreferredSize();
        dim.width = 300;
        setPreferredSize(dim);

        displayOptions = new DisplayOptions(editPage);
        solveStatus = new SolveStatus(editPage);
        saveOptions = new SaveOptions(editPage);

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
        //GridBagHelper.addToPanel(this, regenerateOptions, gbc, 0, y, 1, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, solveStatus, gbc, 0, y, 1, 1);


        // row 4
        y++;
        GridBagHelper.addToPanel(this, saveOptions, gbc, 0, y, 1, 1);

    }
}