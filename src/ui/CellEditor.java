package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CellEditor extends JPanel {

    public CellEditor() {
        Border innerBorder = BorderFactory.createTitledBorder("Display options");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,5,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();

        //GridBagHelper.addToPanel(this, showGrid, gbc, 0, 1, 1, 1);
    }
}
