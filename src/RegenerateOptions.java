import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RegenerateOptions extends JPanel {
    private JLabel sizeLabel;
    private JTextField sizeX;
    private JTextField sizeY;
    private JLabel generateLabel;
    private JComboBox borderWall;
    private JLabel seedLabel;
    private JCheckBox seedCheck;
    private JTextField seedInput;
    private JButton generateButton;

    public RegenerateOptions() {
        sizeLabel = new JLabel("Size");
        sizeX = new JTextField("0");
        sizeY = new JTextField("0");
        generateLabel = new JLabel("Generate");
        borderWall = new JComboBox();
        seedLabel = new JLabel("Seed");
        seedCheck = new JCheckBox();
        seedInput = new JTextField("");
        generateButton = new JButton("Generate");

        Border innerBorder = BorderFactory.createTitledBorder("Regenerate menu");
        Border outerBorder = BorderFactory.createEmptyBorder(5,10,10,10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    public void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        int y = 0;

        // row 1
        GridBagHelper.addToPanel(this, sizeLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, sizeX, gbc, 1, y, 1, 1);
        GridBagHelper.addToPanel(this, sizeY, gbc, 2, y, 1, 1);

        // row 2
        y++;
        GridBagHelper.addToPanel(this, generateLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, borderWall, gbc, 2, y, 2, 1);

        // row 3
        y++;
        GridBagHelper.addToPanel(this, seedLabel, gbc, 0, y, 1, 1);
        GridBagHelper.addToPanel(this, seedCheck, gbc, 1, y, 1, 1);
        GridBagHelper.addToPanel(this, seedInput, gbc, 2, y, 1, 1);

        // row 4
        y++;
        GridBagHelper.addToPanel(this, generateButton, gbc, 2, y, 1, 1);
    }
}