package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CellEditor extends JPanel {

    private EditPage editPage;

    private JLabel title;
    private CellDisplay cellDisplay;

    // TODO: Pending deletion of these 5 buttons
    private JButton toggleRight;
    private JButton toggleLeft;
    private JButton toggleTop;
    private JButton toggleBottom;
    private JButton insertImage;

    private JButton setAll;
    private JButton clearAll;


    /**
     * Stores the position of the currently selected cell
     */
    public int cellX = 0;
    public int cellY = 0;

    /**
     * Is a cell currently selected
     */
    public boolean isCellSelected = true;


    public CellEditor(EditPage editPage) {
        this.editPage = editPage;

        // create components
        title = new JLabel("Cell Editor [%s, %s]".formatted(cellX, cellY));
        title.setFont(new Font("Tahoma", Font.PLAIN,  18));

        // cell display component and custom listener
        cellDisplay = new CellDisplay();
        cellDisplay.AddListener((dir) -> {
            if (isCellSelected) {
                editPage.currentMaze.setWall(cellX, cellY, dir, !cellDisplay.isWall(dir));
            }
        });

        toggleTop = new JButton("Toggle top");
        toggleTop.addActionListener(e -> {
            boolean topWall = cellDisplay.isTopWall();
            cellDisplay.setTopWall(!topWall);
        });

        toggleLeft = new JButton("Toggle left");
        toggleLeft.addActionListener(e -> {
            boolean leftWall = cellDisplay.isLeftWall();
            cellDisplay.setLeftWall(!leftWall);
        });

        toggleBottom = new JButton("Toggle bottom");
        toggleBottom.addActionListener(e -> {
            boolean bottomWall = cellDisplay.isBottomWall();
            cellDisplay.setBottomWall(!bottomWall);
        });

        toggleRight = new JButton("Toggle Right");
        toggleRight.addActionListener(e -> {
            boolean rightWall = cellDisplay.isRightWall();
            cellDisplay.setRightWall(!rightWall);
        });

        setAll = new JButton("Set all");
        setAll.addActionListener(e -> {
            cellDisplay.setAllWalls(true);
        });

        clearAll = new JButton("Clear all");
        clearAll.addActionListener(e -> {
           cellDisplay.setAllWalls(false);
        });

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
}
