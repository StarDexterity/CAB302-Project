package UI;

import javax.swing.*;
import java.awt.*;

public class EditPage extends JPanel {
    private App app;
    private OptionsPanel optionsPanel;

    public EditPage(App app) {
        super();
        this.app = app;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        optionsPanel = new OptionsPanel();

        // layout code
        add(optionsPanel, BorderLayout.WEST);
        add(placeHolder(), BorderLayout.CENTER);
    }

    /**
     * Creates a placeholder panel for the center
     * @return
     */
    private JPanel placeHolder() {
        // create the placeholder panel, with GridBagLayout and an empty border
        JPanel placeHolder = new JPanel();
        placeHolder.setLayout(new GridBagLayout());
        placeHolder.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // grid bag constraints
        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();

        // create inner white panel
        JPanel white = new JPanel();
        white.setBackground(Color.WHITE);
        GridBagHelper.addToPanel(placeHolder, white, gbc, 0, 0, 1, 1);
        return placeHolder;
    }
}

