package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Market.MarketService;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockTests {
    StockService stockService;

    /**
     * Makes sure that market gets initialized with correct number of traders and returns initialization time
     * @throws NoMarketException
     */
    @Test
    public void generateTickerTest() throws NoMarketException, InterruptedException {
        for(int i = 0; i < 100; i++) {
            System.out.println(stockService.generateUnusedTicker());
        }
    }


    public StockTests(@Autowired StockService stockService) {
        this.stockService = stockService;
    }
}
