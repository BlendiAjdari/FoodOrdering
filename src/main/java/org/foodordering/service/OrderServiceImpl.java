package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Checkout;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;

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
            amountChange(order.getId());
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
    public BigDecimal totalAmount(int id) throws Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ORDER_ITEM_FROM_ORDER_ID);
            ps.setInt(1,id);

            rs = ps.executeQuery();
            if(rs.next()){


                BigDecimal n=new BigDecimal(rs.getInt("quantity")).multiply(rs.getBigDecimal("unit_price"));
                return n.add(n.multiply(BigDecimal.valueOf(0.18)));
            }
        }finally {
            close(rs, ps, conn);
        }return new BigDecimal(0);

    }
    @Override
    public void amountChange(int id) throws Exception{
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn= getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_AMOUNT);
            ps.setBigDecimal(1,getAmount(id).add(totalAmount(id)));
            ps.setInt(2,id);
            ps.executeUpdate();
        }finally {
            close( ps, conn);
        }
    }
    @Override
    public BigDecimal getAmount(int id)throws Exception{
        PreparedStatement ps=null;
        Connection conn=null;
        ResultSet rs=null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_AMOUNT);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            if(rs.next()){
                if(rs.getBigDecimal("amount")==null){
                    return BigDecimal.ZERO;
                }else{
                return rs.getBigDecimal("amount");}
            }else {
                return BigDecimal.ZERO;
            }
        }finally {
            close(rs, ps, conn);
        }

    }
    @Override
    public void deleteAmount(int id) throws Exception{
     PreparedStatement ps=null;
     Connection conn=null;
     try {  if(getAmount(id).subtract(totalAmount(id)).compareTo(BigDecimal.ZERO)<0){
          throw new Exception("Amount can't be negative");
     }else{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_AMOUNT);
            ps.setBigDecimal(1,(getAmount(id).subtract(totalAmount(id))));
            ps.setInt(2,id);
            ps.executeUpdate();}
     }finally {
         close( ps, conn);
     }
    }

    @Override
    public int lastOrderId() throws Exception {
        PreparedStatement ps =null;
        ResultSet rs=null;
        Connection conn=null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_LAST_ORDER_ID);
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        }finally {
            close(rs, ps, conn);
        }return 0;
    }

    @Override
    public BigDecimal orderAmountByCustomerId(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn=null;
        ResultSet rs = null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_AMOUNT_FROM_CUSTOMER_ID);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            BigDecimal amount = BigDecimal.ZERO;
            while(rs.next()){
                if(rs.getBigDecimal("amount")!=null){
                    amount = rs.getBigDecimal("amount").add(amount);
                }
            }return amount;
        }finally {
            close(rs, ps, conn);
        }
    }

    @Override
    public void deleteOrderByCustomerId(int id) throws Exception {
        PreparedStatement ps =null;
        Connection conn=null;
        CheckoutService checkoutService = new CheckoutServiceImpl();
        OrderItemService orderItemService = new OrderItemServiceImpl();
        try {
            conn = getConnection();
            orderItemService.deleteAllOrderItemsByOrderId(getOrdersByCustomerId(id));
            checkoutService.deleteCheckoutByCustomerId(id);
            ps = conn.prepareStatement(Sql.DELETE_ORDER_FROM_CUSTOMER_ID);
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            close(ps, conn);
        }
    }

    @Override
    public List<Order> getOrdersByCustomerId(int id) throws Exception {
        PreparedStatement ps =null;
        ResultSet rs = null;
        Connection conn=null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_ORDERS_FROM_CUSTOMER_ID);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCostumer_id(rs.getInt("Costumers_id"));
                order.setDate(rs.getDate("date"));
                order.setStatus(rs.getString("status"));
                order.setStore_id(rs.getInt("store_id"));
                order.setAmount(rs.getBigDecimal("amount"));
                orders.add(order);
            }return orders;
        }finally {
            close(rs, ps, conn);
        }

    }

    @Override
    public List<Order> ordersByStore(int id) throws Exception {
        PreparedStatement ps =null;
        ResultSet rs = null;
        Connection conn=null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_ORDERS_BY_STORE_ID);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            CustomerServiceImpl customerService = new CustomerServiceImpl();
            StoreServiceImpl storeService = new StoreServiceImpl();
            List<Order> orders = new ArrayList<>();
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setAmount(rs.getBigDecimal("amount"));
                order.setDate(rs.getDate("date"));
                order.setStatus(rs.getString("status"));
                order.setCostumer_id(rs.getInt("costumers_id"));
                order.setStore_id(rs.getInt("store_id"));
                order.setCustomer(customerService.getCustomerById(rs.getInt("costumers_id")));
                order.setStore(storeService.getStoreById(rs.getInt("store_id")));
                orders.add(order);
            }return orders;
        }finally {
            close(rs, ps, conn);
        }
    }


    public static class Sql{
        final static String GET_AMOUNT = "SELECT amount FROM orders WHERE id =?";
        final static String UPDATE_AMOUNT = "UPDATE orders SET amount=? WHERE id=?";
        final static String GET_ORDER_ITEM_FROM_ORDER_ID ="SELECT quantity,unit_price FROM order_item WHERE ORDER_ID = ? ORDER BY id DESC LIMIT 1";
        final static String GET_ALL_ORDERS = "select * from orders";
        final static String GET_ORDER_BY_ID = "select * from orders where id=?";
        final static String SAVE_ORDER = "INSERT INTO orders Values(?,?,?,?,?,?)";
        final static String DELETE_ORDER = "DELETE from orders where id=?";
        final static String UPDATE_ORDER = "UPDATE orders SET amount=?,date=?,status=?,Costumers_id=?,Store_id=? WHERE id=?";
        final static String GET_LAST_ORDER_ID = "select id from orders ORDER BY id DESC LIMIT 1";
        final static String GET_AMOUNT_FROM_CUSTOMER_ID="SELECT amount FROM orders WHERE Costumers_id=?";
        final static String DELETE_ORDER_FROM_CUSTOMER_ID = "DELETE FROM orders WHERE Costumers_id=?";
        final static String GET_ORDERS_FROM_CUSTOMER_ID="SELECT * FROM orders WHERE Costumers_id=?";
        final static String GET_ORDERS_BY_STORE_ID="SELECT * FROM orders WHERE store_id=?";
    }
}
