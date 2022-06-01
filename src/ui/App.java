package ui;

import maze.data.Maze;
import ui.dialog.NewMazeDialog;
import ui.pages.EditPage;
import ui.pages.HomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 500;

    public static final String editPageString = "EditPage";


    public EditPage editPage;
    public HomePage homePage;
    public JMenuBar menuBar;

    private String editPageID = "EditPage";
    private String homePageID = "HomePage";

    /**
     *  Stores the current maze object
     */
    public static Maze currentMaze;

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

        // create and set menubar
        menuBar = createMenu();
        setJMenuBar(menuBar);


        editPage = new EditPage(this);
        homePage = new HomePage(this);

        add(homePage, homePageID);
        add(editPage, editPageID);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setResizable(RESIZEABLE);
        setSize(WIDTH, HEIGHT);
    }

    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenuItem newMaze = new JMenuItem("New Maze");
        JMenuItem close = new JMenuItem("Home");
        JMenuItem exit = new JMenuItem("Exit");

        newMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create and display and new dialog window
                NewMazeDialog nmd = new NewMazeDialog(new JFrame());
                nmd.setLocationRelativeTo(getContentPane());
                nmd.setVisible(true);

                // if maze is null, the dialog was canceled
                Maze m = nmd.getGeneratedMaze();
                if (m != null) {
                    showEditPage(m);
                }
            }
        });

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
        mb.add(newMaze);
        mb.add(close);
        //mb.add( Box.createHorizontalStrut( 10 ) );  //this will add a 10 pixel space
        mb.add(exit);
        return mb;
    }

    /**
     * @deprecated
     */
    public void nextPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.next(c);
    }

    /**
     * @deprecated
     */
    public void lastPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.last(c);
    }

    /**
     * @deprecated
     */
    public void firstPage() {
        Container c = getContentPane();
        CardLayout cl = (CardLayout)c.getLayout();
        cl.first(c);
    }

    public void showHomePage() {
        Container c = getContentPane();
        CardLayout cardLayout = (CardLayout)c.getLayout();
        cardLayout.show(c, homePageID);
    }

    public void showEditPage(Maze maze) {
        Container c = getContentPane();
        CardLayout cardLayout = (CardLayout)c.getLayout();

        editPage.setMaze(maze);
        cardLayout.show(c, editPageID);
    }
}
