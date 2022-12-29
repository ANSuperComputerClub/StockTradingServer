package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;

@FunctionalInterface
public interface PricingFunction {
    double run(Stock stock);
    PricingFunction _default = (stock) -> {
        // Basically randomly increase or decrease by at most 5 percent
        double price = stock.getPrice();
        double factor = Math.random() * 0.05;
        boolean increaseOrDecrease = Math.random() > 0.5;
        return price * (1 + (increaseOrDecrease ? -factor : factor));
    };
}
