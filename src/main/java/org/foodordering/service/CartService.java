package org.foodordering.service;

import org.foodordering.domain.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts() throws Exception;
    Cart getCartById(int id) throws Exception;
    void addCart(Cart cart) throws Exception;
    void updateCart(Cart cart) throws Exception;
    void deleteCart(Cart cart) throws Exception;
    int redirectToCheckout(int id)throws Exception;
}
