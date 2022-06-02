package ui.pages.editpage.options.cell;

import maze.data.Maze;
import ui.helper.GridBagHelper;
import ui.pages.EditPage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CellEditor extends JPanel {

    private final JLabel title;
    private final CellDisplay cellDisplay;

    private final JButton setAll;
    private final JButton clearAll;

    public CellEditor(EditPage editPage) {

        // create components
        title = new JLabel("Cell Editor [None]");
        title.setFont(new Font("Tahoma", Font.PLAIN,  18));

        cellDisplay = new CellDisplay();


        setAll = new JButton("Fill all");
        setAll.addActionListener(e -> cellDisplay.setAll(false));

        clearAll = new JButton("Clear all");
        clearAll.addActionListener(e -> cellDisplay.setAll(true));

        // if the mazeDisplays selected cell is altered, this listener is called
        editPage.mazeDisplay.addListener(e -> {

            if (e.isCellSelected) {
                title.setText("Cell Editor [%s, %s]".formatted(e.selectedCell.getX(), e.selectedCell.getY()));
                // update the display with new walls
                cellDisplay.setSelectedCell(e.selectedCell);
            }
            else {
                cellDisplay.setSelectedCell(null);
                title.setText("Cell Editor [None]");
            }
        });

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

        // row 1
        int y = 0;
        GridBagHelper.addToPanel(this, title, gbc, 0, y, 1, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, cellDisplay, gbc, 0, y, 1, 1);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 50, 0, 50);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, setAll, gbc, 0, y, 1, 1);

        gbc.insets.top = 5;
        gbc.insets.bottom = 10;

        // row 4
        y++;
        GridBagHelper.addToPanel(this, clearAll, gbc, 0, y, 1, 1);

    }

    public void setMaze(Maze maze) {
        cellDisplay.setMaze(maze);
    }
}
