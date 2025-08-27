package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Cart;
import org.foodordering.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl extends AbstractService implements CartService {
    @Override
    public void addCart(Cart cart)  throws Exception {
        String validate = cart.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(Sql.SAVE_CART);
            ps.setInt(1,cart.getId());
            ps.setInt(2,cart.getCustomer_id());
            ps.setTime(3, cart.getCreated_at());
            ps.executeUpdate();
        }finally {
            close(ps,con);
        }

    }
    @Override
    public List<Cart> getAllCarts() throws Exception {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(Sql.GET_ALL_CARTS);
            rs = ps.executeQuery();
            List<Cart> carts = new ArrayList<>();
            CustomerServiceImpl customerService = new CustomerServiceImpl();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setCustomer_id(rs.getInt("costumer_id"));
                cart.setCustomer(customerService.getCustomerById((rs.getInt("costumer_id"))));
                cart.setCreated_at(rs.getTime("created_at"));
                carts.add(cart);

            }return carts;
        }finally {
            close(rs,ps,con);
        }

    }
    @Override
    public void updateCart(Cart cart) throws Exception {
        String validate = cart.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(Sql.UPDATE_CART);
            ps.setInt(1,cart.getCustomer_id());
            ps.setInt(2,cart.getId());
            ps.executeUpdate();
        }finally {
            close(ps,con);
        }
    }
    @Override
    public void deleteCart(Cart cart) throws Exception {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(Sql.DELETE_CART);
            ps.setInt(1,cart.getId());
            ps.executeUpdate();
        }finally {
            close(ps,con);
        }
    }

    @Override
    public Cart getCartById(int id) throws Exception {
        PreparedStatement ps =null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(Sql.GET_CART_BY_ID);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            CustomerServiceImpl customerService = new CustomerServiceImpl();

            if(rs.next()){
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setCustomer_id(rs.getInt("costumer_id"));
                cart.setCustomer(customerService.getCustomerById((rs.getInt("costumer_id"))));
                cart.setCreated_at(rs.getTime("created_at"));
                return cart;
            }
        }finally {
            close(rs,ps,con);
        }
        return null;
    }

    public static class Sql{
        final static String SAVE_CART = "INSERT INTO cart VALUES (?,?,?)";
        final static String DELETE_CART = "DELETE FROM cart WHERE id=?";
        final static String GET_CART_BY_ID = "SELECT * FROM cart WHERE id=?";
        final static String GET_ALL_CARTS = "SELECT * FROM cart";
        final static String UPDATE_CART = "UPDATE cart SET costumer_id=? WHERE id=?";
    }
}
