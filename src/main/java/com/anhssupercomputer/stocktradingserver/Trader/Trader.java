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
    private boolean isFakeTrader;

    /**
     * Default singleton trader
     */
    private static Trader defaultTrader;

    private Portfolio portfolio;

    public Trader(String username, String key) {
        id = makeId();
        this.username = username;
        this.key = key;
        isFakeTrader = false;
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

    /**
     * Gets a default trader with id -1
     * @return
     */
    public static Trader getDefault() {
        if(defaultTrader == null) {
            defaultTrader = new Trader("Default", "");
            defaultTrader.id = -1;
        }

        return defaultTrader;
    }

    /**
     * Factory for making fake traders. This is not a constructor so it does not get confused with a standard trader
     * @return
     */
    public static Trader makeFakeTrader() {
        Trader fakeTrader = new Trader("" + nextId, "");
        fakeTrader.isFakeTrader = true;
        return fakeTrader;
    }
}
