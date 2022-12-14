package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import com.anhssupercomputer.stocktradingserver.Utility.AbstractSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class Market extends AbstractSystem {

    private boolean stopped;
    private TraderService traderService;
    private StockService stockService;
    private PriceService priceService;
    private OrderController orderController;

    /**
     * Simulates the stock market
     * @param traderNumber number of trader
     * @param period period in ms that the simulation runs at
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


        for(int i = 0; i < traderNumber; i++) {
            traderService.addTrader(new Trader("", "", 10000, priceService, true));
        }

        for(int i = 0; i < stockNumber; i++) {

            String ticker = stockService.generateUnusedTicker();
            stockService.saveStock(new Stock(ticker, ticker, Math.random() * 100d, (int) (Math.random() * 100000d)));
        }
    }

    /**
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
        } catch(Exception e) {
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
        } catch(Exception e) {
            return false;
        }
    }

    protected boolean isStopped() {
        return stopped;
    }

    @Override
    protected void controlLoop() {
        if(!stopped) {
            ArrayList<Stock> rankedStocks = stockService.getRankedStocks();

            // Run through each trader and have them take their actions
            for(Trader trader : traderService.getAllTraders()) {
                if(trader.isFakeTrader()) {

                    // Find and sell stocks that have low favorability
                    for(Stock stock: trader.getPortfolio().getStocks().keySet()) {
                        if(priceService.getFavorability(stock) < 0) {
                            try {
                                orderController.createOrder(trader.getId(), stock.getTicker(), "SELL", trader.getPortfolio().getStocks().get(stock));
                            } catch (NotFoundException|IllegalTransactionException e) {
                                System.out.println("Could not make transaction for trader: " + trader.getId() + " for stock: " + stock.getName());
                            }
                        }
                    }

                    for(Stock stock : rankedStocks) {
                        double funds = trader.getPortfolio().getFunds();

                        if(stock.getPrice() < funds) {
                            try {
                                int quantity = (int) (funds / stock.getPrice());

                                // Makes sure it doesn't buy more that it is allowed
                                if(quantity > stock.getAvailableVolume()) {
                                    quantity = stock.getAvailableVolume();
                                }

                                orderController.createOrder(trader.getId(), stock.getTicker(), "BUY", quantity);
                            } catch (NotFoundException|IllegalTransactionException e) {
                                System.out.println("Could not make transaction for trader: " + trader.getId() + " for stock: " + stock.getName());
                            }
                        }
                    }
                }
            }
        }
    }
}
