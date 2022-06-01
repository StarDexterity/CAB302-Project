package ui.pages.editpage.options;

import ui.helper.GridBagHelper;
import ui.helper.UIHelper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RegenerateOptions extends JPanel {
    private JLabel sizeLabel;
    private JLabel sizeXLabel;
    private JLabel sizeYLabel;
    private JTextField sizeX;
    private JTextField sizeY;
    private JLabel generateLabel;
    private JComboBox generationCBox;
    private JLabel seedLabel;
    private JCheckBox seedCheck;
    private JTextField seedInput;
    private JButton generateButton;

    public RegenerateOptions() {
        sizeLabel = new JLabel("Size");
        sizeX = new JTextField("0");
        sizeXLabel = new JLabel("x");
        sizeYLabel = new JLabel("y");
        sizeY = new JTextField("0");
        generateLabel = new JLabel("Generate");

        // create combobox and add options
        generationCBox = new JComboBox();
        generationCBox.addItem("Manual");
        generationCBox.addItem("Automatic");

        seedLabel = new JLabel("Seed");
        seedCheck =  UIHelper.createCheckBox("");


        seedInput = new JTextField("");
        generateButton = new JButton("Generate");

        Border innerBorder = BorderFactory.createTitledBorder("Regenerate menu");
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
        GridBagHelper.addToPanel(this, sizeLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, sizePanel(), gbc, 2, y, 1, 1);


        // row 2
        y++;
        GridBagHelper.addToPanel(this, generateLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, generationCBox, gbc, 2, y, 2, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, seedLabel, gbc, 0, y, 1, 1);

        // create a new grid bag constraint to align checkbox to the right of the input field
        GridBagConstraints check = GridBagHelper.createDefaultGBC();
        check.fill = GridBagConstraints.NONE;
        check.anchor = GridBagConstraints.EAST;

        GridBagHelper.addToPanel(this, seedCheck, check, 1, y, 1, 1);
        GridBagHelper.addToPanel(this, seedInput, gbc, 2, y, 1, 1);

        // row 4
        y++;
        GridBagHelper.addToPanel(this, generateButton, gbc, 2, y, 1, 1);
    }

    /**
     * Creates the size panel, and lays out items accordingly
     */
    private JPanel sizePanel() {
        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = GridBagHelper.createDefaultGBC();

        // size x label and input
        GridBagHelper.addToPanel(sizePanel, sizeXLabel, gc, 0, 0, 1, 1);
        GridBagHelper.addToPanel(sizePanel, sizeX, gc, 1, 0, 1, 1);

        // size y label and input
        GridBagHelper.addToPanel(sizePanel, sizeYLabel, gc, 2, 0, 1, 1, new Insets(0, 5, 0, 0));
        GridBagHelper.addToPanel(sizePanel, sizeY, gc, 3, 0, 1, 1);

        return sizePanel;
    }
}