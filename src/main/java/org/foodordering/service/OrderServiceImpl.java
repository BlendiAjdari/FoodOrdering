package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;
import org.foodordering.domain.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl extends AbstractService implements OrderService {


    @Override
    public List<Order> getAllOrders() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_ORDERS);
            rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            CustomerServiceImpl customerService = new CustomerServiceImpl();
            StoreServiceImpl storeService = new StoreServiceImpl();
            while(rs.next()){
                Order order1 = new Order();
                order1.setId(rs.getInt("id"));
                order1.setAmount(rs.getBigDecimal("amount"));
                order1.setDate(rs.getDate("date"));
                order1.setStatus(rs.getString("status"));
                order1.setStore_id(rs.getInt("store_id"));
                order1.setCostumer_id(rs.getInt("costumers_id"));
                order1.setCustomer(customerService.getCustomerById(rs.getInt("costumers_id")));
                order1.setStore(storeService.getStoreById(rs.getInt("store_id")));


                orders.add(order1);

            }return orders;
        } finally {
            close(rs, ps, conn);
        }

    }
    @Override
    public void addOrder(Order order) throws Exception {
        String validate = order.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        OrderItemService orderItemService = new OrderItemServiceImpl();
        try{
            conn = getConnection();
            ps= conn.prepareStatement(Sql.SAVE_ORDER);
            ps.setInt(1, order.getId());
            ps.setBigDecimal(2, order.getAmount() );
            ps.setDate(3,order.getDate());
            ps.setString(4, order.getStatus());
            ps.setInt(5, order.getCostumer_id());
            ps.setInt(6, order.getStore_id());
            ps.executeUpdate();
        }finally {
            close(ps, conn);
        }
    }
    @Override
    public void updateOrder(Order order) throws Exception {
        String validate = order.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_ORDER);
            ps.setBigDecimal(1, order.getAmount());
            ps.setDate(2,order.getDate());
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getCostumer_id());
            ps.setInt(5, order.getStore_id());
            ps.setInt(6, order.getId());
            ps.executeUpdate();
        }finally {
            close(ps, conn);
        }
    }
    @Override
    public void deleteOrder(Order order) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_ORDER);
            ps.setInt(1, order.getId());
            ps.executeUpdate();
        }finally {
            close(ps, conn);
        }
    }
    @Override
    public Order getOrderById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ORDER_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            CustomerServiceImpl customerService = new CustomerServiceImpl();
            StoreServiceImpl storeService = new StoreServiceImpl();
            Order order = new Order();
           if(rs.next()){
           order.setId(rs.getInt("id"));
           order.setAmount(rs.getBigDecimal("amount"));
           order.setDate(rs.getDate("date"));
           order.setStatus(rs.getString("status"));
           order.setCostumer_id(rs.getInt("costumers_id"));
           order.setStore_id(rs.getInt("store_id"));
           order.setCustomer(customerService.getCustomerById(rs.getInt("costumers_id")));
           order.setStore(storeService.getStoreById(rs.getInt("store_id")));}
           return order;

        }finally {
            close(rs, ps, conn);
        }
    }
    @Override
    public int getCustomerIdFromCart() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CUSTOMER_ID_FROM_CART);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        }finally {
            close(rs, ps, conn);
        }return 0;
    }
    @Override
    public int StoreIdFromProduct(int id) throws Exception{
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_STOREID_FROM_PRODUCT_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("store_id");
            }return 0;
        }finally {
            close(rs, ps, conn);
        }
    }
    public int getTotalPrice(Product product, OrderItem orderItem) throws Exception {
      BigDecimal price = BigDecimal.valueOf( product.getPrice().intValue() *orderItem.getQuantity());
      return price.intValue();
    }
    public static class Sql{
        final static String GET_STOREID_FROM_PRODUCT_ID ="SELECT store_id FROM products WHERE id = ?";
        final static String GET_CUSTOMER_ID_FROM_CART ="SELECT id FROM orders WHERE Costumers_id =(SELECT costumer_id FROM cart)";
        final static String GET_ALL_ORDERS = "select * from orders";
        final static String GET_ORDER_BY_ID = "select * from orders where id=?";
        final static String SAVE_ORDER = "INSERT INTO orders Values(?,?,?,?,?,?)";
        final static String DELETE_ORDER = "DELETE from orders where id=?";
        final static String UPDATE_ORDER = "UPDATE orders SET amount=?,date=?,status=?,Costumers_id=?,Store_id=? WHERE id=?";
    }
}
