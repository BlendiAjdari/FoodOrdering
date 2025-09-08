package org.foodordering.service;

import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems() throws Exception;
    OrderItem getOrderItemById(int id) throws Exception;
    void addOrderItem(OrderItem orderItem) throws Exception;
    void updateOrderItem(OrderItem orderItem) throws Exception;
    void deleteOrderItem(OrderItem orderItem) throws Exception;
    List<OrderItem> getOrderItemsByOrderId(int id) throws Exception;
    void deleteAllOrderItemsByOrderId(List<Order> orders) throws Exception;

    List<OrderItem> getOrderItemsByStoreId(int id) throws Exception;
}
