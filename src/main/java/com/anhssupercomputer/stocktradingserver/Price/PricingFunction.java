package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;

@FunctionalInterface
public interface PricingFunction {
    double run(Stock stock);
}
