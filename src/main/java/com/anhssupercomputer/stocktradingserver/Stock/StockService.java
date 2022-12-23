package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Service class for the stock
 */
@Component
public class StockService {
    private final PriceService priceService;

    private final ArrayList<Stock> stockList;

    public StockService(@Autowired PriceService priceService) {
        // TODO Acutally load this from a Database (we have to add databse stuff before
        // then)
        stockList = new ArrayList<>();
        this.priceService = priceService;
    }

    /**
     * @return a list of all stocks in the memory store
     */
    public ArrayList<Stock> getStocks() {
        return new ArrayList<>(stockList);
    }

    /**
     * Adds a stock to the in-memory cache of stocks
     *
     * @param stock the stock to add
     */
    private void addStockInMemory(Stock stock) throws DuplicateTickerException {
        // Check if there is a ticker that already exists
        if (tickerInUse(stock.getTicker())) {
            throw new DuplicateTickerException();
        }

        stockList.add(stock);
    }

    /**
     * Save stock to storage
     */
    public void saveStock(Stock stock) throws DuplicateTickerException {
        // TODO: Determine when this should be added to memory vs the database
        // Right now it just adds it to memory
        addStockInMemory(stock);
    }

    /**
     * @param ticker The ticker of the stock to find
     * @return The stock with the matching ticker
     * @throws NotFoundException if no stock was found
     */
    public Stock getStockByTicker(String ticker) throws NotFoundException {
        return stockList.stream().filter(stock -> stock.tickerMatches(ticker)).findFirst()
                .orElseThrow(NotFoundException::new);
    }

    /**
     * @param ticker The ticker to check
     * @return if the ticker is in use or not
     */
    public boolean tickerInUse(String ticker) {
        return stockList.stream().anyMatch(stock -> stock.tickerMatches(ticker));
    }

    public void clearStocks() {
        stockList.clear();
    }

    /**
     * Generates random capitalized characters and checks to see if they are in use
     */
    public String generateUnusedTicker() {
        String ticker;
        do ticker = new String(new char[] {
            Util.generateRandomCharacter(),
            Util.generateRandomCharacter(),
            Util.generateRandomCharacter(),
            Util.generateRandomCharacter()
        });
        while (tickerInUse(ticker));

        return ticker;
    }

    public ArrayList<Stock> getRankedStocks() {
        ArrayList<Stock> sortedStockList = new ArrayList<>(stockList);
        sortedStockList.sort(new StockFavorabilityComparator(priceService));
        return sortedStockList;
    }

    public void updateStockPrice(Stock stock) {
        stock.setPrice(priceService.getPrice(stock));
    }
}
