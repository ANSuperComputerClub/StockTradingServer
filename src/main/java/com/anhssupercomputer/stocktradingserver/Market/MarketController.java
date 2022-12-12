package com.anhssupercomputer.stocktradingserver.Market;

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
     * @return True
     */
    @GetMapping("start")
    public boolean startMarket() {

    }

    /**
     * @return True
     */
    @GetMapping("stop")
    public boolean stopMarket() {

    }

    /**
     * @return True if market is successfully created
     */
    @PostMapping("create")
    public boolean createMarket(@RequestBody int traderNumber, @RequestBody int period) {
        return service.createMarket(traderNumber, period);
    }
}
