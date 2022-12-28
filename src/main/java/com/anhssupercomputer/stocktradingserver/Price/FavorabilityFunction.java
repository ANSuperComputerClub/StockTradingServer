package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;

/**
 * The function to calculate the favoriability of a stock
 */
@FunctionalInterface
public interface FavorabilityFunction {
    /**
     * @param stock the stock in question
     * @return the favorability of the stock
     */
    double run(Stock stock);
}
