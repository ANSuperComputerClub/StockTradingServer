package com.anhssupercomputer.stocktradingserver.Stock;

import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

public class StockFavorabilityComparator implements Comparator<Stock> {

    private PriceService priceService;

    public StockFavorabilityComparator(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    public int compare(Stock o1, Stock o2) {
        if(priceService.getFavorability(o1) < priceService.getFavorability(o2)) {
            return -1;
        } else if (priceService.getFavorability(o1) == priceService.getFavorability(o2)) {
            return 0;
        } else {
            return 1;
        }
    }
}
