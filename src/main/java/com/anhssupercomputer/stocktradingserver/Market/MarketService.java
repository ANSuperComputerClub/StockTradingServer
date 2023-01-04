package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.DuplicateTickerException;
import com.anhssupercomputer.stocktradingserver.Exceptions.IllegalTransactionException;
import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import com.anhssupercomputer.stocktradingserver.Order.OrderController;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import com.anhssupercomputer.stocktradingserver.Stock.StockService;
import com.anhssupercomputer.stocktradingserver.Trader.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketService {
    private final TraderService traderService;
    private final StockService stockService;
    private final PriceService priceService;
    private final OrderController orderController;
    private Market market;

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
        if (market == null) {
            throw new NoMarketException();
        }

        return market.startMarket();
    }

    /**
     * @return True if successful
     */
    public boolean stopMarket() throws NoMarketException {
        if (market == null) {
            throw new NoMarketException();
        }

        return market.stopMarket();
    }

    /**
     * Creates a new market
     */
    public boolean createMarket(int traders, int stockNumber, int period) throws DuplicateTickerException {
        try {
            // Terminate the old market's thread
            if (market != null) {
                market.terminate();
            }

            market = new Market(traders, stockNumber, period, traderService, stockService, priceService, orderController);
            return true;
        } catch (Exception e) {
            // Returns false if a failure occurs.
            return false;
        } catch (IllegalTransactionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isMarketAlive() {
        return market != null && market.isAlive() && !market.isStopped();
    }
}
