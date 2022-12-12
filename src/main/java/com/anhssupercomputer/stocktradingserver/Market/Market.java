package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import com.anhssupercomputer.stocktradingserver.Utility.AbstractSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Market extends AbstractSystem {

    private boolean stopped;
    TraderService traderService;

    /**
     * Simulates the stock market
     * @param traderNumber number of trader
     * @param period period in ms that the simulation runs at
     */
    protected Market(int traderNumber, int period, TraderService traderService) {
        super(period);
        this.traderService = traderService;

        // Reset the trader list
        traderService.clearTraders();

        for(int i = 0; i < traderNumber; i++) {
            traderService.addTrader(Trader.makeFakeTrader());
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
