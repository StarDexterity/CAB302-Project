package ui.dialog;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;

public class DatabaseErrorHandler {
    private static String formatErrorMessage(Exception e) {
        return "\n--------\nError Message: \"" + e.getMessage() + "\"";
    }

    public static void handle(SQLException inputError, boolean isInstantiation) {
        try {
            throw inputError;
        } catch (
            SQLInvalidAuthorizationSpecException e) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Access denied to maze database.\nPlease update the file 'db.props' with correct login." + formatErrorMessage(e) ,
                        "Database " + (isInstantiation ? "Instantiation" : "Connection") + " Error", JOptionPane.ERROR_MESSAGE);
            } catch (
            SQLNonTransientConnectionException e) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Cannot connect to maze database.\nPlease update the file 'db.props' with correct url." + formatErrorMessage(e),
                        "Database " + (isInstantiation ? "Instantiation" : "Connection") + " Error", JOptionPane.ERROR_MESSAGE);
            } catch (
            SQLException e) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Unknown error connecting to maze database.\nPlease contact system administrator." + formatErrorMessage(e),
                        "Database " + (isInstantiation ? "Instantiation" : "Connection") + " Error", JOptionPane.ERROR_MESSAGE);
            }
    }
}
