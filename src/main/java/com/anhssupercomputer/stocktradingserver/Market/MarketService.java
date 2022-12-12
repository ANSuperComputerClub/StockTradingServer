package com.anhssupercomputer.stocktradingserver.Market;

import org.springframework.stereotype.Component;

@Component
public class MarketService {
    private Market market;

    public MarketService() {

    }

    /** Creates a new market */
    public boolean createMarket(int traders, int period) {
        try {
            // Terminate the old market's thread
            if (market != null) {
                market.terminate();
            }

            market = new Market(traders, period);
            return true;
        } catch(Exception e) {
            // Returns false if a failure occurs.
            return false;
        }
    }
}
