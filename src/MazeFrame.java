import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 500;

    // may change in the future
    public static final boolean RESIZEABLE = false;

    private OptionsPanel optionsPanel;

    public MazeFrame(String s) {
        super(s);

        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        Container c = this.getContentPane();
        optionsPanel = new OptionsPanel();



        // layout code
        c.add(optionsPanel, BorderLayout.WEST);
        c.add(placeHolder(), BorderLayout.CENTER);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setResizable(RESIZEABLE);
        setSize(WIDTH,HEIGHT);
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

