package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Market.MarketService;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import com.anhssupercomputer.stocktradingserver.Utility.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockTests {
    StockService stockService;

    /**
     * Makes sure that market gets initialized with correct number of traders and returns initialization time
     */
    @Test
    public void generateTickerTest() throws NoMarketException, InterruptedException {
        String current = "";
        String previous = "";
        for(int i = 0; i < Constants.STOCK_TICKER_GENERATOR_TEST_ITERATIONS; i++) {
            // Make sure that the one before isn't the same as the last
            previous = current;
            current = stockService.generateUnusedTicker();
            assert(!previous.equals(current));
        }
    }


    public StockTests(@Autowired StockService stockService) {
        this.stockService = stockService;
    }
}
