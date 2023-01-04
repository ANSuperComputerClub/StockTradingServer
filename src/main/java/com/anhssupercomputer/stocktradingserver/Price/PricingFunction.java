package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;

import static com.anhssupercomputer.stocktradingserver.Utility.Constants.E;

@FunctionalInterface
public interface PricingFunction {
    double run(Stock stock);
    PricingFunction _default = (stock) -> {
        // TODO Totally refactor this so it works properly
        // Basically randomly increase or decrease by at most 5 percent
        double supplyRatio = 1.0 * stock.getAvailableVolume() / stock.getTotalVolume();
        double entropyFactor = Math.random() * Math.pow(E, 2.5) - Math.pow(E, 2.5) / 2;
        double price = Math.cbrt(Math.pow(E, -Math.pow(supplyRatio, 2)) + entropyFactor * Math.pow(E, -1) - Math.pow(E, -1)); // bad magic number
        return stock.getPrice() * (1 + 0.004 * price);
    };
}
