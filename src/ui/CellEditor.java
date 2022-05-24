package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CellEditor extends JPanel {

    EditPage editPage;
    JLabel title;
    CellDisplay cellDisplay;
    JButton toggleRight;
    JButton toggleLeft;
    JButton toggleTop;
    JButton toggleBottom;
    JButton toggleAll;
    JButton insertImage;


    public CellEditor(EditPage editPage) {
        this.editPage = editPage;

        // create components
        title = new JLabel("Cell editor [F3]");
        title.setFont(new Font("Tahoma", Font.PLAIN,  18));
        cellDisplay = new CellDisplay();

        toggleTop = new JButton("Toggle top");
        toggleTop.addActionListener(e -> {
            boolean topWallEnabled = cellDisplay.isTopWallEnabled();
            cellDisplay.setTopWallEnabled(!topWallEnabled);
        });

        toggleLeft = new JButton("Toggle left");
        toggleLeft.addActionListener(e -> {
            boolean leftWallEnabled = cellDisplay.isLeftWallEnabled();
            cellDisplay.setLeftWallEnabled(!leftWallEnabled);
        });

        toggleBottom = new JButton("Toggle bottom");
        toggleBottom.addActionListener(e -> {
            boolean bottomWallEnabled = cellDisplay.isBottomWallEnabled();
            cellDisplay.setBottomWallEnabled(!bottomWallEnabled);
        });

        toggleRight = new JButton("Toggle Right");
        toggleRight.addActionListener(e -> {
            boolean rightWallEnabled = cellDisplay.isRightWallEnabled();
            cellDisplay.setRightWallEnabled(!rightWallEnabled);
        });

        toggleAll = new JButton("Toggle all");
        insertImage = new JButton("Insert image");


        Border innerBorder = BorderFactory.createTitledBorder("");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,5,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        int y = 0;

        // row 1
        GridBagHelper.addToPanel(this, title, gbc, 0, y, 1, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, cellDisplay, gbc, 0, y, 1, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, toggleTop, gbc, 0, y, 1, 1);

        // row 4
        y++;
        GridBagHelper.addToPanel(this, toggleLeft, gbc, 0, y, 1, 1);

        // row 5
        y++;
        GridBagHelper.addToPanel(this, toggleBottom, gbc, 0, y, 1, 1);

        // row 6
        y++;
        GridBagHelper.addToPanel(this, toggleRight, gbc, 0, y, 1, 1);

        // row 7
        y++;
        GridBagHelper.addToPanel(this, toggleAll, gbc, 0, y, 1, 1);

        // row 8
        y++;
        GridBagHelper.addToPanel(this, insertImage, gbc, 0, y, 1, 1);

    }
}
