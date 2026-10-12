package ca.hccis.sugarshackboillog.threads;

import ca.hccis.sugarshackboillog.bo.BoilLogBO;
import ca.hccis.sugarshackboillog.entity.BoilLog;
import ca.hccis.sugarshackboillog.util.CisUtility;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Thread that runs the boil log menu.  The CisUtility object passed in decides
 * if this thread works in the console or with JOptionPane (gui).
 *
 * @author Nick Brown
 * @since 20261012
 */
public class ThreadBoilLog extends Thread {

    public static final String ADD = "A";
    public static final String VIEW = "V";
    public static final String EXIT = "X";

    public static final String MENU = ADD + ") Add a boil" + System.lineSeparator()
            + VIEW + ") View boils" + System.lineSeparator()
            + EXIT + ") eXit";

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String PATH = "c:\\cis2232\\";
    public static final String FILE_NAME = "data_brown_nick.json";

    //**************************************************************************
    //Accumulator - static so it is shared by both threads.  Holds the number of
    //boils added from the console thread and the gui thread combined.
    //**************************************************************************
    private static int boilsAdded = 0;

    private CisUtility cisUtility = null;

    public ThreadBoilLog() {
        this.cisUtility = new CisUtility();
    }

    public ThreadBoilLog(CisUtility cisUtility) {
        this.cisUtility = cisUtility;
    }

    public void setCisUtility(CisUtility cisUtility) {
        this.cisUtility = cisUtility;
    }

    /**
     * Show the menu until the user chooses to exit.  This is the code that
     * runs when the thread is started.
     *
     * @author Nick Brown
     * @since 20261012
     */
    @Override
    public void run() {

        String menuOption;

        do {
            menuOption = cisUtility.getInputString(MENU);

            // Cancel or closing the window in JOptionPane gives null, treat it as exit
            if (menuOption == null) {
                menuOption = EXIT;
            }

            if (menuOption.equalsIgnoreCase(ADD)) {
                processAdd();
            } else if (menuOption.equalsIgnoreCase(VIEW)) {
                processView();
            } else if (menuOption.equalsIgnoreCase(EXIT)) {
                cisUtility.display(MESSAGE_EXIT);
            } else {
                cisUtility.display(MESSAGE_ERROR);
            }
        } while (!menuOption.equalsIgnoreCase(EXIT));
    }

    /**
     * Get a new boil log from the user, calculate it, save it to the file and
     * show the accumulated number of boils added from both threads.
     *
     * @author Nick Brown
     * @since 20261012
     */
    public void processAdd() {

        BoilLog boilLog = new BoilLog(cisUtility);
        boilLog.getInformation();

        // Calculate the boil efficiency and cost per litre before saving
        BoilLogBO.calculate(boilLog);

        int total = saveBoilLog(boilLog);

        cisUtility.display("Boil saved." + System.lineSeparator()
                + "Boils added this session (console and gui combined): " + total);
    }

    /**
     * Write the boil log to the file and update the accumulator.  It is
     * synchronized so only one thread at a time can write to the file and
     * update the count.  The file is opened and closed each time so it is not
     * locked by one of the threads.
     *
     * @param boilLog the boil log to save
     * @return the number of boils added from both threads
     * @author Nick Brown
     * @since 20261012
     */
    private static synchronized int saveBoilLog(BoilLog boilLog) {
        try {
            FileWriter boilWriter = new FileWriter(PATH + FILE_NAME, true);
            boilWriter.write(boilLog.toJson() + System.lineSeparator());
            boilWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            throw new RuntimeException(e);
        }

        boilsAdded++;
        return boilsAdded;
    }

    /**
     * Read the boil logs from the file and show them.  One string is built so
     * the gui shows them all in one window.
     *
     * @author Nick Brown
     * @since 20261012
     */
    public void processView() {
        Gson gson = new Gson();
        Path filePath = Paths.get(PATH + FILE_NAME);
        String output = "";

        try {
            if (!Files.exists(filePath)) {
                output = "No boils found";
            } else {
                List<String> lines = Files.readAllLines(filePath);
                if (lines.isEmpty()) {
                    output = "No boils found";
                } else {
                    output = "Here are the boils found (" + lines.size() + " in the file)"
                            + System.lineSeparator() + System.lineSeparator();
                    for (String current : lines) {
                        BoilLog boilLog = gson.fromJson(current, BoilLog.class);
                        output += boilLog.toString() + System.lineSeparator() + System.lineSeparator();
                    }
                }
            }
            cisUtility.display(output);

        } catch (IOException e) {
            cisUtility.display("Error reading file");
            throw new RuntimeException(e);
        }
    }
}
