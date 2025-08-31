package org.foodordering.service;

import org.foodordering.domain.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {
    List<CartItem> getAllCartItems() throws Exception;
    CartItem getCartItemById(int id) throws Exception;
    void addCartItem(CartItem cartItem) throws Exception;
    void updateCartItem(CartItem cartItem) throws Exception;
    void deleteCartItem(CartItem cartItem) throws Exception;
    BigDecimal getTotalPrice(CartItem cartItem) throws Exception;
    List<CartItem>getCartItemsByCartId(int cartId) throws Exception;
}
