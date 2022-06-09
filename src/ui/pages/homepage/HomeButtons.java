package ui.pages.homepage;

import ui.helper.GridBagHelper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class HomeButtons extends JPanel {


    private JButton exportSelected;
    private JButton deleteSelected;

    public HomeButtons() {

        Dimension dim = getPreferredSize();
        dim.height = 50;
        dim.width = 300;
        setPreferredSize(dim);

        Border innerBorder = BorderFactory.createTitledBorder("");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        exportSelected = new JButton("Export selected");
        deleteSelected = new JButton("Delete selected");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        gbc.insets = new Insets(0, 0, 5, 5);

        // row 1
        GridBagHelper.addToPanel(this, exportSelected, gbc, 0, 0, 1, 1);
        GridBagHelper.addToPanel(this, deleteSelected, gbc, 2, 0, 1, 1);


    }
}
