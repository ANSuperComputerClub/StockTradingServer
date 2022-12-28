package com.anhssupercomputer.stocktradingserver.Order;

import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Price.FavorabilityFunction;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.Trader;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final OrderService orderService;

    public OrderController(@Autowired OrderService orderService, @Autowired StockService stockService, @Autowired TraderService traderService) {
        this.stockService = stockService;
        this.traderService = traderService;
        this.orderService = orderService;
    }

    /**
     * Create an order
     *
     * @param ticker   the ticker of the stock to order
     * @param type     the type of transaction
     * @param quantity the quantity of the transaction
     */
    @PostMapping
    public void createOrder(@RequestBody int id, @RequestBody String ticker, @RequestBody String type, @RequestBody int quantity) throws NotFoundException, IllegalTransactionException {
        // Parse data
        Stock stock = stockService.getStockByTicker(ticker);
        OrderType orderType = type.equals("BUY") ? OrderType.BUY : OrderType.SELL;

        // Grab user
        Trader trader = traderService.getTraderById(id);

        // Create order
        Order order = new Order(stock, orderType, quantity);
        orderService.saveOrder(order);

        // TODO: Save it to user
        trader.getPortfolio().addTransaction(order);

        // TODO: Finish implementing this function
    }

    /**
     * @return All orders ever
     */
    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
