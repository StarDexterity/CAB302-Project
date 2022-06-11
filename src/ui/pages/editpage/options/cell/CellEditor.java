package ui.pages.editpage.options.cell;

import maze.data.Maze;
import maze.data.MazeImage;
import maze.data.Position;
import maze.data.Selection;
import maze.enums.Direction;
import maze.enums.SelectionType;
import ui.helper.GridBagHelper;
import ui.pages.EditPage;
import ui.pages.editpage.MazeDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class CellEditor extends JPanel {

    private final JLabel title;
    private final CellDisplay cellDisplay;

    private final JButton fillAll;
    private final JButton clearAll;
    private JButton addImage;
    private JButton removeImage;

    private final JFileChooser fc;


    // data
    private Maze maze;
    private Selection selection;

    private final static FileFilter imageFilter = new FileNameExtensionFilter(
            "Image Files",
            ImageIO.getReaderFileSuffixes());


    public CellEditor(EditPage editPage) {

        // create components
        title = new JLabel("Cell Editor [None]");
        title.setFont(new Font("Tahoma", Font.PLAIN, 18));

        cellDisplay = new CellDisplay();

        // initialse file picker (this takes a bit of time)
        fc = new JFileChooser();
        fc.setFileFilter(imageFilter);


        fillAll = new JButton("Fill all");
        fillAll.addActionListener(e -> cellDisplay.setAll(false));

        clearAll = new JButton("Clear all");
        clearAll.addActionListener(e -> cellDisplay.setAll(true));

        // if the mazeDisplays selected cell is altered, this listener is called
        editPage.mazeDisplay.addListener(e -> {
            this.selection = e;
            if (e.selectionType == SelectionType.CELL) {
                title.setText("Cell Editor [%s, %s]".formatted(e.selectedCell.getX(), e.selectedCell.getY()));

                // set options
                fillAll.setEnabled(true);
                clearAll.setEnabled(true);
                addImage.setEnabled(true);
                removeImage.setEnabled(false);


                cellDisplay.setSelectedCell(e.selectedCell);
            }
            else if (e.selectionType == SelectionType.IMAGE) {
                title.setText("Cell Editor [Image]");


                fillAll.setEnabled(false);
                clearAll.setEnabled(false);
                addImage.setEnabled(false);
                removeImage.setEnabled(true);

            }
            else {
                cellDisplay.setSelectedCell(null);
                title.setText("Cell Editor [None]");
                fillAll.setEnabled(false);
                clearAll.setEnabled(false);
                addImage.setEnabled(false);
                removeImage.setEnabled(false);
            }
        });

        addImage = new JButton("Add Image");
        addImage.addActionListener(e -> {
            if (selection.selectionType == SelectionType.CELL) {
                // get x and y position of selected cell
                Position p1 = selection.selectedCell;
                int x1 = cellDisplay.selectedCell.getX();
                int y1 = cellDisplay.selectedCell.getY();

                JOptionPane.showMessageDialog(getRootPane(), "Top left cell selected, please select bottom right cell");

                editPage.mazeDisplay.addListener(new MazeDisplay.MazeDisplayListener() {
                    @Override
                    public void selectedCellChanged(Selection cce) {
                        if (cce.selectionType == SelectionType.CELL) {
                            // get x and y position of selected cell (will act as bottom right anchor of image)
                            Position p2 = cce.selectedCell;
                            int x2 = cellDisplay.selectedCell.getX();
                            int y2 = cellDisplay.selectedCell.getY();



                            if (x2 >= x1 && y2 >= y1) {
                                // initialise and show file chooser
                                int returnVal = fc.showOpenDialog(getRootPane());

                                // if file chooser aproved
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    File file = fc.getSelectedFile();
                                    MazeImage image = new MazeImage(p1, p2, file);
                                    maze.placeImage(image);
                                }

                            }
                        }
                        editPage.mazeDisplay.removeListener(this);
                    }
                });
            }
        });


        removeImage = new JButton("Remove Image");
        removeImage.addActionListener(e -> {
            if (selection.selectionType == SelectionType.IMAGE) {
                maze.removeImage(selection.selectedImage);
            }
        });


        Border innerBorder = BorderFactory.createTitledBorder("");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 10, 5, 10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutComponents();
        addKeyBindings();
    }

    public void addKeyBindings() {
        // key bindings
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),"removeImage");

        actionMap.put("removeImage",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeImage.doClick();
                    }
                }
        );
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
        GridBagHelper.addToPanel(this, fillAll, gbc, 0, y, 1, 1);

        gbc.insets.top = 5;

        // row 4
        y++;
        GridBagHelper.addToPanel(this, clearAll, gbc, 0, y, 1, 1);

        // row 5
        y++;
        GridBagHelper.addToPanel(this, addImage, gbc, 0, y, 1, 1);

        gbc.insets.bottom = 10;


        // row 6
        y++;
        GridBagHelper.addToPanel(this, removeImage, gbc, 0, y, 1, 1);



    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        cellDisplay.setMaze(maze);
    }
}
