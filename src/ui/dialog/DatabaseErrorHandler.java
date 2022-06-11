package ui.dialog;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;

public class DatabaseErrorHandler {

    private static boolean isErrorIgnored = false;

    private static String formatErrorMessage(Exception e) {
        return "\n--------\nError Message: \"" + e.getMessage() + "\"";
    }

    private static Object[] options = {"Continue without Database", "Exit program"};

    public static void handle(SQLException inputError, boolean isInstantiation) {
        int choice = 0;
        try {
            if (!isErrorIgnored) {
                throw inputError;
            }
        } catch (
            SQLInvalidAuthorizationSpecException e) {
                choice = JOptionPane.showOptionDialog(new JFrame(),
                        "Access denied to maze database.\nPlease update the file 'db.props' with correct login." + formatErrorMessage(e) ,
                        "Database " + (isInstantiation ? "Instantiation" : "Connection") + " Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            } catch (
            SQLNonTransientConnectionException e) {
                choice = JOptionPane.showOptionDialog(new JFrame(),
                        "Cannot connect to maze database.\nPlease update the file 'db.props' with correct url." + formatErrorMessage(e),
                        "Database " + (isInstantiation ? "Instantiation" : "Connection") + " Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            } catch (
            SQLException e) {
                choice = JOptionPane.showOptionDialog(new JFrame(),
                        "Unknown error connecting to maze database.\nPlease contact system administrator." + formatErrorMessage(e),
                        "Database " + (isInstantiation ? "Instantiation" : "Connection") + " Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            } finally {
            if (choice == 0) {
                isErrorIgnored = true;
            }
            if (choice == 1) {
                System.exit(-105);
            }
        }

    }
}
