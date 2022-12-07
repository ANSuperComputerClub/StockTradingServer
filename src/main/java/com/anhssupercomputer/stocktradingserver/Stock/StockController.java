package com.anhssupercomputer.stocktradingserver.Stock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("stock")
public class StockController {
    @GetMapping
    public Stock getStock() {
        return new Stock(
                "Apple",
                "AAPL",
                new BigDecimal("141.43"),
                5000000,
                0.05
        );
    }
}
