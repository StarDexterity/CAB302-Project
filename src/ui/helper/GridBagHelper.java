package ui.helper;

import javax.swing.*;
import java.awt.*;

public final class GridBagHelper {
    // Disable constructor
    private GridBagHelper(){}

    /**
     * Convenience method for quickly creating a new GridBagConstraint object with all default parameters
     */
    public static GridBagConstraints createDefaultGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    public static void addToPanel(JPanel jp, Component c, GridBagConstraints
            constraints, int x, int y, int w, int h) {
        addToPanel(jp, c, constraints, x, y, w, h, null);
    }

    public static void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h, Insets i) {
        addToPanel(jp, c, constraints, x, y, w, h, i, -1);
    }

    public static void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h, Insets i, int anchor) {
        // I don't want to change the underlying object, so I cloned it
        GridBagConstraints gbc = (GridBagConstraints)constraints.clone();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        if (anchor != -1) {
            gbc.anchor = anchor;
        }
        if (i != null) {
            gbc.insets = i;
        }
        jp.add(c, gbc);
    }
}
