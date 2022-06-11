package ui.dialog;

import database.DatabaseConnection;
import maze.data.Maze;
import ui.helper.GridBagHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

/**
 * Tutorial:
 * https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * Code adapted from:
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/CustomDialog.java
 */
public class SaveDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;

    private JPanel myPanel;

    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel authorLabel;
    private JTextField authorField;
    private JLabel descriptionLabel;
    private JTextArea descriptionField;
    private JScrollPane descriptionFieldScroller;


    private String SaveString = "Save";
    private String CancelString = "Cancel";

    /**
     * Maze object to save
     */
    private Maze maze;

    /**
     * Displays a dialog offering to be able to save a maze
     *
     * @param frame
     */
    public SaveDialog(JFrame frame) {
        super(frame);

        setSize(400, 300);
        myPanel = new JPanel();
        titleLabel = new JLabel("Title");
        titleField = new JTextField("");
        authorLabel = new JLabel("Author");
        authorField = new JTextField("");
        descriptionLabel = new JLabel("Description");
        descriptionField = new JTextArea("");
        descriptionFieldScroller = new JScrollPane(descriptionField);
        descriptionField.setRows(4);

        layoutComponents();

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {SaveString, CancelString};


        // Create the JOptionPane
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

    public void layoutComponents() {
        myPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        gbc.insets = new Insets(0, 0, 5, 5);
        int y = 0;

        // row 1
        GridBagHelper.addToPanel(myPanel, titleLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(myPanel, titleField, gbc, 1, y, 2, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(myPanel, authorLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(myPanel, authorField, gbc, 1, y, 2, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(myPanel, descriptionLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(myPanel, descriptionFieldScroller, gbc, 1, y, 2, 1);

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
                // Sets the data from the JTextField into the Maze object.
                maze.setData(authorField.getText())
                        .title(titleField.getText())
                        .description(descriptionField.getText());



                // Saves the maze to the database.
                try {
                    new DatabaseConnection().save(maze);
                } catch (SQLException ex) {
                    DatabaseErrorHandler.handle(ex, false);
                } finally {
                    clearAndHide();
                }
            }
         else { //user closed dialog or clicked cancel
                clearAndHide();
            }
        }

    }


    /**
     * This method clears the dialog and hides it.
     */
    public void clearAndHide() {
        setVisible(false);
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }
}
