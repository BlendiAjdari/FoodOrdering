package org.foodordering.service;

import org.foodordering.domain.Courier;

import java.util.List;

public interface CourierService {
    List<Courier> getAllCouriers() throws Exception;
    Courier getCourierById(int id) throws Exception;
    void addCourier(Courier courier) throws Exception;
    void updateCourier(Courier courier) throws Exception;
    void deleteCourier(Courier courier) throws Exception;

}
