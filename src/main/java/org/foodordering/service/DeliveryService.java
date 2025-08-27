package org.foodordering.service;

import org.foodordering.domain.Courier;
import org.foodordering.domain.Delivery;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getAllDelivery() throws Exception;
    Delivery getDeliveryById(int id) throws Exception;
    void addDelivery(Delivery delivery) throws Exception;
    void updateDelivery(Delivery delivery) throws Exception;
    void deleteDelivery(Delivery delivery) throws Exception;
}
