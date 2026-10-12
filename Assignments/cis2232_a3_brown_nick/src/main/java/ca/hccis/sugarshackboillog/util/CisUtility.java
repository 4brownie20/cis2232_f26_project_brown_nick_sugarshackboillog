package ca.hccis.sugarshackboillog.util;

import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Has some useful methods to be used in our programs.  A CisUtility object can
 * be set to use either the console or JOptionPane (gui) for input and output.
 *
 * @author bjmaclean / Nick Brown
 * @since 20261012
 */
public class CisUtility {

    private Scanner input = new Scanner(System.in);

    //The isGUI will be used to determine if JOptionPane is used or console
    private boolean isGUI = false;

    public CisUtility() {
        //default - console
    }

    public CisUtility(boolean isGUI) {
        this.isGUI = isGUI;
    }

    public void setIsGUI(boolean isGUI) {
        this.isGUI = isGUI;
    }

    public boolean isGUI() {
        return isGUI;
    }

    /**
     * Return the default currency String value of the double passed in as a
     * parameter.
     *
     * @param inputDouble double to be formatted
     * @return String in default currency format
     * @author BJM
     * @since 20211020
     */
    public static String toCurrency(double inputDouble) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(inputDouble);
    }

    /**
     * Prompt the user and return the value entered as a String.  Uses
     * JOptionPane if this is a gui CisUtility, otherwise the console.
     *
     * @param prompt The user prompt
     * @return The String entered by the user
     * @author BJ MacLean
     * @since 20181121
     */
    public String getInputString(String prompt) {

        String userOption;

        if (isGUI) {
            userOption = JOptionPane.showInputDialog(prompt);
        } else {
            System.out.println(prompt + " -->");
            userOption = input.nextLine();
        }

        return userOption;
    }

    /**
     * Prompt the user and return an int value.
     *
     * @param prompt The user prompt
     * @return The number entered by the user
     * @author BJ MacLean
     * @since 20181121
     */
    public int getInputInt(String prompt) {
        String enteredString = getInputString(prompt);
        int entered = Integer.parseInt(enteredString);
        return entered;
    }

    /**
     * Prompt the user and return a double value.
     *
     * @param prompt The user prompt
     * @return The number entered by the user
     * @author BJ MacLean
     * @since 20181121
     */
    public double getInputDouble(String prompt) {
        String enteredString = getInputString(prompt);
        double entered = Double.parseDouble(enteredString);
        return entered;
    }

    /**
     * Display a string for the user.  Uses JOptionPane if this is a gui
     * CisUtility, otherwise the console.
     *
     * @param output The string that will be displayed to the user
     * @author BJM
     * @since 20181115
     */
    public void display(String output) {
        if (isGUI) {
            JOptionPane.showMessageDialog(null, output);
        } else {
            System.out.println(output);
        }
    }
}
