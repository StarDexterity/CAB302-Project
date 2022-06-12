package maze.data;

import javax.swing.table.AbstractTableModel;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * This class allows easy editing of the maze table, found on the home page in the application
 */
public class MazeTableModel extends AbstractTableModel {
    public MazeTableModel() {
        super();
        data = new ArrayList<MazeData>();
    }

    /**
     * Adds a row of data to the table
     * @param mazeData The data of a maze to be added to the table
     */
    public void addRow(MazeData mazeData) {
        data.add(mazeData);
        fireTableDataChanged();
    }

    /**
     * Adds multiple rows of data to the table
     * @param data The ArrayList of data to  be added to the table
     */
    public void addRows(ArrayList<MazeData> data) {
        for (MazeData datum : data) {
            addRow(datum);
        }
    }

    /**
     * Used to clear the data held within the table
     */
    public void clear() {
        data.clear();
    }

    /**
     * An enum to specify the names of the headers of the table
     */
    public enum TableHeaders {
        ID(0, "Id"),
        TITLE(1,"Title"),
        AUTHOR(2,"Author"),
        LAST_EDITED(3, "Last edited on"),
        CREATED(4,"Created on");

        private String name;
        private int id;

        TableHeaders(int id, String name) {
            this.id = id;
            this.name = name;
        }



        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static TableHeaders getById(int id) {
            for (TableHeaders header : TableHeaders.values()) {
                if (header.getId() == id) return header;
            }
            return null;
        }
    }


    // a lot of data to show the scroll bar
    private ArrayList<MazeData> data;

    public ArrayList<MazeData> getData() {
        return data;
    }

    public int getColumnCount() {
        return TableHeaders.values().length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return TableHeaders.getById(col).getName();
    }

    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss z");

    public Object getValueAt(int row, int col) {
        MazeData record = data.get(row);

        switch(TableHeaders.getById(col)) {
            case ID -> {
                return record.getId();
            }
            case TITLE -> {
                return record.getTitle();
            }
            case AUTHOR -> {
                return record.getAuthor();
            }
            case LAST_EDITED -> {
                return record.getLastEditDate().atZone(ZoneId.systemDefault()).format(dateTimeFormat);
            }
            case CREATED -> {
                return record.getCreationDate().atZone(ZoneId.systemDefault()).format(dateTimeFormat);
            }
            default -> {
                return null;
            }
        }
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        if (c == 0) return Integer.class;
        return String.class;
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {}
}
