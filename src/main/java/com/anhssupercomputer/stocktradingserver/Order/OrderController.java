package com.anhssupercomputer.stocktradingserver.Order;

import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    /**
     * A dependency-injected stock service instance
     */
    private final StockService stockService;
    /**
     * A dependency-injected trader service instance
     */
    private final TraderService traderService;

    public OrderController(@Autowired StockService stockService, @Autowired TraderService traderService) {
        this.stockService = stockService;
        this.traderService = traderService;
    }

    /**
     * Create an order
     *
     * @param ticker   the ticker of the stock to order
     * @param type     the type of transaction
     * @param quantity the quantity of the transaction
     * @return a message verifying the success
     */
    @PostMapping
    public String createOrder(@RequestBody int id, @RequestBody String ticker, @RequestBody String type, @RequestBody int quantity) throws NotFoundException, IllegalTransactionException {
        // Parse data
        Stock stock = stockService.getStockByTicker(ticker);
        OrderType orderType = type.equals("BUY") ? OrderType.BUY : OrderType.SELL;

        // Grab user
        Trader trader = traderService.getTraderById(id);

        // Create order
        Order order = new Order(stock, orderType, quantity);

        // TODO: Save it to user
        trader.getPortfolio().addTransaction(order);

        // TODO: Finish implementing this function
        // Blockers: User storage (so we can add it to the user portfolio)
        return "";
    }
}
