package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import com.anhssupercomputer.stocktradingserver.Utility.AbstractSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Market extends AbstractSystem {

    private boolean stopped;
    private TraderService traderService;
    private StockService stockService;

    /**
     * Simulates the stock market
     * @param traderNumber number of trader
     * @param period period in ms that the simulation runs at
     */
    protected Market(int traderNumber, int stockNumber, int period, TraderService traderService, StockService stockService) throws DuplicateTickerException {
        super(period);
        this.traderService = traderService;
        this.stockService = stockService;

        // Reset the services
        traderService.clearTraders();
        stockService.clearStocks();


        for(int i = 0; i < traderNumber; i++) {
            traderService.addTrader(Trader.makeFakeTrader());
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

        }
    }
}
