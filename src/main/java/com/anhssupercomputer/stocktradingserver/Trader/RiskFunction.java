package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockPriceEntry;
import com.anhssupercomputer.stocktradingserver.Utility.Util;

import java.util.List;


@FunctionalInterface
public interface RiskFunction {
    /**
     * Calculates how risky a stock is based on its price history
     * @return a number between 1 and 0 calculating how risky the stock is, where 1.0 is very risky and 0.0 is very stable.
     */
     double run(Stock stock);

    /**
     * Default risk function for now. Is purely based on the standard deviation of the price history.
     */
    RiskFunction elementaryRiskFunction = (stock) -> {
         // Gather price history
         List<Double> priceHistory = stock.getPriceHistory().stream().map(StockPriceEntry::getPrice).toList();

         // Calculate standard deviation
         double standardDeviation = Util.standardDeviation(priceHistory);
         double range = priceHistory.stream().max(Double::compare).orElse(0.0) - priceHistory.stream().min(Double::compare).orElse(0.0);

         return standardDeviation / range;
    };

     RiskFunction _default = elementaryRiskFunction;
}
