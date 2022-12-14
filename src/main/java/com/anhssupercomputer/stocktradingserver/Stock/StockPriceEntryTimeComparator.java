package com.anhssupercomputer.stocktradingserver.Stock;

import java.util.Comparator;

public class StockPriceEntryTimeComparator implements Comparator<StockPriceEntry>{

    @Override
    public int compare(StockPriceEntry o1, StockPriceEntry o2) {
        if(o1.getTimeMS() < o2.getTimeMS()) {
            return -1;
        } else {
            return 1;
        }
    }
}