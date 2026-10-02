package ca.hccis.sugarshackboillog.bo;

import ca.hccis.sugarshackboillog.entity.BoilLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the BoilLogBO class.
 *
 * @author Nick Brown
 * @since 20261002
 */
class BoilLogBOTest {

    public static final double DELTA = 0.01;

    /**
     * Test 1 created following a test driven development (TDD) approach.
     * This test was written first, then calculateBoilEfficiency was coded to make it pass.
     * Uses the example from the project readme:  800 L of sap at 2.0 Brix
     * and 17.5 L of syrup gives a boil efficiency of 94.06%.
     *
     * @author Nick Brown
     * @since 20261002
     */
    @Test
    void testCalculateBoilEfficiency_readmeExample() {

        BoilLog boilLog = new BoilLog();

        boilLog.setSapVolume(800);
        boilLog.setSapBrix(2.0);
        boilLog.setSyrupProduced(17.5);

        double actual = BoilLogBO.calculateBoilEfficiency(boilLog);

        assertEquals(94.06, actual, DELTA);
    }

    /**
     * Test 2 created following a test driven development (TDD) approach.
     * This test was written first, then calculateCostPerLitre was coded to make it pass.
     * Uses the example from the project readme:  0.5 cord of wood and
     * 17.5 L of syrup gives a fuel cost per litre of 4.29.
     *
     * @author Nick Brown
     * @since 20261002
     */
    @Test
    void testCalculateCostPerLitre_readmeExampleWood() {

        BoilLog boilLog = new BoilLog();

        boilLog.setFuelType("wood");
        boilLog.setFuelQuantity(0.5);
        boilLog.setSyrupProduced(17.5);

        double actual = BoilLogBO.calculateCostPerLitre(boilLog);

        assertEquals(4.29, actual, DELTA);
    }

    /**
     * Test 3 created following a test driven development (TDD) approach.
     * This test was written first, then the check for a sap Brix of 0 was added
     * to calculateBoilEfficiency to make it pass.  A sap Brix of 0 would cause
     * a divide by zero, so the method is to give back 0.
     *
     * @author Nick Brown
     * @since 20261002
     */
    @Test
    void testCalculateBoilEfficiency_zeroSapBrixGivesZero() {

        BoilLog boilLog = new BoilLog();

        boilLog.setSapVolume(800);
        boilLog.setSapBrix(0);
        boilLog.setSyrupProduced(17.5);

        double actual = BoilLogBO.calculateBoilEfficiency(boilLog);

        assertTrue(actual == 0);
    }


    //****************************************************************************
    //The following were the unit tests that were created by Claude AI
    //****************************************************************************

    /**
     * Build a boil log with the values that are used in the calculations.
     *
     * @author Claude AI
     * @since 20261002
     */
    private BoilLog buildBoilLog(double sapVolume, double sapBrix, double syrupProduced,
                                 String fuelType, double fuelQuantity) {
        BoilLog boilLog = new BoilLog();
        boilLog.setSapVolume(sapVolume);
        boilLog.setSapBrix(sapBrix);
        boilLog.setSyrupProduced(syrupProduced);
        boilLog.setFuelType(fuelType);
        boilLog.setFuelQuantity(fuelQuantity);
        return boilLog;
    }

    // Boil efficiency: actual yield matches the predicted yield exactly (860 L at 2.0 Brix predicts 20 L)
    @Test
    void calculateBoilEfficiency_actualEqualsPredictedIs100() {
        BoilLog boilLog = buildBoilLog(860, 2.0, 20, "wood", 0.5);

        assertEquals(100.0, BoilLogBO.calculateBoilEfficiency(boilLog), DELTA);
    }

    // Boil efficiency: more syrup than predicted gives more than 100%
    @Test
    void calculateBoilEfficiency_moreThanPredictedIsOver100() {
        BoilLog boilLog = buildBoilLog(860, 2.0, 22, "wood", 0.5);

        assertEquals(110.0, BoilLogBO.calculateBoilEfficiency(boilLog), DELTA);
        assertTrue(BoilLogBO.calculateBoilEfficiency(boilLog) > 100);
    }

    // Boil efficiency: a low yield boil (600 L at 2.2 Brix predicts 15.35 L, only 13.1 L made)
    @Test
    void calculateBoilEfficiency_lowYieldIsUnder100() {
        BoilLog boilLog = buildBoilLog(600, 2.2, 13.1, "propane", 140);

        assertEquals(85.35, BoilLogBO.calculateBoilEfficiency(boilLog), DELTA);
        assertTrue(BoilLogBO.calculateBoilEfficiency(boilLog) < 100);
    }

