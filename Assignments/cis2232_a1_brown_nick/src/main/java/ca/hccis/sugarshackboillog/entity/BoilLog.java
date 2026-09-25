package ca.hccis.sugarshackboillog.entity;

import ca.hccis.sugarshackboillog.util.CisUtility;
import com.google.gson.Gson;

/**
 * Represents one boil session at the sugar shack.
 *
 * @author Nick Brown
 * @since 2026-09-24
 */
public class BoilLog {

    private int boilId;              // Unique id for the boil
    private String boilDate;         // Date of the boil (yyyy-MM-dd)
    private int tapCount;            // Number of taps
    private double sapVolume;        // Litres of sap boiled
    private double sapBrix;          // Sugar content of the sap (Brix)
    private String boilStartTime;    // Time the fire was lit (hh:mm)
    private String boilEndTime;      // Time the last batch came off (hh:mm)
    private String fuelType;         // Wood, propane, oil, electric
    private double fuelQuantity;     // Fuel burned, in the unit for the fuel type
    private double syrupProduced;    // Litres of finished syrup

    // Calculated fields - not calculated in Assignment 1 (done in the unit testing assignment)
    private double boilEfficiency;   // Actual yield vs predicted yield (%)
    private double costPerLitre;     // Fuel cost per litre of syrup

    /**
     * Prompt the user for the boil details and store them in this object.
     * The calculated fields are not set here.
     *
     * @author Nick Brown
     * @since 2026-09-24
     */
    public void getInformation() {
        boilId = CisUtility.getInputInt("Enter boil id: ");
        boilDate = CisUtility.getInputString("Enter boil date (yyyy-MM-dd): ");
        tapCount = CisUtility.getInputInt("Enter number of taps: ");
        sapVolume = CisUtility.getInputDouble("Enter sap volume (litres): ");
        sapBrix = CisUtility.getInputDouble("Enter sap Brix: ");
        boilStartTime = CisUtility.getInputString("Enter boil start time (hh:mm): ");
        boilEndTime = CisUtility.getInputString("Enter boil end time (hh:mm): ");
        fuelType = CisUtility.getInputString("Enter fuel type (wood, propane, oil, electric): ");
        fuelQuantity = CisUtility.getInputDouble("Enter fuel quantity: ");
        syrupProduced = CisUtility.getInputDouble("Enter syrup produced (litres): ");
    }

    /**
     * Convert this boil log to a JSON string.
     *
     * @return JSON representation of this object
     * @author Nick Brown
     * @since 2026-09-24
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getBoilId() {
        return boilId;
    }

    public void setBoilId(int boilId) {
        this.boilId = boilId;
    }

    public String getBoilDate() {
        return boilDate;
    }

    public void setBoilDate(String boilDate) {
        this.boilDate = boilDate;
    }

    public int getTapCount() {
        return tapCount;
    }

    public void setTapCount(int tapCount) {
        this.tapCount = tapCount;
    }

    public double getSapVolume() {
        return sapVolume;
    }

    public void setSapVolume(double sapVolume) {
        this.sapVolume = sapVolume;
    }

    public double getSapBrix() {
        return sapBrix;
    }

    public void setSapBrix(double sapBrix) {
        this.sapBrix = sapBrix;
    }

    public String getBoilStartTime() {
        return boilStartTime;
    }

    public void setBoilStartTime(String boilStartTime) {
        this.boilStartTime = boilStartTime;
    }

    public String getBoilEndTime() {
        return boilEndTime;
    }

    public void setBoilEndTime(String boilEndTime) {
        this.boilEndTime = boilEndTime;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getFuelQuantity() {
        return fuelQuantity;
    }

    public void setFuelQuantity(double fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    public double getSyrupProduced() {
        return syrupProduced;
    }

    public void setSyrupProduced(double syrupProduced) {
        this.syrupProduced = syrupProduced;
    }

    public double getBoilEfficiency() {
        return boilEfficiency;
    }

    public void setBoilEfficiency(double boilEfficiency) {
        this.boilEfficiency = boilEfficiency;
    }

    public double getCostPerLitre() {
        return costPerLitre;
    }

    public void setCostPerLitre(double costPerLitre) {
        this.costPerLitre = costPerLitre;
    }

    /**
     * Show all of the boil details, one per line.
     *
     * @return the boil details as a String
     * @author Nick Brown
     * @since 2026-09-24
     */
    @Override
    public String toString() {
        return "Boil Id: " + boilId
                + System.lineSeparator() + "Boil Date: " + boilDate
                + System.lineSeparator() + "Tap Count: " + tapCount
                + System.lineSeparator() + "Sap Volume (L): " + sapVolume
                + System.lineSeparator() + "Sap Brix: " + sapBrix
                + System.lineSeparator() + "Boil Start Time: " + boilStartTime
                + System.lineSeparator() + "Boil End Time: " + boilEndTime
                + System.lineSeparator() + "Fuel Type: " + fuelType
                + System.lineSeparator() + "Fuel Quantity: " + fuelQuantity
                + System.lineSeparator() + "Syrup Produced (L): " + syrupProduced
                + System.lineSeparator() + "Boil Efficiency (%): " + boilEfficiency
                + System.lineSeparator() + "Cost Per Litre: " + CisUtility.toCurrency(costPerLitre);
    }
}
