package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController {
    /**
     * The stock service that handles the database, caching, and management of Stocks
     */
    private final StockService service;

    /**
     * Initialize the Stock Controller
     *
     * @param service the StockService to use
     */
    public StockController(@Autowired StockService service) {
        this.service = service;
    }

    /**
     * @return A list of all stocks stored
     */
    @GetMapping("all")
    public List<Stock> getStocks() {
        return service.getStocks();
    }

    /**
     * Creates a new stock
     *
     * @param name        the name of the stock
     * @param ticker      the stock's ticker
     * @param price       the stock's price
     * @param totalVolume the total volume of the stock
     * @return a message if the creation was successful, an error otherwise
     */
    @PostMapping
    public String createStock(@RequestBody String name, @RequestBody String ticker, @RequestBody double price, @RequestBody int totalVolume) throws DuplicateTickerException {
        Stock stock = new Stock(name, ticker, price, totalVolume, service);
        return "Successfully added Stock: \n" + stock;
    }

    /**
     * Returns the stock matching the specified ticker
     * @param ticker the ticker to search for
     * @return the stock with the matching ticker
     * @throws NotFoundException if no stock is found with a matching ticker
     */
    @GetMapping
    public Stock getStockByTicker(@RequestParam String ticker) throws NotFoundException {
        return service.getStockByTicker(ticker);
    }
}
