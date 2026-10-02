package ca.hccis.sugarshackboillog.bo;

import ca.hccis.sugarshackboillog.entity.BoilLog;

/**
 * Business object for a boil log.  Does the calculations for the
 * Sugar Shack Boil Log project (boil efficiency and fuel cost per litre).
 *
 * @author Nick Brown
 * @since 20261002
 */
public class BoilLogBO {

    public static final double RULE_OF_86 = 86;
    public static final double PERCENT = 100;

    public static final String FUEL_WOOD = "wood";
    public static final String FUEL_PROPANE = "propane";
    public static final String FUEL_OIL = "oil";
    public static final String FUEL_ELECTRIC = "electric";

    public static final double PRICE_WOOD = 150.00;     // per cord
    public static final double PRICE_PROPANE = 0.83;    // per litre
    public static final double PRICE_OIL = 2.10;        // per litre
    public static final double PRICE_ELECTRIC = 0.15;   // per kWh

    /**
     * Do both calculations for the boil log and store the results in the
     * boil log (boilEfficiency and costPerLitre).
     *
     * @param boilLog the boil log to calculate
     * @return the boil efficiency (%), or 0 if it can't be calculated
     * @author Nick Brown
     * @since 20261002
     */
    public static double calculate(BoilLog boilLog) {
        if (boilLog == null) {
            return 0;
        }

        boilLog.setBoilEfficiency(calculateBoilEfficiency(boilLog));
        boilLog.setCostPerLitre(calculateCostPerLitre(boilLog));

        return boilLog.getBoilEfficiency();
    }

    /**
     * Calculate the boil efficiency, which is the actual syrup produced
     * against the predicted syrup (%).  The Rule of 86 gives the litres of
     * sap needed per litre of syrup.
     *
     * @param boilLog the boil log to calculate
     * @return the boil efficiency (%), or 0 if it can't be calculated
     * @author Nick Brown
     * @since 20261002
     */
    public static double calculateBoilEfficiency(BoilLog boilLog) {
        if (boilLog == null) {
            return 0;
        }

        // These would cause a divide by zero or are not valid values
        if (boilLog.getSapBrix() <= 0 || boilLog.getSapVolume() <= 0 || boilLog.getSyrupProduced() < 0) {
            return 0;
        }

        double sapRatio = RULE_OF_86 / boilLog.getSapBrix();
        double predictedSyrup = boilLog.getSapVolume() / sapRatio;

        return boilLog.getSyrupProduced() / predictedSyrup * PERCENT;
    }

    /**
     * Calculate the fuel cost per litre of syrup produced.
     *
     * @param boilLog the boil log to calculate
     * @return the fuel cost per litre of syrup, or 0 if it can't be calculated
     * @author Nick Brown
     * @since 20261002
     */
    public static double calculateCostPerLitre(BoilLog boilLog) {
        if (boilLog == null) {
            return 0;
        }

        // These would cause a divide by zero or are not valid values
        if (boilLog.getSyrupProduced() <= 0 || boilLog.getFuelQuantity() < 0) {
            return 0;
        }

        double unitPrice = getUnitPrice(boilLog.getFuelType());
        double fuelCost = boilLog.getFuelQuantity() * unitPrice;

        return fuelCost / boilLog.getSyrupProduced();
    }

    /**
     * Look up the unit price for the fuel type.
     *
     * @param fuelType wood, propane, oil or electric
     * @return the unit price, or 0 if the fuel type is not known
     * @author Nick Brown
     * @since 20261002
     */
    private static double getUnitPrice(String fuelType) {
        if (fuelType == null) {
            return 0;
        }

        if (fuelType.equalsIgnoreCase(FUEL_WOOD)) {
            return PRICE_WOOD;
        } else if (fuelType.equalsIgnoreCase(FUEL_PROPANE)) {
            return PRICE_PROPANE;
        } else if (fuelType.equalsIgnoreCase(FUEL_OIL)) {
            return PRICE_OIL;
        } else if (fuelType.equalsIgnoreCase(FUEL_ELECTRIC)) {
            return PRICE_ELECTRIC;
        }

        return 0;
    }
}
