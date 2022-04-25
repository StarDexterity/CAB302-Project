import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SolveStatus extends JPanel {
    private JLabel deadendLabel;
    private JLabel deadendStatus;
    private JLabel explorationLabel;
    private JLabel explorationStatus;
    private JLabel solveLabel;
    private JLabel solveStatus;
    private JButton solve;

    public SolveStatus() {
        //The status labels will need to be reactive in later stages of the project
        deadendLabel = new JLabel("Dead end %");
        deadendStatus = new JLabel("0");
        explorationLabel = new JLabel("Solution %");
        explorationStatus = new JLabel("0");
        solveLabel = new JLabel("Solve status");
        solveStatus = new JLabel("Not solved");
        solve = new JButton("Solve");

        Border innerBorder = BorderFactory.createTitledBorder("Solve status");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,10,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        int y = 0;

        // row 1
        GridBagHelper.addToPanel(this, deadendLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, deadendStatus, gbc, 2, y, 1, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, explorationLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, explorationStatus, gbc, 2, y, 1, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, solveLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, solveStatus, gbc, 2, y, 1, 1);

        // row 4
        y++;
        GridBagHelper.addToPanel(this, solve, gbc, 2, y, 1, 1);
    }
}