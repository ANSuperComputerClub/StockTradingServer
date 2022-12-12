package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MarketTests {
    MarketService marketService;
    TraderService traderService;

    /**
     * Makes sure that market gets initialized with correct number of traders and returns initialization time
     * @throws NoMarketException
     */
    @Test
    public void createMarketTest() throws NoMarketException, InterruptedException {

        long startTimeMS = System.currentTimeMillis();

        marketService.createMarket(5000, 20);

        long elapsedTimeMS = System.currentTimeMillis() - startTimeMS;
        System.out.println("Market Initialization Time in MS: " + elapsedTimeMS);

        assert (marketService.startMarket());
        assert (marketService.isMarketAlive());
        assert (traderService.getTraderNumber() == 5000);
    }

    public MarketTests(@Autowired MarketService marketService, @Autowired TraderService traderService) {
        this.marketService = marketService;
        this.traderService = traderService;
    }
}
