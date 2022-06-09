package ui.dialog;

import maze.data.Maze;
import maze.enums.GenerationOption;
import ui.pages.EditPage;
import ui.pages.editpage.MazeDisplay;
import ui.pages.editpage.options.image.InsertImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Tutorial:
 * https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * Code adapted from:
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/CustomDialog.java
 */
public class SaveDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;

    private JPanel myPanel;

    private JButton save;

    /**
     * The maze object instantiated by this pop up
     */
    public Maze generatedMaze;


    private String SaveString = "Save";
    private String CancelString = "Cancel";

    /**
     * Displays a dialog offering to be able to save an image
     *
     * @param frame
     */
    public SaveDialog(JFrame frame) {
        super(frame);

        save = new JButton("Save");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel row1 = new JPanel();
        row1.add(save);

        panel.add(row1);

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {SaveString, CancelString};


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


        setTitle("Save Maze");
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
     * This method reacts to state changes in the option pane.
     */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();


            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if ("Save".equals(value)) {
                System.out.println("Lets Get Saving");
                clearAndHide();
            }
         else { //user closed dialog or clicked cancel
                clearAndHide();
            }
        }

    }

    public Maze getGeneratedMaze() {
        return generatedMaze;
    }


    /**
     * This method clears the dialog and hides it.
     */
    public void clearAndHide() {
        setVisible(false);
    }
}
