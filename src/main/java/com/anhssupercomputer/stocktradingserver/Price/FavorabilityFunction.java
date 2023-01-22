package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Exceptions.LambdaException;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockPriceEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * The function to calculate the favorability of a stock
 */
@FunctionalInterface
public interface FavorabilityFunction {
    /**
     * @param stock the stock in question
     * @return the favorability of the stock
     */
    double run(Stock stock);

    /**
     * Simply sums the squares of the total movements and returns
     */
    FavorabilityFunction rise = (stock) -> {
        ArrayList<StockPriceEntry> history = stock.getPriceHistory();
        List<Double> derivatives = new ArrayList<>(history.size() - 1);

        // Calculate derivatives
        for(int i = 0; i < history.size() - 1; i++) {
            var entry1 = history.get(i);
            var entry2 = history.get(i + 1);
            derivatives.add(entry2.getPrice() - entry1.getPrice());
        }

        // Square them
        derivatives = derivatives.stream().map(val -> val * val * val).toList();

        // Add them all up and return
        return derivatives.stream().reduce(Double::sum).orElse(0.0);
    };

    // TODO Ask matthew to explain how this works
    FavorabilityFunction matthewRise = (stock) -> {
        ArrayList<StockPriceEntry> history = stock.getPriceHistory();
        double derivative = 0;

        // Calculate derivative
        if (history.size() >= 2) {
            StockPriceEntry entry1 = history.get(0);
            StockPriceEntry entry2 = history.get(1);

            derivative = entry2.getPrice() - entry1.getPrice();
        }

        // number of times in the past 20 price histories that the stock increased from one price to the next
        int riseCount = 0;

        for (int i = 0; i < history.size() - 1; i++) {
            if (history.get(i).getPrice() >= history.get(i + 1).getPrice()) {
                riseCount++;
            }
        }

        // adds the derivative and risecount, adjusted to make half of price changes positive be the midpoint, to get a favorability
        return (riseCount - (history.size() / 2.0)) + (derivative / stock.getPrice());
    };

    FavorabilityFunction _default = matthewRise;
}
