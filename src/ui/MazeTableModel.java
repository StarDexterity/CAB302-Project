package ui;

import javax.swing.table.AbstractTableModel;


public class MazeTableModel extends AbstractTableModel {
    public MazeTableModel() {
        super();
    }
    private String[] columnNames = {"Title", "Author", "Last edited", "select"};

    // a lot of data to show the scroll bar
    private Object[][] data = {
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false},
            {"Best Default.Maze", "Mr Default.Maze", "22/04/2022", false}
    };

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return (col == 3);
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}