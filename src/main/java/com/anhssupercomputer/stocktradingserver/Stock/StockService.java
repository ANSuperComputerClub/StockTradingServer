package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.ArrayList;

/**
 * Service class for the stock
 */
@Component
public class StockService {
    private List<Stock> stockList;

    public StockService() {
        // TODO Acutally load this from a Database (we have to add databse stuff before then)
        stockList = new ArrayList<>();
    }

    /**
     * Returns a list of all stocks in the memory store
     * @return
     */
    public List<Stock> getStockList() { return stockList; }

    /**
     * Adds a stock to the in-memory cache of stocks
     * @param stock
     */
    private void addStockInMemory(Stock stock) {
        stockList.add(stock);
    }

    /**
     * Save stock to storage
     */
    public void saveStock(Stock stock) {
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
}
