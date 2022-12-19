package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Service class for the stock
 */
@Component
public class StockService {
    private PriceService priceService;

    private ArrayList<Stock> stockList;

    public StockService(@Autowired PriceService priceService) {
        // TODO Acutally load this from a Database (we have to add databse stuff before then)
        stockList = new ArrayList<>();
        this.priceService = priceService;
    }

    /**
     * Returns a list of all stocks in the memory store
     * @return
     */
    public ArrayList<Stock> getStocks() {
        return (ArrayList<Stock>) stockList.clone();
    }

    /**
     * Adds a stock to the in-memory cache of stocks
     * @param stock
     */
    private void addStockInMemory(Stock stock) throws DuplicateTickerException {
        if(stockList.contains(stock.getTicker())) {
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

    public Stock getStockByTicker(String ticker) throws NotFoundException {
        for(Stock stock: stockList) {
            if(stock.getTicker().equals(ticker)) {
                return stock;
            }
        }
        throw new NotFoundException();
    }

    public void clearStocks() {
        stockList.clear();
    }

    /**
     * Generates random capitalized characters and checks to see if they are in use
     */
    public String generateUnusedTicker() {
        String ticker;
        do {
            // Convert to chars
            char charOne = Util.generateRandomCharacter();
            char charTwo = Util.generateRandomCharacter();
            char charThree = Util.generateRandomCharacter();
            char charFour =  Util.generateRandomCharacter();

            char[] chars = {charOne, charTwo, charThree, charFour};
            ticker = new String(chars);

        } while(stockList.stream().anyMatch(stock -> stock.getTicker().equals(ticker)));

        return ticker;
    }

    public ArrayList<Stock> getRankedStocks() {
        ArrayList<Stock> sortedStocklist = (ArrayList<Stock>) stockList.clone();
        sortedStocklist.sort(new StockFavorabilityComparator(priceService));
        return sortedStocklist;
    }

    public void updateStockPrice(Stock stock) {
        stock.setPrice(priceService.getPrice(stock));
    }
}
