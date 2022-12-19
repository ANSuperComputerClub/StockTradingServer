package com.anhssupercomputer.stocktradingserver.Utility;

public class Util {
    public static int generateRandomInteger(int lower, int upper) {
        return (int) (Math.random() * Math.abs(upper - lower)) + lower;
    }

    public static char generateRandomCharacter() {
        return (char) generateRandomInteger(65, 65 + 26);
    }
}
