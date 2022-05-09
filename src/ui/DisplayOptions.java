package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DisplayOptions extends JPanel {
    private JCheckBox showSolution;
    private JCheckBox showGrid;

    public DisplayOptions() {
        showSolution = UIHelper.createCheckBox("Show Solution");
        showGrid = UIHelper.createCheckBox("Show Grid");

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
    }
}
