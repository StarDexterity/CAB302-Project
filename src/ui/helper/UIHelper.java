package ui.helper;

import javax.swing.*;

/**
 * A static helper class that provides methods for creating ui elements with consistent values, and other misc helper methods for ui
 */
public final class UIHelper {
    // Disable constructor
    private UIHelper(){}

    public static JCheckBox createCheckBox(String s) {
        JCheckBox checkBox = new JCheckBox(s);
        checkBox.setFocusable(false);
        return checkBox;
    }
}
