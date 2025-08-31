package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.CartItem;
import org.foodordering.domain.Product;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartItemServiceImpl extends AbstractService implements CartItemService {

    @Override
    public BigDecimal getTotalPrice(CartItem cartItem) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        ProductService productService = new ProductServiceImpl();

        try{
            conn = getConnection();
            ps= conn.prepareStatement(Sql.TOTAL_PRICE);
            ps.setInt(1, cartItem.getId());

            rs = ps.executeQuery();
            if(rs.next()) {
                int product_id = rs.getInt("product_id");
                Product product = productService.getProductById(product_id);
                BigDecimal price = new BigDecimal(String.valueOf(productService.returnProductPrice(product)));

                int quantity = rs.getInt("quantity");
                return price.multiply(BigDecimal.valueOf(quantity));
            }
        }finally {
            close(rs,ps,conn);
        }return new BigDecimal(0);
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CART_ITEM_BY_CART_ID);
            ps.setInt(1, cartId);
            rs = ps.executeQuery();
            List<CartItem> cartItems = new ArrayList<>();
            while(rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setCart_id(rs.getInt("cart_id"));
                cartItem.setProduct_id(rs.getInt("product_id"));
                cartItems.add(cartItem);
            }return cartItems;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public List<CartItem> getAllCartItems() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try{
        conn = getConnection();
        ps = conn.prepareStatement(Sql.GET_CART_ITEMS);
        rs = ps.executeQuery();
        List<CartItem> list1 = new ArrayList<>();
        while(rs.next()){
            CartItem item = new CartItem();
            item.setId(rs.getInt("id"));
            item.setCart_id(rs.getInt("cart_id"));
            item.setProduct_id(rs.getInt("product_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(getTotalPrice(item));
            list1.add(item);

        }return list1;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public void addCartItem(CartItem item) throws Exception {
        String validate = item.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        try{
        conn = getConnection();
        ps = conn.prepareStatement(Sql.SAVE_CART_ITEM);
        ps.setInt(1, item.getId());
        ps.setInt(2, item.getCart_id());
        ps.setInt(3, item.getProduct_id());
        ps.setInt(4, item.getQuantity());
        ps.executeUpdate();

        }finally {
            close(ps,conn);
        }

    }

    @Override
    public void updateCartItem(CartItem item) throws Exception {
        String validate = item.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        try{
        conn = getConnection();
        ps = conn.prepareStatement(Sql.UPDATE_CART_ITEM);
        ps.setInt(1,item.getCart_id());
        ps.setInt(2,item.getProduct_id());
        ps.setInt(3,item.getQuantity());
        ps.setInt(4, item.getId());
        ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }

    @Override
    public void deleteCartItem(CartItem cartItem) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
        conn = getConnection();
        ps = conn.prepareStatement(Sql.DELETE_CART_ITEM);
        ps.setInt(1, cartItem.getId());
        ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }

    @Override
    public CartItem getCartItemById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
        conn = getConnection();
        ps = conn.prepareStatement(Sql.GET_CART_ITEM_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            CartItem item = new CartItem();
            item.setId(rs.getInt("id"));
            item.setCart_id(rs.getInt("cart_id"));
            item.setProduct_id(rs.getInt("product_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(getTotalPrice(item));
            return item;
        }return null;
        }finally {
            close(ps,conn);
        }
    }


    public static class Sql{
        final static String TOTAL_PRICE="SELECT product_id,quantity FROM cart_item WHERE id=?";
        final static String GET_CART_ITEMS="SELECT * FROM cart_item";
        final static String GET_CART_ITEM_BY_ID="SELECT * FROM cart_item WHERE id=? ";
        final static String SAVE_CART_ITEM = "INSERT INTO cart_item VALUES(?,?,?,?)";
        final static String DELETE_CART_ITEM = "DELETE FROM cart_item WHERE id=?";
        final static String UPDATE_CART_ITEM = "UPDATE cart_item SET cart_id=?,product_id=?,quantity=? WHERE id=?";
        final static String GET_CART_ITEM_BY_CART_ID = "SELECT * FROM cart_item WHERE cart_id =?";
    }
}
