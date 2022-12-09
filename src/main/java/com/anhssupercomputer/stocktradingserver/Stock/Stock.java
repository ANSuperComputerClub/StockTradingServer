package com.anhssupercomputer.stocktradingserver.Stock;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private String name;
    /**
     * The ticker for the stock
     */
    private String ticker;

    /**
     * The price of the stock
     */
    private BigDecimal price;
    /**
     * The total volume of the stock
     */
    private int totalVolume;

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
    public Stock(String name, String ticker, BigDecimal price, int totalVolume) {
        this.name = name;
        this.ticker = ticker;
        this.price = price;
        this.totalVolume = totalVolume;
        this.availableVolume = totalVolume;
        this.dividend = 0;
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
    public Stock(String name, String ticker, BigDecimal price, int totalVolume, double dividend) {
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
    public Stock(String name, String ticker, BigDecimal price, int totalVolume, StockService service) {
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
    public BigDecimal getPrice() {
        return price;
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
    public boolean updateAvailableVolume(int amountToPurchase) {
        if (amountToPurchase > availableVolume) return false;
        availableVolume -= amountToPurchase;
        return true;
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
}