package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Checkout;
import org.foodordering.domain.OrderItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckoutServiceImpl extends AbstractService implements CheckoutService{
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = null;
    OrderService orderService = new OrderServiceImpl();
    CustomerService customerService = new CustomerServiceImpl();
    AddressService addressService = new AddressServiceImpl();
    OrderItemService orderItemService = new OrderItemServiceImpl();
    @Override
    public List<Checkout> getCheckouts() throws Exception {
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_CHECKOUTS);
            rs = ps.executeQuery();
            List<Checkout> checkouts = new ArrayList<Checkout>();

            while (rs.next()) {
                Checkout checkout = new Checkout();
                checkout.setId(rs.getInt("id"));
                checkout.setOrder_id(rs.getInt("order_id"));
                checkout.setOrder(orderService.getOrderById(rs.getInt("order_id")));
                checkout.setOrderItems( orderItemService.getOrderItemsByOrderId(rs.getInt("order_id")));
                checkout.setAddress_id(rs.getInt("address_id"));
                checkout.setAddress(addressService.getAddressByCustomerId(orderService.getOrderById(rs.getInt("order_id")).getCostumer_id()));
                checkouts.add(checkout);

            }return checkouts;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public Checkout getCheckoutById(int id) throws Exception {
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CHECKOUT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Checkout checkout = new Checkout();
                checkout.setId(rs.getInt("id"));
                checkout.setOrder_id(rs.getInt("order_id"));
                checkout.setOrder(orderService.getOrderById(rs.getInt("order_id")));
                checkout.setOrderItems( orderItemService.getOrderItemsByOrderId(rs.getInt("order_id")));
                checkout.setAddress_id(rs.getInt("address_id"));
                checkout.setAddress(addressService.getAddressById(rs.getInt("address_id")));
                return checkout;
            }
        }finally {
            close(rs,ps,conn);
        }
        return null;
    }

    @Override
    public void addCheckout(Checkout checkout) throws Exception {
        String validate = checkout.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_CHECKOUT);
            ps.setInt(1, checkout.getId());
            ps.setInt(2, checkout.getOrder_id());
            ps.setInt(3, addressService.getAddressByCustomerId(orderService.getOrderById(checkout.getOrder_id()).getCostumer_id()).getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }

    @Override
    public void updateCheckout(Checkout checkout) throws Exception {
        String validate = checkout.validate();
        if(validate != null){
            throw new Exception(validate);
        }
     try {
         conn = getConnection();
         ps = conn.prepareStatement(Sql.UPDATE_CHECKOUT);
         ps.setInt(1,checkout.getOrder_id());
         ps.setInt(2,addressService.getAddressById(orderService.getOrderById(checkout.getOrder_id()).getCostumer_id()).getId());
         ps.setInt(3,checkout.getId());
         ps.executeUpdate();
     }finally {
         close(ps,conn);
     }
    }

    @Override
    public void deleteCheckout(Checkout checkout) throws Exception {
       try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_CHECKOUT);
            ps.setInt(1, checkout.getId());
            ps.executeUpdate();
       }finally {
           close(ps,conn);
       }
    }

    public BigDecimal orderItemsTotalCost(OrderItem orderItem) throws Exception {
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.ORDER_TOTAL_COST);
            rs=ps.executeQuery();
            if(rs.next()){
                int n = rs.getInt("order_id");
                orderItemService.getOrderItemsByOrderId(n);

            }
        }finally {
            close(rs,ps,conn);
        }return null;
    }
    public static class Sql{
        final static String ORDER_TOTAL_COST="SELECT order_id from checkout";
        final static String GET_ALL_CHECKOUTS = "select * from checkout";
        final static String GET_CHECKOUT_BY_ID = "select * from checkout where id = ?";
        final static String SAVE_CHECKOUT = "INSERT INTO checkout VALUES(?,?,?)";
        final static String DELETE_CHECKOUT = "delete from checkout where id = ?";
        final static String UPDATE_CHECKOUT = "UPDATE checkout SET order_id=?,address_id=? where id=?";

    }
}
