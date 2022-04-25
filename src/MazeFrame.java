import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 500;

    private JPanel optionsPanel;

    public MazeFrame(String s) {
        super(s);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        createGUI();
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // panel related code will go here
        Container c = this.getContentPane();

        OptionsPanel optionsPanel = new OptionsPanel();


        c.add(optionsPanel, BorderLayout.WEST);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(WIDTH,HEIGHT);
    }
}

