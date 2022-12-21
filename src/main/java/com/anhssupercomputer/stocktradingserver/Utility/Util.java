package com.anhssupercomputer.stocktradingserver.Utility;

import java.math.BigDecimal;

public class Util {
    public static int generateRandomInteger(int lower, int upper) {
        return (int) (Math.random() * Math.abs(upper - lower)) + lower;
    }

    public static char generateRandomCharacter() {
        return (char) generateRandomInteger(65, 65 + 26);
    }

    public static double generateRandomDouble(double seed) { return Math.random() * seed; }

    public static BigDecimal doubleToBigDecimal(double val) { return new BigDecimal(val); }
}
