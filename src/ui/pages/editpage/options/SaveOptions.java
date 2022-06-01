package ui.pages.editpage.options;

import ui.helper.GridBagHelper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import static maze.Export.exportMaze;


public class SaveOptions extends JPanel {
    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel authorLabel;
    private JTextField authorField;
    private JLabel descriptionLabel;
    private JTextArea descriptionField;
    private JScrollPane descriptionFieldScroller;
    private JButton saveButton;
    private JButton exportButton;
    private JButton deleteButton;

    public SaveOptions() {
        //The status labels will need to be reactive in later stages of the project
        titleLabel = new JLabel("Title");
        titleField = new JTextField("");
        authorLabel = new JLabel("Author");
        authorField = new JTextField("");
        descriptionLabel = new JLabel("Description");
        descriptionField = new JTextArea("");
        descriptionFieldScroller = new JScrollPane(descriptionField);
        descriptionField.setRows(3);
        saveButton = new JButton("Save");
        exportButton = new JButton("Export");
        deleteButton = new JButton("Delete");

        Border innerBorder = BorderFactory.createTitledBorder("Save options");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,10,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        gbc.insets = new Insets(0, 0, 5, 5);
        int y = 0;

        // row 1
        GridBagHelper.addToPanel(this, titleLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, titleField, gbc, 1, y, 2, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, authorLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, authorField, gbc, 1, y, 2, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, descriptionLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, descriptionFieldScroller, gbc, 1, y, 2, 1);


        // row 4
        y++;
        GridBagHelper.addToPanel(this, saveButton, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, exportButton, gbc, 1, y, 1, 1);
        GridBagHelper.addToPanel(this, deleteButton, gbc, 2, y, 1, 1);


        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("testing button");
                try {
                    exportMaze("export maze");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        //if (exportButton) {

       // }

    }
}