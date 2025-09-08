package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Checkout;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckoutServiceImpl extends AbstractService implements CheckoutService{

    OrderService orderService = new OrderServiceImpl();
    CustomerService customerService = new CustomerServiceImpl();
    AddressService addressService = new AddressServiceImpl();
    OrderItemService orderItemService = new OrderItemServiceImpl();
    @Override
    public List<Checkout> getCheckouts() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_CHECKOUTS);
            rs = ps.executeQuery();
            List<Checkout> checkouts = new ArrayList<Checkout>();

            while (rs.next()) {
                Checkout checkout = new Checkout();
                checkout.setId(rs.getInt("id"));
                checkout.setCustomer_id(rs.getInt("customer_id"));
                checkout.setAddress_id(rs.getInt("address_id"));
                checkout.setAddress(addressService.getAddressByCustomerId(rs.getInt("customer_id")));
                checkout.setTotalAmount(rs.getBigDecimal("total_amount"));
                List<Order>orders=orderService.getOrdersByCustomerId(rs.getInt("customer_id"));
                checkout.setOrders(orders);
                List<OrderItem>orderItems=orderItemService.getOrderItemsByOrderId(orders.getFirst().getId());

                    checkout.setOrderItems(orderItems);



                checkouts.add(checkout);

            }return checkouts;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public Checkout getCheckoutById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CHECKOUT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                Checkout checkout = new Checkout();
                checkout.setId(rs.getInt("id"));
                checkout.setCustomer_id(rs.getInt("customer_id"));
                checkout.setAddress_id(rs.getInt("address_id"));
                checkout.setAddress(addressService.getAddressByCustomerId(rs.getInt("customer_id")));
                checkout.setTotalAmount(rs.getBigDecimal("total_amount"));
                List<Order>orders=orderService.getOrdersByCustomerId(rs.getInt("customer_id"));
                checkout.setOrders(orders);

                List<OrderItem>orderItems=orderItemService.getOrderItemsByOrderId(orders.getFirst().getId());

                checkout.setOrderItems(orderItems);

                return checkout;

            }return null;
        }finally {
            close(rs,ps,conn);
        }

    }

    @Override
    public void addCheckout(Checkout checkout) throws Exception {
        String validate = checkout.validate();
        if(validate != null){
            throw new Exception(validate);
        }

        PreparedStatement ps = null;
        Connection conn = null;
        OrderService orderService1 = new OrderServiceImpl();


        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_CHECKOUT);
            ps.setInt(1, checkout.getId());
            ps.setInt(2, checkout.getCustomer_id());
            if (orderService.getOrdersByCustomerId(checkout.getCustomer_id()).isEmpty()) {
                throw new Exception("Can't checkout without an Order!");
            }
            ps.setInt(3, checkout.getAddress_id());
            ps.setBigDecimal(4, orderService.orderAmountByCustomerId(checkout.getCustomer_id()));
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
        PreparedStatement ps = null;
        Connection conn = null;

     try {
         conn = getConnection();
         ps = conn.prepareStatement(Sql.UPDATE_CHECKOUT);
         ps.setInt(1,checkout.getCustomer_id());
         ps.setInt(2,addressService.getAddressById(checkout.getCustomer_id()).getId());
         ps.setBigDecimal(3, orderService.orderAmountByCustomerId(checkout.getCustomer_id()));
         ps.setInt(4,checkout.getId());
         ps.executeUpdate();
     }finally {
         close(ps,conn);
     }
    }

    @Override
    public void deleteCheckout(Checkout checkout) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;

       try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_CHECKOUT);
            ps.setInt(1, checkout.getId());
            ps.executeUpdate();
       }finally {
           close(ps,conn);
       }
    }

    @Override
    public int getLastCheckoutId() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_LAST_CHECKOUT_ID);
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
        }finally {
            close(rs,ps,conn);
        }return 0;
    }

    @Override
    public void deleteCheckoutByCustomerId(int customerId) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_CHECKOUT_BY_CUSTOMER_ID);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }
    }


    public static class Sql{
        final static String GET_ALL_CHECKOUTS = "select * from checkout";
        final static String GET_CHECKOUT_BY_ID = "select * from checkout where id = ?";
        final static String SAVE_CHECKOUT = "INSERT INTO checkout VALUES(?,?,?,?)";
        final static String DELETE_CHECKOUT = "delete from checkout where id = ?";
        final static String UPDATE_CHECKOUT = "UPDATE checkout SET customer_id=?,address_id=?,total_amount=? where id=?";
        final static String GET_LAST_CHECKOUT_ID="SELECT id FROM checkout ORDER BY id DESC LIMIT 1";
        final static String DELETE_CHECKOUT_BY_CUSTOMER_ID="DELETE FROM checkout WHERE customer_id = ?";
    }
}
