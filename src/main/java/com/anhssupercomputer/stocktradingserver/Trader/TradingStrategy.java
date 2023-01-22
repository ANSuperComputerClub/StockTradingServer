package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import java.util.ArrayList;
import java.util.HashMap;

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

    TradingStrategy matthewDefault = (trader, orderController, priceService, stockService) -> {
        // Find and sell stocks that have low favorability
        for (Stock stock : new HashMap<>(trader.getPortfolio().getStocks()).keySet()) {
            if (priceService.getFavorability(stock) < 0) {
                try {
                    orderController.createOrder(trader.getId(), stock.getTicker(), "SELL", trader.getPortfolio().getStocks().get(stock));
                } catch (NotFoundException | IllegalTransactionException e) {
                    // System.out.println("Could not make transaction for trader: " + trader.getId() + " for stock: " + stock.getName());
                }
            }
        }

        ArrayList<Stock> rankedStocks = stockService.getRankedStocks();

        for (Stock stock : rankedStocks) {
            double funds = trader.getPortfolio().getFunds();

            if (stock.getPrice() < funds) {
                try {
                    int quantity = (int) (funds / (3 * stock.getPrice()));
                    if(quantity == 0) return;

                    // Makes sure it doesn't buy more that it is allowed
                    if (quantity > stock.getAvailableVolume()) {
                        quantity = stock.getAvailableVolume();
                    }

                    orderController.createOrder(trader.getId(), stock.getTicker(), "BUY", quantity);
                } catch (NotFoundException | IllegalTransactionException e) {
                    // System.out.println("Could not make transaction for trader: " + trader.getId() + " for stock: " + stock.getName());
                }
            }
        }
    };


    /**
     * The Default trading strategy. It's evil, we need to change this.
     */
    TradingStrategy _default = matthewDefault;
}
