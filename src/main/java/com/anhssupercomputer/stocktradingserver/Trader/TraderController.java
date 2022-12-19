package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import com.anhssupercomputer.stocktradingserver.Price.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trader")
public class TraderController {
    // dependency inject a traderservice
    private final TraderService traderService;
    private final PriceService priceService;

    public TraderController(@Autowired TraderService traderService, @Autowired PriceService priceService) {
        this.traderService = traderService;
        this.priceService = priceService;
    }

    /**
     * Finds a trader with the specififed id
     *
     * @param id the id of the trader
     * @return the Trader, if it is found
     * @throws NotFoundException If the trader is not found
     */
    @GetMapping
    public Trader getTrader(@RequestParam int id) throws NotFoundException {
        return traderService.getTraderById(id);
    }

    @PostMapping
    public String createTrader(@RequestBody String username, @RequestBody String key, @RequestBody double funds) {
        Trader trader = new Trader(username, key, funds, priceService, false);
        traderService.addTrader(trader);
        return "";
    }

    @ExceptionHandler
    public Exception handleException(Exception e) {
        return e;
    }
}
