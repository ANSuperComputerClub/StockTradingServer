package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.util.List;
import java.util.Map;

public class Trader {
    private static int nextId = 0;
    private int id;
    private String username;
    private String key;

    private Portfolio portfolio;

    public Trader(String username, String key) {
        id = makeId();
        this.username = username;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public List<Order> getTransactionHistory() {
        return portfolio.getTransactionHistory();
    }

    /**
     * Increments through ids starting from 0 when a trader is created
     * @return
     */
    private static int makeId() {
        try {
            return nextId;
        } finally {
            nextId++;
        }
    }
}
