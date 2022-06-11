package ui.pages.homepage;

import database.DatabaseConnection;
import ui.dialog.DatabaseErrorHandler;
import ui.dialog.ExportSelectedDialog;
import ui.helper.GridBagHelper;
import ui.pages.EditPage;
import ui.pages.HomePage;

import javax.sound.midi.ShortMessage;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.sql.SQLException;
import java.util.Arrays;


public class HomeButtons extends JPanel {


    private JButton exportSelected;
    private JButton deleteSelected;

    private HomePage homePage;

    public HomeButtons(JTable table, HomePage homePage) {

        Dimension dim = getPreferredSize();
        dim.height = 50;
        dim.width = 300;
        setPreferredSize(dim);

        Border innerBorder = BorderFactory.createTitledBorder("");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutComponents(table);

        this.homePage = homePage;
    }

    public void layoutComponents(JTable table) {
        exportSelected = new JButton("Export selected");
        deleteSelected = new JButton("Delete selected");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagHelper.createDefaultGBC();
        gbc.insets = new Insets(0, 0, 5, 5);

        // row 1
        GridBagHelper.addToPanel(this, exportSelected, gbc, 0, 0, 1, 1);
        GridBagHelper.addToPanel(this, deleteSelected, gbc, 2, 0, 1, 1);

        exportSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] row = table.getSelectedRows();
                if (row.length != 0) {

                    int mazeID = (int) table.getModel().getValueAt(row[row.length - 1], 0);
                    try {
                        ExportSelectedDialog.storedMazes(new DatabaseConnection().retrieveMaze(mazeID));
                        ExportSelectedDialog get = new ExportSelectedDialog(new JFrame());
                        get.setVisible(true);
                    } catch (SQLException f) {
                        DatabaseErrorHandler.handle(f, false);
                    }


                    boolean pathCheck;

                    for (int i = row.length - 1; i >= 1; i--) {
                        mazeID = (int) table.getModel().getValueAt(row[i - 1], 0);
                        try {
                            ExportSelectedDialog.storedMazes(new DatabaseConnection().retrieveMaze(mazeID));
                            if (row.length == 1) {
                                pathCheck = false;
                            } else {
                                pathCheck = true;
                            }
                            ExportSelectedDialog.afterFirst(i, pathCheck);
                        } catch (SQLException f) {
                            DatabaseErrorHandler.handle(f, false);
                        }
                    }
                }
            }
        });

        deleteSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();

                for (int i = 0; i < rows.length; i++) {
                    int mazeID = (int) table.getModel().getValueAt(rows[i], 0);
                    try {
                        new DatabaseConnection().delete(mazeID);
                    } catch (SQLException f) {
                        DatabaseErrorHandler.handle(f, false);
                    }
                }
                homePage.updateTable();
            }
        });
    }
}
