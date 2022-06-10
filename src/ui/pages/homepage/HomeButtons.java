package ui.pages.homepage;

import database.DatabaseConnection;
import ui.dialog.DatabaseErrorHandler;
import ui.dialog.ExportDialog;
import ui.helper.GridBagHelper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class HomeButtons extends JPanel {


    private JButton exportSelected;
    private JButton deleteSelected;

    public HomeButtons(JTable table) {

        Dimension dim = getPreferredSize();
        dim.height = 50;
        dim.width = 300;
        setPreferredSize(dim);

        Border innerBorder = BorderFactory.createTitledBorder("");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutComponents(table);
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

                for (int i = row.length - 1; i >= 0; i--) {
                    int mazeID = (int) table.getModel().getValueAt(row[i], 0);
                    try {
                        ExportDialog.storedMazes(new DatabaseConnection().retrieveMaze(mazeID));
                        ExportDialog get = new ExportDialog(new JFrame());
                        get.setVisible(true);
                    } catch (SQLException f) {
                        DatabaseErrorHandler.handle(f, false);
                    }
                }



            }
        });
    }
}
