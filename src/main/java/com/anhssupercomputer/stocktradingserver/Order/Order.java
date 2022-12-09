package com.anhssupercomputer.stocktradingserver.Order;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.util.Date;

/**
 * An order, in which a Trader makes a transaction of a particular stock.
 * This will mainly be used for record keeping.
 */
public class Order {
    /**
     * The stock that was bought in the order
     */
    private Stock stock;
    /**
     * The type of order it is
     */
    private OrderType type;
    /**
     * How much of that stock was bought
     */
    private int quantity;
    /**
     * The date that the transaction happened
     */
    private Date date;
    public Order(Stock stock, OrderType type, int quantity) {
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.date = new Date();
    }

    /**
     * @return The stock of the order
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * @return Whether the stock was bought or sold
     *
     */
    public OrderType getType() {
        return type;
    }

    /**
     * @return How much stock was bought/sold
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return The date of the transaction
     */
    public Date getDate() {
        return date;
    }
}
