package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Price.PriceService;

import java.util.Comparator;

public class StockFavorabilityComparator implements Comparator<Stock> {

    private final PriceService priceService;

    public StockFavorabilityComparator(PriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return 1 if the first one is greater, -1 if the second one is greater, and 0 if they are equal.
     */
    @Override
    public int compare(Stock o1, Stock o2) {
        return Double.compare(priceService.getFavorability(o1), priceService.getFavorability(o2));
    }
}
