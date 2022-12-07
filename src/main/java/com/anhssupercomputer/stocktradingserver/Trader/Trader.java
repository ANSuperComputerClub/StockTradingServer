package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.util.List;
import java.util.Map;

public class Trader {
    private int id;
    private String username;
    private String key;

    private Portfolio portfolio;

    public Trader(int id, String username, String key) {
        this.id = id;
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

    public Map<Stock, Integer> getPortfolio() {
        return portfolio.getStocks();
    }

    public List<Order> getTransactionHistory() {
        return portfolio.getTransactionHistory();
    }
}
