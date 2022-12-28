package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockPriceEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PriceService {
   private final PricingFunction pricingFunction;
   private final FavorabilityFunction favorabilityFunction;

   private static final PricingFunction defaultPricingFunction = (stock) -> {
       // Basically randomly increase or decrease by at most 5 percent
       double price = stock.getPrice();
       double factor = Math.random() * 0.05;
       boolean increaseOrDecrease = Math.random() > 0.5;
       return price * (1 + (increaseOrDecrease ? -factor : factor));
   };

   private static final FavorabilityFunction defaultFavorabilityFunction = (stock) -> {
       ArrayList<StockPriceEntry> history = stock.getPriceHistory();
       double derivative = 0;

       // Calculate derivative
       if (history.size() >= 2) {
           StockPriceEntry entry1 = history.get(0);
           StockPriceEntry entry2 = history.get(1);

           derivative = entry1.getPrice() - entry2.getPrice();
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

    public PriceService() {
        pricingFunction = defaultPricingFunction;
        favorabilityFunction = defaultFavorabilityFunction;
    }

    public PriceService(PricingFunction pricingFunction, FavorabilityFunction favorabilityFunction) {
        this.pricingFunction = pricingFunction;
        this.favorabilityFunction = favorabilityFunction;
    }

    /**
     * Calculates the "favorability" of a stock based on a variety of factors
     *
     * @param stock the stock to calculate it for
     * @return The favorability
     */
    public double getFavorability(Stock stock) {
        return favorabilityFunction.run(stock);
    }

    /**
     * The pricing algorithm that we are using at the moment
     *
     * @param stock the stock to price
     * @return the price
     */
    public double getPrice(Stock stock) {
        return pricingFunction.run(stock);
    }
}