    // Boil efficiency: no syrup produced gives 0%
    @Test
    void calculateBoilEfficiency_noSyrupProducedIsZero() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 0, "wood", 0.5);

        assertEquals(0.0, BoilLogBO.calculateBoilEfficiency(boilLog), DELTA);
    }

    // Edge case: no sap volume would divide by zero, so 0 is expected
    @Test
    void calculateBoilEfficiency_zeroSapVolumeIsZero() {
        BoilLog boilLog = buildBoilLog(0, 2.0, 17.5, "wood", 0.5);

        assertEquals(0.0, BoilLogBO.calculateBoilEfficiency(boilLog), DELTA);
    }

    // Edge case: negative values are not valid, so 0 is expected
    @Test
    void calculateBoilEfficiency_negativeValuesAreZero() {
        assertEquals(0.0, BoilLogBO.calculateBoilEfficiency(buildBoilLog(800, -2.0, 17.5, "wood", 0.5)), DELTA);
        assertEquals(0.0, BoilLogBO.calculateBoilEfficiency(buildBoilLog(-800, 2.0, 17.5, "wood", 0.5)), DELTA);
        assertEquals(0.0, BoilLogBO.calculateBoilEfficiency(buildBoilLog(800, 2.0, -17.5, "wood", 0.5)), DELTA);
    }

    // Edge case: a null boil log gives 0 instead of an error
    @Test
    void calculateBoilEfficiency_nullBoilLogIsZero() {
        assertEquals(0.0, BoilLogBO.calculateBoilEfficiency(null), DELTA);
    }

    // Edge case: the result is always a real number (not infinity and not NaN)
    @Test
    void calculateBoilEfficiency_resultIsNeverInfiniteOrNaN() {
        double actual = BoilLogBO.calculateBoilEfficiency(buildBoilLog(0, 0, 0, "wood", 0));

        assertFalse(Double.isInfinite(actual));
        assertFalse(Double.isNaN(actual));
    }

    // Cost per litre: propane at 0.83 per litre (140 L of propane, 13.1 L of syrup)
    @Test
    void calculateCostPerLitre_propane() {
        BoilLog boilLog = buildBoilLog(600, 2.2, 13.1, "propane", 140);

        assertEquals(8.87, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Cost per litre: oil at 2.10 per litre (60 L of oil, 8.8 L of syrup)
    @Test
    void calculateCostPerLitre_oil() {
        BoilLog boilLog = buildBoilLog(380, 2.0, 8.8, "oil", 60);

        assertEquals(14.32, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Cost per litre: electric at 0.15 per kWh (210 kWh, 6.2 L of syrup)
    @Test
    void calculateCostPerLitre_electric() {
        BoilLog boilLog = buildBoilLog(300, 1.8, 6.2, "electric", 210);

        assertEquals(5.08, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Cost per litre: the fuel type is not case sensitive
    @Test
    void calculateCostPerLitre_fuelTypeIgnoresCase() {
        BoilLog lower = buildBoilLog(800, 2.0, 17.5, "wood", 0.5);
        BoilLog upper = buildBoilLog(800, 2.0, 17.5, "WOOD", 0.5);
        BoilLog mixed = buildBoilLog(800, 2.0, 17.5, "Wood", 0.5);

        assertEquals(BoilLogBO.calculateCostPerLitre(lower), BoilLogBO.calculateCostPerLitre(upper), DELTA);
        assertEquals(BoilLogBO.calculateCostPerLitre(lower), BoilLogBO.calculateCostPerLitre(mixed), DELTA);
    }

    // Edge case: a fuel type that is not wood, propane, oil or electric has no price, so 0 is expected
    @Test
    void calculateCostPerLitre_unknownFuelTypeIsZero() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 17.5, "coal", 10);

        assertEquals(0.0, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Edge case: a fuel type that was never set (null) gives 0 instead of an error
    @Test
    void calculateCostPerLitre_nullFuelTypeIsZero() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 17.5, null, 10);

        assertEquals(0.0, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Edge case: no syrup produced would divide by zero, so 0 is expected
    @Test
    void calculateCostPerLitre_noSyrupProducedIsZero() {
        double actual = BoilLogBO.calculateCostPerLitre(buildBoilLog(800, 2.0, 0, "wood", 0.5));

        assertEquals(0.0, actual, DELTA);
        assertFalse(Double.isInfinite(actual));
    }

    // Edge case: no fuel burned costs nothing
    @Test
    void calculateCostPerLitre_noFuelIsZero() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 17.5, "wood", 0);

        assertEquals(0.0, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Edge case: a negative fuel quantity is not valid, so 0 is expected
    @Test
    void calculateCostPerLitre_negativeFuelQuantityIsZero() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 17.5, "wood", -0.5);

        assertEquals(0.0, BoilLogBO.calculateCostPerLitre(boilLog), DELTA);
    }

    // Edge case: a null boil log gives 0 instead of an error
    @Test
    void calculateCostPerLitre_nullBoilLogIsZero() {
        assertEquals(0.0, BoilLogBO.calculateCostPerLitre(null), DELTA);
    }

    // calculate: does both calculations, stores them in the boil log and gives back the boil efficiency
    @Test
    void calculate_setsBothCalculatedFields() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 17.5, "wood", 0.5);

        double actual = BoilLogBO.calculate(boilLog);

        assertEquals(94.06, actual, DELTA);
        assertEquals(94.06, boilLog.getBoilEfficiency(), DELTA);
        assertEquals(4.29, boilLog.getCostPerLitre(), DELTA);
    }

    // calculate: a new boil log has not been calculated yet, and calculate changes that
    @Test
    void calculate_changesTheCalculatedFields() {
        BoilLog boilLog = buildBoilLog(800, 2.0, 17.5, "wood", 0.5);

        assertTrue(boilLog.getBoilEfficiency() == 0);
        assertTrue(boilLog.getCostPerLitre() == 0);

        BoilLogBO.calculate(boilLog);

        assertTrue(boilLog.getBoilEfficiency() > 0);
        assertTrue(boilLog.getCostPerLitre() > 0);
    }

    // Edge case: calculate with a null boil log gives 0 instead of an error
    @Test
    void calculate_nullBoilLogIsZero() {
        assertEquals(0.0, BoilLogBO.calculate(null), DELTA);
    }

}
