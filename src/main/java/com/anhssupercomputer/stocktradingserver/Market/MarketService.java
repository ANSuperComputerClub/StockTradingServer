package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketService {
    private Market market;
    private TraderService traderService;
    private StockService stockService;
    private PriceService priceService;
    private OrderController orderController;

    public MarketService(@Autowired TraderService traderService, @Autowired StockService stockService, @Autowired PriceService priceService, @Autowired OrderController orderController) {
        this.traderService = traderService;
        this.stockService = stockService;
        this.priceService = priceService;
        this.orderController = orderController;
    }

    /**
     * @return True if successful
     */
    public boolean startMarket() throws NoMarketException {
        if(market == null) {
            throw new NoMarketException();
        }

        return market.startMarket();
    }

    /**
     * @return True if successful
     */
    public boolean stopMarket() throws NoMarketException {
        if(market == null) {
            throw new NoMarketException();
        }

        return market.stopMarket();
    }

    /** Creates a new market */
    public boolean createMarket(int traders, int stockNumber, int period) throws DuplicateTickerException {
        try {
            // Terminate the old market's thread
            if (market != null) {
                market.terminate();
            }

            market = new Market(traders, stockNumber, period, traderService, stockService , priceService, orderController);
            return true;
        } catch(Exception e) {
            // Returns false if a failure occurs.
            return false;
        }
    }

    public boolean isMarketAlive() {
        if(market == null || !market.isAlive() || market.isStopped())
            return false;
        else
            return true;
    }
}
