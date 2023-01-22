package com.anhssupercomputer.stocktradingserver.Utility;

import java.math.BigDecimal;
import java.util.List;

public class Util {
    public static int generateRandomInteger(int lower, int upper) {
        return (int) (Math.random() * Math.abs(upper - lower)) + lower;
    }

    public static char generateRandomCharacter() {
        return (char) generateRandomInteger(65, 65 + 26);
    }

    public static double generateRandomDouble(double seed) { return Math.random() * seed; }

    public static BigDecimal doubleToBigDecimal(double val) { return new BigDecimal(val); }

    public static double standardDeviation(List<Double> values) {
        // First we need the mean
        double mean = values.stream().reduce(Double::sum).orElse(0.0) / values.size();

        // Now we calculate the sum of all the differences, squared
        double sumOfDifferencesSquared = values.stream().map((val) -> Math.pow(val - mean, 2)).reduce(Double::sum).orElse(0.0);

        // Now we return that value divided by the size, to the one half power
        return Math.sqrt(sumOfDifferencesSquared / values.size());
    }
}
