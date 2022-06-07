package ui.dialog;

import maze.data.Maze;
import maze.enums.GenerationOption;
import ui.pages.EditPage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import static maze.Export.displayMaze;

/**
 * Tutorial:
 * https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * Code adapted from:
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/CustomDialog.java
 */
public class ExportDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;
    private JPanel myPanel;

    private final JButton jpg;
    private final JButton png;
    public EditPage editPage;

    private static Maze current;


    /**
     * The maze object instantiated by this pop up
     */
    public Maze generatedMaze;

    //private String ExportString = "Export";
    private String CancelString = "Cancel";

    public static void storedMazes(Maze maze){
        current = maze;
    }

    /**
     * Constructs and displays a new 'new maze' dialog that pauses the main program until it is closed.
     *
     * @param frame
     */
    public ExportDialog(JFrame frame) {
        super(frame);

        jpg = new JButton("JPG");
        png = new JButton("PNG");


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();

        row1.add(jpg);
        row1.add(Box.createHorizontalStrut(15)); // a spacer
        row1.add(png);

        myPanel.add(row1);


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

    /**
     * This method checks if any of the image buttons have been selected.
     */
    public void propertyChange(PropertyChangeEvent e) {

        jpg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (current != null) {
                        displayMaze(current, "jpg");
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                clearAndHide();
            }
        });

        //export mazes (if multiple selected export all in for loop)
        png.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (current != null) {
                        displayMaze(current, "png");
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
