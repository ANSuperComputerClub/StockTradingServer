package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Order.OrderType;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The portfolio of an individual trader.
 */
public class Portfolio {
    // Represents the Stock and the Amount of stocks this trader owns
    private final Map<Stock, Integer> stocks;
    private final List<Order> transactionHistory;

    public Portfolio() {
        this.stocks = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public Map<Stock, Integer> getStocks() {
        return stocks;
    }

    public List<Order> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Adds a transaction to the portfolio, then processes it
     * @param order the order to process and store
     */
    public void addTransaction(Order order) throws IllegalTransactionException {
        // TODO: After saving to transaction history, add the stocks
        transactionHistory.add(order);

        // We use this a lot
        final Stock stock = order.getStock();

        // If the transaction is a buy:
        if(order.getType() == OrderType.BUY) {
            // If we already own some, we just add to the order entry
            // IF we don't already own any, we just add the transaction
            stocks.computeIfPresent(stock, (key, value) -> value + order.getQuantity());
            stocks.computeIfAbsent(stock, (key) -> order.getQuantity());
            return;
        }

        // We try to sell stock that we don't own
        if(!stocks.containsKey(stock)) throw new IllegalTransactionException();

        // TODO Ask matthew for a better way to do this
        AtomicBoolean shouldThrowError = new AtomicBoolean(false);

        // If it exists, check if we are allowed to sell the stock
        // If we aren't, throw an error; otherwise proceed with transaction
        stocks.computeIfPresent(stock, (key, value) -> {
            if(value < order.getQuantity()) {
                shouldThrowError.set(true);
                return value;
            }
            return value - order.getQuantity();
        });

        // World's greatest error hanndling system!!
        if(shouldThrowError.get()) {
            throw new IllegalTransactionException();
        }
    }

    /**
     * @return the total value of the portfolio
     */
    public double getTotalBalance() {
        double total = 0;
        for (var entry : stocks.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}
