package com.anhssupercomputer.stocktradingserver.Stock;


import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;

/**
 * A representation of a Stock object, to be exchanged on the server by way of Orders.
 *
 * @author Medha
 * @date 12/6/2022
 */
public class Stock {
    /**
     * The name of the stock
     */
    private final String name;
    /**
     * The ticker for the stock
     */
    private final String ticker;
    /**
     * The total volume of the stock
     */
    private final int totalVolume;
    private final CircularFifoQueue<StockPriceEntry> priceHistory;
    /**
     * The price of the stock
     */
    private double price;
    /**
     * The available volume of stock to be bought
     */
    private int availableVolume;
    /**
     * The ratio of profit to dividend, paid monthly
     */
    private double dividend;

    /**
     * Stock constructor without dividend
     *
     * @param name        The name of the stock, e.g. "Google"
     * @param ticker      The ticker for the stock, e.g. "GOOG"
     * @param price       The price of the stock, leave at 5 for default
     * @param totalVolume The total available volume
     */
    public Stock(String name, String ticker, double price, int totalVolume) {
        this.name = name;
        this.ticker = ticker;
        this.totalVolume = totalVolume;
        this.availableVolume = totalVolume;
        this.dividend = 0;
        priceHistory = new CircularFifoQueue<>(50);
        setPrice(price);
    }

    /**
     * Full argument constructor for the Stock object
     *
     * @param name        The name of the stock, e.g. "Google"
     * @param ticker      The ticker for the stock, e.g. "GOOG"
     * @param price       The price of the stock, leave at 5 for default
     * @param totalVolume The total available volume
     * @param dividend    The dividend earned per month
     */
    public Stock(String name, String ticker, double price, int totalVolume, double dividend) {
        this(name, ticker, price, totalVolume);
        this.dividend = dividend;
    }

    /**
     * Create a Stock and save it permanently
     *
     * @param name        The name of the stock, e.g. "Google"
     * @param ticker      The ticker for the stock, e.g. "GOOG"
     * @param price       The price of the stock, leave at 5 for default
     * @param totalVolume The total available volume
     * @param service     The service that should store this stock
     */
    public Stock(String name, String ticker, double price, int totalVolume, StockService service) throws DuplicateTickerException {
        this(name, ticker, price, totalVolume);
        service.saveStock(this);
    }

    /**
     * @return The name of the stock
     */
    public String getName() {
        return name;
    }

    /**
     * @return The ticker of the stock
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @return The price of the stock
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price < 0) return;
        priceHistory.add(new StockPriceEntry(price, System.currentTimeMillis()));
        this.price = price;
    }

    /**
     * @return The total volume of stock
     */
    public int getTotalVolume() {
        return totalVolume;
    }

    /**
     * @return The available volume of the stock
     */
    public int getAvailableVolume() {
        return availableVolume;
    }

    public double getDividend() {
        return dividend;
    }

    /**
     * Updates volume of stock available
     */
    public void updateAvailableVolume(int amountToPurchase) {
        if (amountToPurchase > availableVolume) return;
        availableVolume -= amountToPurchase;
    }

    /**
     * @return ArrayList of the stock prices starting from newest to oldest
     */
    public ArrayList<StockPriceEntry> getPriceHistory() {
        ArrayList<StockPriceEntry> sortedList = new ArrayList<>(20);

        for (Object stockPriceEntry : priceHistory) {
            sortedList.add((StockPriceEntry) stockPriceEntry);
        }

        sortedList.sort(new StockPriceEntryTimeComparator());
        return sortedList;
    }

    /**
     * @return A representation of the stock as a string
     */
    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", price=" + price +
                ", totalVolume=" + totalVolume +
                ", availableVolume=" + availableVolume +
                ", dividend=" + dividend +
                '}';
    }

    /**
     * Equals override
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.getClass() != getClass()) return false;
        // At this point we are guarnateed to have a clean conversion
        return this.equals((Stock) obj);
    }

    /**
     * @param stock the stock to compare it to
     * @return true if equal, false if not
     */
    public boolean equals(Stock stock) {
        return tickerMatches(stock.ticker);
    }

    /**
     * If a particular ticker matches a stock
     * @param ticker the ticker to check
     * @return true if match, false if not
     */
    public boolean tickerMatches(String ticker) {
        return this.ticker.equals(ticker);
    }
}