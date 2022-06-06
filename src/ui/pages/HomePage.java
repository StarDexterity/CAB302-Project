package ui.pages;

import maze.data.MazeData;
import maze.enums.GenerationOption;
import maze.data.Maze;
import ui.App;
import maze.data.MazeTableModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static maze.Export.displayMaze;

public class HomePage extends JPanel {
    private App app;

    private JScrollPane scrollPane;
    private JTable table;
    private JPopupMenu popupMenu;

    public EditPage editPage;


    public HomePage(App app) {
        super();
        this.app = app;
        createGUI();
    }

    private void createGUI() {
        new JLabel("testing");

        setLayout(new BorderLayout());

        MazeTableModel mazeTableModel = new MazeTableModel();
        table = new JTable(mazeTableModel);
        getMazes();

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFocusable(false);
        table.setRowHeight(32);
        table.setAutoCreateRowSorter(true);

        // double click event that opens up the maze for editing
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                // Point point = mouseEvent.getPoint(); // this code seems redundant
                //int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // placeholder maze gets displayed to user
                    // TODO: Pass in maze linked to the selected row
                    app.showEditPage(new Maze(10, 10, GenerationOption.DFS));
                }
            }
        });

        // manually set column size
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(170);
        columnModel.getColumn(1).setPreferredWidth(170);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(1);


        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);


        popupMenu = createPopupMenu(table);
        table.setComponentPopupMenu(popupMenu);

        // border code
        Border innerBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        Border outerBorder = BorderFactory.createEmptyBorder(120,100,100,100);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        // layout code
        add(scrollPane, BorderLayout.CENTER);
    }

    private void getMazes() {
        MazeTableModel tableModel = (MazeTableModel) table.getModel();
        tableModel.clear();

        tableModel.addRows(getDummyMazeData());
    }

    private ArrayList<MazeData> getDummyMazeData() {
        int id = 0;
        ArrayList<MazeData> data = new ArrayList<>();

        String[] authors = {"Dave", "Jane", "Richard", "Mary", "Sally", "Bob"};
        String[] titles = {"Cool maze", "Great maze", "Amazing maze", "Bad maze", "Test maze", ":("};

        for (int i = 0; i < 6; i++) {
            data.add(new MazeData(id, authors[i], titles[i], "", Instant.now(), Instant.now()));
            id++;
        }
        return data;
    }

    private JPopupMenu createPopupMenu(JTable table) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.showEditPage(new Maze(10, 10, GenerationOption.DFS));
            }
        });

        popupMenu.add(exportItem);
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);


        //DOES NOT WORK YET
        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("testing button");
                try {
                    displayMaze(editPage.currentMaze);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        // this code automatically selects row when popup menu is opened
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }
        });
        return popupMenu;
    }
}

