package ca.hccis.sugarshackboillog;

import ca.hccis.sugarshackboillog.threads.ThreadBoilLog;
import ca.hccis.sugarshackboillog.util.CisUtility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controls the overall flow of the Sugar Shack Boil Log application.  Starts
 * two threads:  one that works in the console and one that uses JOptionPane.
 *
 * @author Nick Brown
 * @since 2026-09-24
 */
public class Controller {

    /**
     * Main method.  Creates the folder if needed, then starts the console
     * thread and the gui thread.
     *
     * @param args not used
     * @author Nick Brown
     * @since 20261012
     */
    public static void main(String[] args) {

        // Create the folder if it doesn't already exist
        Path folderPath = Paths.get(ThreadBoilLog.PATH);
        if (!Files.exists(folderPath)) {
            File folder = new File(ThreadBoilLog.PATH);
            folder.mkdirs();
        }

        Thread threadConsole = new ThreadBoilLog(new CisUtility(false));
        Thread threadGUI = new ThreadBoilLog(new CisUtility(true));

        threadConsole.start();
        threadGUI.start();
    }
}
