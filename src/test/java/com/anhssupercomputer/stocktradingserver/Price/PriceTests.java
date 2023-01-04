package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Market.MarketService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import com.anhssupercomputer.stocktradingserver.Utility.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PriceTests {
    MarketService marketService;
    TraderService traderService;
    StockService stockService;
    PriceService priceService;

    public PriceTests(@Autowired MarketService marketService, @Autowired TraderService traderService, @Autowired StockService stockService, @Autowired PriceService priceService) {
        this.marketService = marketService;
        this.traderService = traderService;
        this.stockService = stockService;
        this.priceService = priceService;
    }
    @Test
    public void testPricingFunction() throws DuplicateTickerException, NoMarketException {
        marketService.createMarket(Constants.TRADERS, Constants.STOCK_NUMBER, Constants.PERIOD);
        marketService.startMarket();

        Stock stockToTrack = stockService.getStocks().get(0);

        long last = 0;
        int period = 0;

        while(period != 1000) {
            if(System.currentTimeMillis() - last > Constants.PERIOD) {
                double stockPrice = stockToTrack.getPrice();
                System.out.println(stockPrice);
                period++;
                last = System.currentTimeMillis();
            }
        }
    }
}
