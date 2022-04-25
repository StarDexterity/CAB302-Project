import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class OptionsPanel extends JPanel {

    //Display
    private JLabel solutionLabel;
    private JCheckBox showSolution;
    private JLabel gridLabel;
    private JCheckBox showGrid;

    //Regenerate
    private JLabel sizeLabel;
    private JTextField sizeX;
    private JTextField sizeY;
    private JLabel generateLabel;
    private JLabel borderWall;
    private JLabel seedLabel;
    private JCheckBox seedCheck;
    private JTextField seedInput;

    //Solve status
    private JLabel deadendLabel;
    private JLabel deadendStatus;
    private JLabel explorationLabel;
    private JLabel explorationStatus;
    private JLabel solveLabel;
    private JLabel solveStatus;
    private JButton solve;

    //Save
    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel authorLabel;
    private JTextField authorField;
    private JLabel descriptionLabel;
    private JTextField descriptionField;


    public OptionsPanel(){
        Dimension dim  = getPreferredSize();
        dim.width = 300;
        setPreferredSize(dim);

        //display
        solutionLabel = new JLabel("Show Solution");
        showSolution = new JCheckBox();
        gridLabel = new JLabel("Show Grid");
        showGrid = new JCheckBox();

        //regenerate
        sizeLabel = new JLabel("Size");
        sizeX = new JTextField("0");
        sizeY = new JTextField("0");
        generateLabel = new JLabel("Generate");
        borderWall = new JLabel("Border wall");
        seedLabel = new JLabel("Seed");
        seedCheck = new JCheckBox();
        seedInput = new JTextField("");

        //solve status
        //The status labels will need to be reactive in later stages of the project
        deadendLabel = new JLabel("Dead end %");
        deadendStatus = new JLabel("0");
        explorationLabel = new JLabel("Solution %");
        explorationStatus = new JLabel("0");
        solveLabel = new JLabel("Solve status");
        solveStatus = new JLabel("Not solved");
        solve = new JButton("Solve");


        //save
        titleLabel = new JLabel("Title");
        titleField = new JTextField("");
        authorLabel = new JLabel("Author");
        authorField = new JTextField("");
        descriptionLabel = new JLabel("Description");
        descriptionField = new JTextField("");

        Border innerBorder = BorderFactory.createTitledBorder(" Display ");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,10,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents()
    {
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        // All Field
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        // First Row
        gc.gridy = 0;


        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(solutionLabel,gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(showSolution,gc);


        // Second Row
        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(gridLabel,gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(showGrid,gc);

    }

    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h) {
        addToPanel(jp, c, constraints, x, y, w, h, null);
    }

    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h, Insets i) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        if (i != null) {
            constraints.insets = i;
        }
        jp.add(c, constraints);
    }

}