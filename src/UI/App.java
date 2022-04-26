package UI;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 500;

    public EditPage editPage;
    public HomePage homePage;

    // may change in the future
    public static final boolean RESIZEABLE = false;

    public App(String s) {
        super(s);
        initialize();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new App("Amazing"));
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        editPage = new EditPage(this);
        homePage = new HomePage(this);

        add(homePage);
        add(editPage);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setResizable(RESIZEABLE);
        setSize(WIDTH,HEIGHT);
    }

    public void nextPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.next(c);
    }
}













