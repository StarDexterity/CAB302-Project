package ui.dialog;

import maze.enums.GenerationOption;
import maze.data.Maze;
import ui.pages.EditPage;

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
public class NewMazeDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;
    private JPanel myPanel;

    private JSpinner sizeX;
    private JSpinner sizeY;
    private JLabel generateLabel;
    private JComboBox generationCBox;
    private EditPage editPage;

    /**
     * The maze object instantiated by this pop up
     */
    public Maze generatedMaze;


    private String GenerateString = "Generate";
    private String CancelString = "Cancel";

    /**
     * Constructs and displays a new 'new maze' dialog that pauses the main program until it is closed.
     *
     * @param frame
     */
    public NewMazeDialog(JFrame frame) {
        super(frame);

        SpinnerNumberModel xModel = new SpinnerNumberModel(25, 4, 100, 1);
        SpinnerNumberModel yModel = new SpinnerNumberModel(25, 4, 100, 1);

        sizeX = new JSpinner(xModel);
        sizeY = new JSpinner(yModel);

        // create combobox and add options
        generationCBox = new JComboBox();
        for (GenerationOption option : GenerationOption.values()) {
            generationCBox.addItem(option.getName());
        }

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();
        row1.add(new JLabel("x:"));
        row1.add(sizeX);
        row1.add(Box.createHorizontalStrut(15)); // a spacer
        row1.add(new JLabel("y:"));
        row1.add(sizeY);

        JPanel row2 = new JPanel();
        row2.add(new Label("Generation options"));
        row2.add(generationCBox);

        myPanel.add(row1);
        myPanel.add(row2);

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {GenerateString, CancelString};


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

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                sizeX.requestFocusInWindow();
            }
        });


        setTitle("New maze");
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

            if (GenerateString.equals(value)) {
                int cols = (int) sizeX.getValue();
                int rows = (int) sizeY.getValue();
                GenerationOption option = GenerationOption.getOption((String) generationCBox.getSelectedItem());

                generatedMaze = new Maze(cols, rows, option);
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
