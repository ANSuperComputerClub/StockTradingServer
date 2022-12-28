package com.anhssupercomputer.stocktradingserver.Price;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockPriceEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.anhssupercomputer.stocktradingserver.Price.FavorabilityFunction.defaultFavorabilityFunction;
import static com.anhssupercomputer.stocktradingserver.Price.PricingFunction.defaultPricingFunction;

@Component
public class PriceService {
   private final PricingFunction pricingFunction;
   private final FavorabilityFunction favorabilityFunction;
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
