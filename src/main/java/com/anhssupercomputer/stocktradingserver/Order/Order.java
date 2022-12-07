package com.anhssupercomputer.stocktradingserver.Order;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.util.Date;

/**
 * An order, in which a Trader makes a transaction of a particular stock.
 * This will mainly be used for record keeping.
 */
public class Order {
    private Stock stock;
    private OrderType type;
    private int quantity;
    private Date date;
    public Order(Stock stock, OrderType type, int quantity, Date date) {
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public OrderType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }
}
