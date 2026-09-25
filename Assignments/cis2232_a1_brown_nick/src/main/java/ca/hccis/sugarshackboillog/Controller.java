package ca.hccis.sugarshackboillog;

import ca.hccis.sugarshackboillog.entity.BoilLog;
import ca.hccis.sugarshackboillog.util.CisUtility;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controls the overall flow of the Sugar Shack Boil Log console application.
 *
 * @author Nick Brown
 * @since 2026-09-24
 */
public class Controller {

    public static final String ADD = "A";
    public static final String VIEW = "V";
    public static final String EXIT = "X";

    public static final String MENU = ADD + ") Add a boil" + System.lineSeparator()
            + VIEW + ") View boils" + System.lineSeparator()
            + EXIT + ") eXit"
            + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String PATH = "c:\\cis2232\\";
    public static final String FILE_NAME = "data_brown_nick.json";
    private static Path folderPath = null;
    private static FileWriter boilWriter = null;

    /**
     * Main method. Creates the folder if needed, then shows the menu
     * until the user chooses to exit.
     *
     * @param args not used
     * @author Nick Brown
     * @since 2026-09-24
     */
    public static void main(String[] args) {

        // Create the folder if it doesn't already exist
        folderPath = Paths.get(PATH);
        if (!Files.exists(folderPath)) {
            File folder = new File(PATH);
            folder.mkdirs();
        }

        // Open the file for adding to (true = append, so existing boils are kept)
        try {
            boilWriter = new FileWriter(PATH + FILE_NAME, true);
        } catch (IOException e) {
            System.out.println("Error creating file writer");
            throw new RuntimeException(e);
        }

        String menuOption;

        do {
            menuOption = CisUtility.getInputString(MENU);

            if (menuOption.equalsIgnoreCase(ADD)) {
                processAdd();
            } else if (menuOption.equalsIgnoreCase(VIEW)) {
                processView();
            } else if (menuOption.equalsIgnoreCase(EXIT)) {
                System.out.println(MESSAGE_EXIT);
            } else {
                System.out.println(MESSAGE_ERROR);
            }
        } while (!menuOption.equalsIgnoreCase(EXIT));

        try {
            boilWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a new boil log from the user and save it to the file as json.
     *
     * @author Nick Brown
     * @since 2026-09-24
     */
    public static void processAdd() {

        BoilLog boilLog = new BoilLog();
        boilLog.getInformation();

        try {
            String jsonValue = boilLog.toJson();
            boilWriter.write(jsonValue + System.lineSeparator());
            boilWriter.flush();
            System.out.println("Boil saved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read the boil logs from the file and show them.
     *
     * @author Nick Brown
     * @since 2026-09-24
     */
    public static void processView() {
        System.out.println();
        Gson gson = new Gson();
        try {
            List<String> lines = Files.readAllLines(Paths.get(PATH + FILE_NAME));
            if (lines.isEmpty()) {
                System.out.println("No boils found");
            } else {
                System.out.println("Here are the boils found");
                for (String current : lines) {
                    BoilLog boilLog = gson.fromJson(current, BoilLog.class);
                    System.out.println(boilLog.toString());
                    System.out.println();
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
            throw new RuntimeException(e);
        }
    }
}
