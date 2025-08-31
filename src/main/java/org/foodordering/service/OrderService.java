package org.foodordering.service;

import org.foodordering.domain.Order;

import java.math.BigDecimal;
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
    public void amountChange(int id) throws Exception;
    public BigDecimal totalAmount(int id) throws Exception;
    BigDecimal getAmount(int id)throws Exception;
    void deleteAmount(int id) throws Exception;
    int lastOrderId() throws Exception;
    BigDecimal orderAmountByCustomerId(int id) throws Exception;
    void deleteOrderByCustomerId(int id) throws Exception;
    List<Order> getOrdersByCustomerId(int id) throws Exception;
    List<Order>ordersByStore(int id) throws Exception;
}
