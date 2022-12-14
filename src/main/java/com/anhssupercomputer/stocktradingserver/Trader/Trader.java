package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.util.List;
import java.util.Map;

public class Trader {
    private static int nextId = 0;
    private int id;
    private String username;
    private String key;
    private boolean isFakeTrader;

    /**
     * Default singleton trader
     */
    private static Trader defaultTrader;

    private Portfolio portfolio;

    public Trader(String username, String key, double startingFunds, PriceService priceService, boolean isFakeTrader) {
        id = makeId();
        this.username = username;
        this.key = key;
        portfolio = new Portfolio(startingFunds, priceService);
        this.isFakeTrader = isFakeTrader;
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

    protected static void resetIdCount() {
        nextId = 0;
    }

    public boolean isFakeTrader() {
        return isFakeTrader;
    }
}
