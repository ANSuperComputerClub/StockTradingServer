package com.anhssupercomputer.stocktradingserver.Order;

import com.anhssupercomputer.stocktradingserver.Stock.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class OrderTests {
    private final Stock testStock;
    private final Order testOrder;

    public OrderTests() {
        this.testStock = new Stock("Test stock", "TEST", 1, 1000000);
        this.testOrder = new Order(testStock, OrderType.BUY, 2);
    }
    @Test
    public void verifyConstructor() {
        // Test the order constructor
        assert (testOrder.getStock().equals(testStock));
        assert (testOrder.getQuantity() == 2);
        assert (testOrder.getType() == OrderType.BUY);
    }


}
