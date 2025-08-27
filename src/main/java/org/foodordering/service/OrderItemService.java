package org.foodordering.service;

import org.foodordering.domain.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems() throws Exception;
    OrderItem getOrderItemById(int id) throws Exception;
    void addOrderItem(OrderItem orderItem) throws Exception;
    void updateOrderItem(OrderItem orderItem) throws Exception;
    void deleteOrderItem(OrderItem orderItem) throws Exception;
    List<OrderItem> getOrderItemsByOrderId(int id) throws Exception;
    BigDecimal totalAmount(OrderItem orderitem) throws Exception;
}
