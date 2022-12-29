package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import com.anhssupercomputer.stocktradingserver.Utility.AbstractSystem;

import java.util.ArrayList;

public class Market extends AbstractSystem {

    private final TraderService traderService;
    private final StockService stockService;
    private final PriceService priceService;
    private final OrderController orderController;
    private boolean stopped;

    /**
     * Simulates the stock market
     *
     * @param traderNumber number of trader
     * @param period       period in ms that the simulation runs at
     */
    protected Market(int traderNumber, int stockNumber, int period, TraderService traderService, StockService stockService, PriceService priceService, OrderController orderController) throws DuplicateTickerException {
        super(period);
        this.traderService = traderService;
        this.stockService = stockService;
        this.priceService = priceService;
        this.orderController = orderController;

        // Reset the services
        traderService.clearTraders();
        stockService.clearStocks();


        for (int i = 0; i < traderNumber; i++) {
            traderService.addTrader(new Trader("", "", 10000,true));
        }

        for (int i = 0; i < stockNumber; i++) {

            String ticker = stockService.generateUnusedTicker();
            stockService.saveStock(new Stock(ticker, ticker, Math.random() * 100d, 999999999));
        }
    }

    /**
     * Starts the market
     *
     * @return true if successful
     */
    protected boolean startMarket() {
        try {
            stopped = false;

            if (!isAlive()) {
                setInitialized();
                start();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @return true if successful
     */
    protected boolean stopMarket() {
        try {
            stopped = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isStopped() {
        return stopped;
    }

    @Override
    protected void controlLoop() {
        if (!stopped) {
            // Run through each trader and have them take their actions
            for (Trader trader : new ArrayList<>(traderService.getAllTraders())) {
                // If the trader is fake
                if (trader.isFakeTrader()) {
                    trader.executeStrategy(orderController, priceService, stockService);
                }
            }

            // After every iteration, update the price of the stock
            for(Stock stock : stockService.getStocks()) {
                stockService.updateStockPrice(stock);
            }
        }
    }
}
