package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Utility.AbstractSystem;

public class Market extends AbstractSystem {

    private boolean stopped;

    /**
     * Simulates the stock market
     * @param traderNumber number of trader
     * @param period period in ms that the simulation runs at
     */
    public Market(int traderNumber, int period) {
        super(period);
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

    @Override
    protected void controlLoop() {
        if(!stopped) {

        }
    }
}
