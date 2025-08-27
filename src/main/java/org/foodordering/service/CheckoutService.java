package org.foodordering.service;

import org.foodordering.domain.Checkout;

import java.util.List;

public interface CheckoutService {
    List<Checkout> getCheckouts() throws Exception;
    Checkout getCheckoutById(int id) throws Exception;
    void addCheckout(Checkout checkout) throws Exception;
    void updateCheckout(Checkout checkout) throws Exception;
    void deleteCheckout(Checkout checkout) throws Exception;
}

