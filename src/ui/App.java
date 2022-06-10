package ui;

import database.DatabaseConnection;
import maze.data.Maze;
import maze.enums.SelectionType;
import ui.dialog.DatabaseErrorHandler;

import ui.dialog.NewMazeDialog;
import ui.dialog.SaveDialog;
import ui.dialog.ExportDialog;

import ui.pages.EditPage;
import ui.pages.HomePage;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import static maze.Export.displayMaze;

public class App extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;

    public static final int MIN_WIDTH = 1000;
    public static final int MIN_HEIGHT = 500;



    public EditPage editPage;
    public HomePage homePage;
    public JMenuBar menuBar;

    private final String editPageID = "EditPage";
    private final String homePageID = "HomePage";

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

        try {
            DatabaseConnection.instantiate();
        } catch (SQLException e) {
            DatabaseErrorHandler.handle(e, true);
        }

        SwingUtilities.invokeLater(() -> new App("Amazing"));
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());



        editPage = new EditPage();
        homePage = new HomePage(this);

        // create and set menubar
        menuBar = createMenu();
        setJMenuBar(menuBar);

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
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu(("Edit"));

        JMenuItem newMaze = new JMenuItem("New Maze");

        JMenuItem save = new JMenuItem("Save");
        save.setEnabled(false);
        editPage.mazeDisplay.addListener(cce -> {
            save.setEnabled(editPage.mazeDisplay.isEnabled());
        });

        JMenuItem export = new JMenuItem("Export");
        export.setEnabled(false);
        editPage.mazeDisplay.addListener(cce -> {
            export.setEnabled(editPage.mazeDisplay.isEnabled());
        });

        JMenuItem close = new JMenuItem("Home");
        JMenuItem exit = new JMenuItem("Exit");

        JMenuItem deselect = new JMenuItem("Deselect");
        deselect.setEnabled(false);
        deselect.addActionListener(e -> {
            editPage.mazeDisplay.deselect();
        });

        editPage.mazeDisplay.addListener(cce -> {
            deselect.setEnabled(cce.selectionType != SelectionType.NONE);
        });

        JMenuItem insert = new JMenuItem("Insert Image");

        newMaze.addActionListener(e -> {
            // create and display and new dialog window
            NewMazeDialog nmd = new NewMazeDialog(new JFrame());
            nmd.setLocationRelativeTo(getContentPane());
            nmd.setVisible(true);

            // if maze is null, the dialog was canceled
            Maze m = nmd.getGeneratedMaze();
            if (m != null) {
                showEditPage(m);
            }
        });

        save.addActionListener(e -> {
            SaveDialog saveDialog = new SaveDialog(new JFrame());
            saveDialog.setLocationRelativeTo(getContentPane());
            saveDialog.setVisible(true);
        });

        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create and display and new dialog window
                Maze m = editPage.currentMaze;
                ExportDialog.storedMazes(m);

                ExportDialog get = new ExportDialog(new JFrame());
                get.setLocationRelativeTo(getContentPane());
                get.setVisible(true);

            }
        });

        close.addActionListener(e -> showHomePage());

        exit.addActionListener(e -> System.exit(69));

        file.add(newMaze);
        file.add(save);
        file.add(export);
        file.add(close);
        file.addSeparator();
        file.add(exit);

        edit.add(deselect);

        mb.add(file);
        mb.add(edit);

        return mb;
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
