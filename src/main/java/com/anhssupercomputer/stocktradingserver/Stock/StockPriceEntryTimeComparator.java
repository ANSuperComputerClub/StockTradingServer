package com.anhssupercomputer.stocktradingserver.Stock;

import java.util.Comparator;

public class StockPriceEntryTimeComparator implements Comparator<StockPriceEntry> {

    /**
     * Compares two stock prices
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return 1 if the first one is greater, -1 if the second one is greater, 0 if they are equal
     */
    @Override
    public int compare(StockPriceEntry o1, StockPriceEntry o2) {
        return Double.compare(o1.getTimeMS(), o2.getTimeMS());
    }
}