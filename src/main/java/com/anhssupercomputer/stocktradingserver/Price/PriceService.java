package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockPriceEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PriceService {
    /**
     * @return Calculated demand based on past price and derivative
     */
    public double getFavorability(Stock stock) {
        ArrayList<StockPriceEntry> history = stock.getPriceHistory();
        double derivative = 0;

        // Calculate derivative
        if(history.size() >= 2) {
            StockPriceEntry entry1 = history.get(0);
            StockPriceEntry entry2 = history.get(1);

            derivative = entry1.getPrice() - entry2.getPrice();
        }

        // number of times in the past 20 price histories that the stock increased from one price to the next
        int riseCount = 0;

        for(int i = 0; i < history.size() - 1; i++) {
            if(history.get(i).getPrice() >= history.get(i + 1).getPrice()) {
                riseCount++;
            }
        }

        // adds the derivative and risecount, adjusted to make half of price changes positive be the midpoint, to get a favorability
        return (riseCount - (history.size() / 2.0)) + derivative;
    }

    public double getPrice(Stock stock) {
        double favorability = getFavorability(stock);

        // 0 means all stock is bought, 1 means all stock is bought
        double supply = 1.0 - (stock.getAvailableVolume() / stock.getTotalVolume());

        double price = favorability + supply * 30.0;

        if(price < 1) {
            price = 1;
        }

        return price;
    }
}
