package com.anhssupercomputer.stocktradingserver.Order;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderService {
    private final List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    /**
     * Save an order to the order book
     * @param order the order to add
     */
    public void saveOrder(Order order) {
        orders.add(order);
    }

    /**
     * @return a list of orders
     */
    public List<Order> getOrders() {
        return orders;
    }
}
