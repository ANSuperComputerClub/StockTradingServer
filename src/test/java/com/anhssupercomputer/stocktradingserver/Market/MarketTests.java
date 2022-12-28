package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MarketTests {
    MarketService marketService;
    TraderService traderService;
    StockService stockService;
    PriceService priceService;

    /**
     * Makes sure that market gets initialized with correct number of traders and returns initialization time
     * @throws NoMarketException if the market doesn't exist
     */
    @Test
    public void createMarketTest() throws NoMarketException, DuplicateTickerException {

        long startTimeMS = System.currentTimeMillis();

        marketService.createMarket(5000, 500,20);

        long elapsedTimeMS = System.currentTimeMillis() - startTimeMS;
        System.out.println("Market Initialization Time in MS: " + elapsedTimeMS);

        assert (marketService.startMarket());
        assert (marketService.isMarketAlive());
        assert (traderService.getTraderNumber() == 5000);
        assert (stockService.getStocks().size() == 500);

        for(Stock stock : stockService.getStocks()) {
            System.out.println(stock.getTicker() + " " + stock.getPrice() + " " + stock.getTotalVolume());
        }
    }

    @Test
    public void viewMarketSimulation() throws DuplicateTickerException, NoMarketException, NotFoundException {
        marketService.createMarket(200, 10, 1000);

        marketService.startMarket();

        Trader tracked = traderService.getTraderById(0);

        Stock stock = stockService.getStocks().get(0);
        long last = 0;
        while(true) {
            if(System.currentTimeMillis() - last > 1) {
                System.out.println(stock.getTicker() + " " + stock.getPrice() + " " + stock.getAvailableVolume());
                last = System.currentTimeMillis();
            }
        }


    }

    public MarketTests(@Autowired MarketService marketService, @Autowired TraderService traderService, @Autowired StockService stockService, @Autowired PriceService priceService) {
        this.marketService = marketService;
        this.traderService = traderService;
        this.stockService = stockService;
        this.priceService = priceService;
    }
}
