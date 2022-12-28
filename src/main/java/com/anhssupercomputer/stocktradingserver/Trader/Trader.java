package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;

import java.util.ArrayList;
import java.util.List;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;
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

    private static final TradingStrategy defaultStrategy = (trader, orderController, priceService, stockService) -> {
        // Find and sell stocks that have low favorability
        for (Stock stock : trader.getPortfolio().getStocks().keySet()) {
            if (priceService.getFavorability(stock) < 0) {
                try {
                    orderController.createOrder(trader.getId(), stock.getTicker(), "SELL", trader.getPortfolio().getStocks().get(stock));
                } catch (NotFoundException | IllegalTransactionException e) {
                    System.out.println("Could not make transaction for trader: " + trader.getId() + " for stock: " + stock.getName());
                }
            }
        }

        ArrayList<Stock> rankedStocks = stockService.getRankedStocks();

        for (Stock stock : rankedStocks) {
            double funds = trader.getPortfolio().getFunds();

            if (stock.getPrice() < funds) {
                try {
                    int quantity = (int) (funds / stock.getPrice());

                    // Makes sure it doesn't buy more that it is allowed
                    if (quantity > stock.getAvailableVolume()) {
                        quantity = stock.getAvailableVolume();
                    }

                    orderController.createOrder(trader.getId(), stock.getTicker(), "BUY", quantity);
                } catch (NotFoundException | IllegalTransactionException e) {
                    System.out.println("Could not make transaction for trader: " + trader.getId() + " for stock: " + stock.getName());
                }
            }
        }
    };

    public Trader(String username, String key, double startingFunds, boolean isFakeTrader) {
        id = makeId();
        this.username = username;
        this.key = key;
        portfolio = new Portfolio(startingFunds);
        this.isFakeTrader = isFakeTrader;
        this.strategy = defaultStrategy;
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
