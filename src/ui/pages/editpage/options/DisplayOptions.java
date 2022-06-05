package ui.pages.editpage.options;

import maze.data.Maze;
import maze.enums.SolveStatus;
import ui.helper.GridBagHelper;
import ui.helper.UIHelper;
import ui.pages.EditPage;
import ui.pages.editpage.options.cell.CellDisplay;
import ui.pages.editpage.options.image.InsertImage;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayOptions extends JPanel {
    private final JCheckBox showSolution;
    private final JCheckBox showGrid;

    public JButton colorButton;
    public JButton addImage;

    public JButton removeImage;

    private Maze maze;

    InsertImage insertImage = new InsertImage();

    public DisplayOptions(EditPage editPage) {

        // create show solution check box and bind event
        showSolution = UIHelper.createCheckBox("Show Solution");
        showSolution.setEnabled(false);
        showSolution.addActionListener(e -> {
            JCheckBox src = (JCheckBox) e.getSource();
            editPage.setShowSolution(src.isSelected());
        });

        colorButton = new JButton("Choose Color");
        colorButton.setEnabled(false);
        colorButton.addActionListener(e -> {
            Color changeColor = JColorChooser.showDialog(null, "Change Color",Color.RED);
            if (changeColor != null){
                editPage.mazeDisplay.changeSolutionColor(changeColor);
            }
        });

        addImage = new JButton("Add Image");
        addImage.addActionListener(e -> {

            if (CellDisplay.selectedCell != null){
                int x1 = CellDisplay.selectedCell.getX();
                int y1 = CellDisplay.selectedCell.getY();

                insertImage.currentMaze = editPage.currentMaze;
                insertImage.imageCell=CellDisplay.selectedCell;
                insertImage.newImg=null;
                editPage.mazeDisplay.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (CellDisplay.selectedCell != null) {
                            int x2 = CellDisplay.selectedCell.getX();
                            int y2 = CellDisplay.selectedCell.getY();
                            if (x2 >= x1 && y2 >=y1){
                                insertImage.bottomRight = CellDisplay.selectedCell;
                                insertImage.getImage();
                                editPage.currentMaze = insertImage.currentMaze;
                                editPage.mazeDisplay.addImage(true);
                                InsertImage.currentMaze.placeImage();
                                editPage.mazeDisplay.removeMouseListener(this);
                            }else{
                                editPage.mazeDisplay.removeMouseListener(this);
                            }

                        }
                    }
                });

            }
        });

        removeImage = new JButton("Remove Image");
        removeImage.addActionListener(e -> {
            if (insertImage.imageTopLeft.contains(CellDisplay.selectedCell)){
                int x = insertImage.imageTopLeft.indexOf(CellDisplay.selectedCell);
                insertImage.images.remove(x);
                insertImage.imageTopLeft.remove(x);
                insertImage.imageBottomRight.remove(x);
                editPage.mazeDisplay.repaint();
            }
        });

        // create show grid check box and bind event
        showGrid = UIHelper.createCheckBox("Show Grid");
        showGrid.addActionListener(e -> {
            JCheckBox src = (JCheckBox) e.getSource();
            editPage.setShowGrid(src.isSelected());
        });

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
        GridBagHelper.addToPanel(this, colorButton, gbc, 0,2,1,1);
        GridBagHelper.addToPanel(this, addImage, gbc,1,0,1,1 );
        GridBagHelper.addToPanel(this, removeImage,gbc,1,1,1,1);
    }

    public void setMaze(Maze maze) {
        maze.addListener(new Maze.MazeListener() {
            @Override
            public void solveStatusChanged(SolveStatus status) {
                switch (status) {
                    case SOLVED -> {
                        showSolution.setEnabled(true);
                        colorButton.setEnabled(true);
                    }
                    case UNSOLVED, UNSOLVABLE -> {
                        showSolution.doClick();
                        showSolution.setEnabled(false);
                        colorButton.setEnabled(false);
                    }
                }
            }
        });
    }
}
