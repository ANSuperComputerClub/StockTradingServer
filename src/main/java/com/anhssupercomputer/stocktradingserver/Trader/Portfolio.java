package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Order.Order;
import com.anhssupercomputer.stocktradingserver.Order.OrderType;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * The portfolio of an individual trader.
 */
public class Portfolio {
    // Represents the Stock and the Amount of stocks this trader owns
    private final Map<Stock, Integer> stocks;
    private final List<Order> transactionHistory;
    private final ReentrantReadWriteLock stockLock;
    // TODO: Move the functionality of the PriceService somewhere else
    private final PriceService priceService;
    private double funds;

    public Portfolio(double startingFunds, PriceService priceService) {
        this.stocks = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
        funds = startingFunds;
        stockLock = new ReentrantReadWriteLock();
        this.priceService = priceService;
    }

    public double getFunds() {
        return funds;
    }

    public double getProfit() {
        double totalPrice = 0;
        for (Stock stock : stocks.keySet()) {
            totalPrice = stock.getPrice() * stocks.get(stock);
        }

        return totalPrice;
    }

    /**
     * @param change Change in funds will be added to the total funds.
     */
    public void changeFunds(double change) {
        //    funds += change;
        updateFunds(val -> val + change);
    }

    /**
     * Update the value of funds
     * @param f the function to run. The return value of this function becomes the value of funds
     */
    public void updateFunds(Function<Double, Double> f) {
        funds = f.apply(funds);
    }

    public Map<Stock, Integer> getStocks() {
        stockLock.readLock().lock();
        try {
            return stocks;
        } finally {
            stockLock.readLock().unlock();
        }
    }

    public List<Order> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Adds a transaction to the portfolio, then processes it
     *
     * @param order the order to process and store
     */
    public void addTransaction(Order order) throws IllegalTransactionException {
        stockLock.writeLock().lock();
        try {
            // TODO: After saving to transaction history, add the stocks
            transactionHistory.add(order);

            // We use this a lot
            final Stock stock = order.getStock();

            // If the transaction is a buy: 
            if (order.getType() == OrderType.BUY) {
                // If we already own some, we just add to the order entry
                // IF we don't already own any, we just add the transaction
                stocks.computeIfPresent(stock, (key, value) -> value + order.getQuantity());
                stocks.computeIfAbsent(stock, (key) -> order.getQuantity());
                changeFunds(-(stock.getPrice() * order.getQuantity()));
                stock.updateAvailableVolume(order.getQuantity());
                stock.setPrice(priceService.getPrice(stock));
                return;
            }

            // We try to sell stock that we don't own
            if (!stocks.containsKey(stock))
                throw new IllegalTransactionException();

            // TODO Ask matthew for a better way to do this
            AtomicBoolean shouldThrowError = new AtomicBoolean(false);

            // If it exists, check if we are allowed to sell the stock
            // If we aren't, throw an error; otherwise proceed with transaction
            stocks.computeIfPresent(stock, (key, value) -> {
                if (value < order.getQuantity()) {
                    shouldThrowError.set(true);
                    return value;
                }
                return value - order.getQuantity();
            });

            // World's greatest error handling system!!
            if (shouldThrowError.get()) {
                throw new IllegalTransactionException();
            }

            changeFunds((stock.getPrice() * order.getQuantity()));
            stock.updateAvailableVolume(-order.getQuantity());
            stock.setPrice(priceService.getPrice(stock));
        } finally {
            stockLock.writeLock().unlock();
        }
    }

    /**
     * @return the total value of the portfolio
     */
    public double getTotalBalance() {
        stockLock.readLock().lock();
        try {
            double total = 0;
            for (var entry : stocks.entrySet()) {
                total += entry.getKey().getPrice() * entry.getValue();
            }
            return total;
        } finally {
            stockLock.readLock().unlock();
        }
    }
}
