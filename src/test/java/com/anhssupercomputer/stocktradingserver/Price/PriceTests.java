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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    @Test
    public void averageIncreaseTest() throws DuplicateTickerException, NoMarketException {
        // Start market
        marketService.createMarket(Constants.TRADERS, Constants.STOCK_NUMBER, Constants.PERIOD);
        marketService.startMarket();

        // Set up the initial average price
        double initialAverageStockPrice = stockService.getStocks().stream().collect(Collectors.averagingDouble(Stock::getPrice));
        double lastAverageStockPrice = initialAverageStockPrice;
        double derivativeAverageStockPrice = 0;

        // Loop variables
        long last = 0;
        int period = 0;
        while(period != 1000) {
            if(System.currentTimeMillis() - last > Constants.PERIOD){
                // Get the current average stock price
                double currentAverageStockPrice = stockService.getStocks().stream().collect(Collectors.averagingDouble(Stock::getPrice));
                double averageStockPriceTotalDelta = (currentAverageStockPrice - initialAverageStockPrice) / initialAverageStockPrice;

                // Calculate derivatives and update
                derivativeAverageStockPrice = (currentAverageStockPrice - lastAverageStockPrice) / currentAverageStockPrice;
                lastAverageStockPrice = currentAverageStockPrice;

                // Update
                System.out.println(averageStockPriceTotalDelta + "\t" + derivativeAverageStockPrice);

                // Update loop variables
                period++;
                last = System.currentTimeMillis();
            }
        }
    }

    @Test
    public void stockToMarketValueTest() throws DuplicateTickerException, NoMarketException, IOException {
        // Start market
        marketService.createMarket(Constants.TRADERS, Constants.STOCK_NUMBER, Constants.PERIOD);
        marketService.startMarket();

        // Loop variables
        long last = 0;
        int period = 0;

        // Set up the file writing
        File outputFile = new File("data\\output.txt");
        FileWriter writer = new FileWriter("data\\output.txt");

        // Get the initial data for the average price and the stock prices
        double initialAveragePrice = stockService.getStocks().stream().collect(Collectors.averagingDouble(Stock::getPrice));
        ArrayList<Double> initalStockPrices = new ArrayList<>(Constants.STOCK_NUMBER);
        stockService.getStocks().forEach(stock -> initalStockPrices.add(stock.getPrice()));
        stockService.getStocks().forEach(System.out::println);

        while(period != 10000) {
            if(System.currentTimeMillis() - last > Constants.PERIOD) {
                // Regular progress
                if(period % 1000 == 0) System.out.println("PERIOD: @[" + period + "]");

                // The ratio of the current price to the orignal price
                double currentAverageStockPrice = stockService.getStocks().stream().collect(Collectors.averagingDouble(Stock::getPrice)) / initialAveragePrice;

                // Get the list of stock prices
                ArrayList<Double> stockPrices = new ArrayList<>(Constants.STOCK_NUMBER);
                stockService.getStocks().forEach(stock -> stockPrices.add(stock.getPrice()));
                for(int i = 0; i < stockPrices.size(); i++) {
                    // Set them equal to their current values over their original values
                    stockPrices.set(i, stockPrices.get(i) / initalStockPrices.get(i));
                }

                // Create the output string
                StringBuilder output = new StringBuilder();
                output.append(currentAverageStockPrice).append(" ");
                stockPrices.forEach(price -> output.append(price).append(" "));
                output.append('\n');

                // Write it to the file
                writer.append(output);

                // Update loop
                period++;
                last = System.currentTimeMillis();
            }
        }

        // Close the file
        writer.close();
    }
}
