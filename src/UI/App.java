package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 500;

    public EditPage editPage;
    public HomePage homePage;
    public JMenuBar menuBar;

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
        setLayout(new BorderLayout());

        // create and set menubar
        menuBar = createMenu();
        setJMenuBar(menuBar);


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

    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem close = new JMenuItem("Close maze ");
        JMenuItem exit = new JMenuItem("Exit");

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstPage();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(69);
            }
        });

        mb.add(file);
        file.add(close);
        file.addSeparator();
        file.add(exit);
        return mb;
    }

    public void nextPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.next(c);
    }

    public void lastPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.last(c);
    }

    public void firstPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.first(c);
    }
}
