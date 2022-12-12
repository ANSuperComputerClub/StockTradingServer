package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketService {
    private Market market;
    private TraderService traderService;

    public MarketService(@Autowired TraderService traderService) {
        this.traderService = traderService;
    }

    /**
     * @return True if successful
     */
    public boolean startMarket() throws NoMarketException {
        if(market == null) {
            throw new NoMarketException();
        }

        return market.startMarket();
    }

    /**
     * @return True if successful
     */
    public boolean stopMarket() throws NoMarketException {
        if(market == null) {
            throw new NoMarketException();
        }

        return market.stopMarket();
    }

    /** Creates a new market */
    public boolean createMarket(int traders, int period) {
        try {
            // Terminate the old market's thread
            if (market != null) {
                market.terminate();
            }

            market = new Market(traders, period, traderService);
            return true;
        } catch(Exception e) {
            // Returns false if a failure occurs.
            return false;
        }
    }

    public boolean isMarketAlive() {
        if(market == null || !market.isAlive() || market.isStopped())
            return false;
        else
            return true;
    }
}
