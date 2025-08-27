package org.foodordering.service;

import org.foodordering.domain.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders() throws Exception;
    Order getOrderById(int id) throws Exception;
    void  addOrder(Order order) throws Exception;
    void updateOrder(Order order) throws Exception;
    void deleteOrder(Order order) throws Exception;
    int getCustomerIdFromCart() throws Exception;
     int StoreIdFromProduct(int id) throws Exception;
}
