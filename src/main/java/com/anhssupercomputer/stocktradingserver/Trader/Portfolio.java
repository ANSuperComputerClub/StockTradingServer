package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The portfolio of an individual trader.
 */
public class Portfolio {
    private Map<Stock, Integer> stocks;
    private List<Order> transactionHistory;

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
    public void addTransaction(Order order) {
        // TODO: After saving to transaction history, add the stocks
        transactionHistory.add(order);
    }

    /**
     * @return the total value of the portfolio
     */
    public BigDecimal getTotalBalance() {
        BigDecimal total = new BigDecimal("0");
        for (var entry : stocks.entrySet()) {
            total = total.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }
}
