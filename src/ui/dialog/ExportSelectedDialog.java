package ui.dialog;

import maze.data.*;
import maze.helper.MazeSolver;
import ui.pages.EditPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import static maze.Export.exportMaze;

/**
 * Tutorial:
 * https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * Code adapted from:
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/CustomDialog.java
 */
public class ExportSelectedDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;

    private final JButton jpg;
    private final JButton png;
    private final JCheckBox showSolution;
    private final JCheckBox showGrid;
    private final JLabel labelSolution;
    private final JLabel labelGrid;
    private final JButton colorButton;

    private boolean ifSolution = false;
    private boolean ifGrid = false;
    private Color colour = Color.ORANGE;

    private static boolean ifSolutionMulti = false;
    private static boolean ifGridMulti = false;
    private static Color colourMulti = Color.ORANGE;

    public EditPage editPage;

    private static Maze current;


    /**
     * The maze object instantiated by this pop up
     */
    public Maze generatedMaze;

    private String CancelString = "Cancel";

    public static void storedMazes(Maze maze){
        current = maze;
    }
    public static String imageType;

    /**
     * Constructs and displays a new 'new maze' dialog that pauses the main program until it is closed.
     *
     * @param frame
     */
    public ExportSelectedDialog(JFrame frame) {
        super(frame);

        jpg = new JButton("JPG");
        png = new JButton("PNG");
        showSolution = new JCheckBox();
        showGrid = new JCheckBox();
        labelSolution = new JLabel(" Show solution:");
        labelGrid = new JLabel(" Show grid:");
        colorButton = new JButton("Solution line colour");

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();

        row2.add(jpg);
        row2.add(Box.createHorizontalStrut(15)); // a spacer
        row2.add(png);

        row1.add(labelSolution);
        row1.add(showSolution);
        row1.add(labelGrid);
        row1.add(showGrid);
        row1.add(colorButton);

        myPanel.add(row1);
        myPanel.add(row2);


        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {CancelString};

        //Create the JOptionPane
        optionPane = new JOptionPane(myPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        setTitle("Export selected");
        setResizable(false);


        // freeze application
        setModal(true);


        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);

        // fit to content
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void afterFirst(int number, boolean pathCheck) {
        String imageName = "MazeImage" + (number + "");
        if (ifSolutionMulti == true) {
            ifSolutionMulti = MazeSolver.solve(current);
        }

        try {
            if (current != null) {
                exportMaze(current, pathCheck, ifSolutionMulti, ifGridMulti, imageType, imageName, colourMulti);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }    }

    /**
     * This method checks if any of the image buttons have been selected.
     */
    public void propertyChange(PropertyChangeEvent e) {

        showSolution.addActionListener(f -> {
            if (showSolution.isSelected()) {
                ifSolution = MazeSolver.solve(current);
                ifSolutionMulti = true;
            }
            else {
                ifSolution = false;
                ifSolutionMulti = false;
            }
        });

        showGrid.addActionListener(f -> {
            if (showGrid.isSelected()) {
                ifGrid = true;
                ifGridMulti = true;
            }
            else {
                ifGrid = false;
                ifGridMulti = false;
            }
        });

        colorButton.addActionListener(f -> {
            Color changeColor = JColorChooser.showDialog(null, "Change Color",Color.RED);
            if (changeColor != null){
                colour = changeColor;
                colourMulti = changeColor;
            }
        });

        jpg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageType = "jpg";
                try {
                    if (current != null) {
                        exportMaze(current, false, ifSolution, ifGrid, "jpg", " ", colour);
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                clearAndHide();
            }
        });

        png.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageType = "png";
                try {
                    if (current != null) {
                        exportMaze(current, false, ifSolution, ifGrid, "png", " ", colour);
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                clearAndHide();
            }
        });

        clearAndHide();

        //popup menu activates here printing "Export successful"

    }


    /**
     * This method clears the dialog and hides it.
     */
    public void clearAndHide() {
        setVisible(false);
    }
}
