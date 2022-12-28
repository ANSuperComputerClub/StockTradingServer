package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;

/**
 * A strategy that a bot trader will use when trading is automated.
 */
@FunctionalInterface
public interface TradingStrategy {
    /**
     * @param trader The trader to run the strategy on
     * @param orderController the orderController
     * @param priceService the priceService
     * @param stockService the stockService
     */
    void run(Trader trader, OrderController orderController, PriceService priceService, StockService stockService);
}
