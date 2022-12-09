package com.anhssupercomputer.stocktradingserver.Order;

import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    private final StockService stockService;

    public OrderController(@Autowired StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Create an order
     * @param ticker   the ticker of the stock to order
     * @param type     the type of transaction
     * @param quantity the quantity of the transaction
     * @return a message verifying the success
     */
    @PostMapping
    public String createOrder(@RequestBody String ticker, @RequestBody String type, @RequestBody int quantity) throws NotFoundException {
        // Parse data
        Stock stock = stockService.getStockByTicker(ticker);
        OrderType orderType = type.equals("BUY") ? OrderType.BUY : OrderType.SELL;

        // Create order
        Order order = new Order(stock, orderType, quantity);

        // TODO: Save it to user
        // TODO: Finish implementing this function
        // Blockers: User storage (so we can add it to the user portfolio)
        return "";
    }
}
