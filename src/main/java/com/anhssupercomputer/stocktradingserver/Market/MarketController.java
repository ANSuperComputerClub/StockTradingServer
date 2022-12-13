package com.anhssupercomputer.stocktradingserver.Market;

import com.anhssupercomputer.stocktradingserver.Exceptions.NoMarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("market")
public class MarketController {

    private final MarketService service;

    public MarketController(@Autowired MarketService service) {
        this.service = service;
    }

    /**
     * @return True if successful
     */
    @GetMapping("start")
    public boolean startMarket() throws NoMarketException {
        return service.startMarket();
    }

    /**
     * @return True if successful
     */
    @GetMapping("stop")
    public boolean stopMarket() throws NoMarketException {
        return service.stopMarket();
    }

    /**
     * @return True if market is successfully created
     */
    @PostMapping("create")
    public boolean createMarket(@RequestBody int traderNumber, @RequestBody int stockNumber, @RequestBody int period) {
        return service.createMarket(traderNumber, stockNumber, period);
    }

    @GetMapping("alive")
    public boolean isMarketAlive() {
        return service.isMarketAlive();
    }

    @ExceptionHandler
    public Exception handleException(Exception e) {
        return e;
    }
}
