package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;

import java.util.List;

import com.anhssupercomputer.stocktradingserver.Stock.StockService;

public class Trader {
    private static int nextId = 0;
    /**
     * Default singleton trader
     */
    private static Trader defaultTrader;
    private final int id;
    private final String username;
    private final String key;
    private final boolean isFakeTrader;
    private final Portfolio portfolio;

    private final TradingStrategy strategy;

    public Trader(String username, String key, double startingFunds, boolean isFakeTrader) {
        id = makeId();
        this.username = username;
        this.key = key;
        portfolio = new Portfolio(startingFunds);
        this.isFakeTrader = isFakeTrader;
        this.strategy = TradingStrategy._default;
    }

    public Trader(String username, String key, double startingFunds, boolean isFakeTrader, TradingStrategy strategy) {
        id = makeId();
        this.username = username;
        this.key = key;
        portfolio = new Portfolio(startingFunds);
        this.isFakeTrader = isFakeTrader;
        this.strategy = strategy;
    }

    /**
     * Increments through ids starting from 0 when a trader is created
     *
     * @return the next id
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

    public boolean isFakeTrader() {
        return isFakeTrader;
    }

    /**
     * If the trader is a bot, this function will execute the trading strategy for the specific bot
     * @param orderController an OrderController instance
     * @param priceService a PriceService instance
     * @param stockService a StockService instance
     */
    public void executeStrategy(OrderController orderController, PriceService priceService, StockService stockService) {
        strategy.run(this, orderController, priceService, stockService);
    }
}
