package com.anhssupercomputer.stocktradingserver.Stock;

/**
 * A simple StockPriceEntry that will be used in the price history for a stock. Has a price and a time of recording
 */
public class StockPriceEntry {

    private final double price;
    private final double timeMS;

    protected StockPriceEntry(double price, double timeMS) {
        this.price = price;
        this.timeMS = timeMS;
    }

    public double getPrice() {
        return price;
    }

    public double getTimeMS() {
        return timeMS;
    }
}
