package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.common.TotalAmountException;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderItemServiceImpl extends AbstractService implements OrderItemService {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = null;
    OrderServiceImpl orderService = new OrderServiceImpl();
    ProductServiceImpl  productService = new ProductServiceImpl();
    @Override
    public void addOrderItem(OrderItem orderitem) throws Exception {
        String validate = orderitem.validate();
        if(validate != null){
            throw new Exception(validate);
        }

        ProductService productService=new ProductServiceImpl();
    try{

        conn = getConnection();
        ps = conn.prepareStatement(Sql.SAVE_ORDER_ITEMS);
        ps.setInt(1, orderitem.getId());
        ps.setInt(2,orderitem.getOrder_id());
        ps.setInt(3,orderitem.getProduct_id());
        ps.setInt(4,orderitem.getQuantity());
        ps.setBigDecimal(5,orderitem.getUnit_price());
        productService.stockChanges(productService.getProductById(orderitem.getProduct_id()),orderitem);
        ps.executeUpdate();
        orderService.amountChange(orderitem.getOrder_id());
    }finally {
        close(ps,conn);
    }

    }
    @Override
    public List<OrderItem> getAllOrderItems() throws Exception {
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_ORDERED_ITEMS);
            rs = ps.executeQuery();
            List<OrderItem> ordereditems = new ArrayList<>();
            while (rs.next()) {
                OrderItem orderitem = new OrderItem();
                orderitem.setId(rs.getInt("id"));
                orderitem.setOrder_id(rs.getInt("order_id"));
                orderitem.setOrder(orderService.getOrderById(rs.getInt("order_id")));
                orderitem.setProduct_id(rs.getInt("product_id"));
                orderitem.setProduct(productService.getProductById(rs.getInt("product_id")));
                orderitem.setQuantity(rs.getInt("quantity"));
                orderitem.setUnit_price(rs.getBigDecimal("unit_price"));
                ordereditems.add(orderitem);
            }return  ordereditems;
        }finally {
            close(rs,ps,conn);
        }

    }
    @Override
    public void updateOrderItem(OrderItem orderItem) throws Exception {
        String validate = orderItem.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        OrderService orderService = new OrderServiceImpl();
        orderService.amountChange(orderItem.getOrder_id());
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_ORDERED_ITEMS);
             ps.setInt(1, orderItem.getOrder_id());
             ps.setInt(2, orderItem.getProduct_id());
             ps.setInt(3, orderItem.getQuantity());
             ps.setBigDecimal(4, orderItem.getUnit_price());
             ps.setInt(5, orderItem.getId());
             ps.executeUpdate();


        }finally {
            close(ps,conn);
        }
    }
    @Override
    public void deleteOrderItem(OrderItem orderItem) throws Exception {
        orderService.deleteAmount(orderItem.getOrder_id());
        try{

            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_ORDER_ITEMS);
            ps.setInt(1, orderItem.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }

    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int id) throws Exception {
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_BY_ORDER_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            List<OrderItem>orderedItems = new ArrayList<>();
            while(rs.next()) {
                OrderItem orderitem = new OrderItem();
                orderitem.setId(rs.getInt("id"));
                orderitem.setOrder_id(rs.getInt("order_id"));
                //orderitem.setOrder(orderService.getOrderById(rs.getInt("order_id")));
                orderitem.setProduct_id(rs.getInt("product_id"));
                orderitem.setProduct(productService.getProductById(rs.getInt("product_id")));
                orderitem.setQuantity(rs.getInt("quantity"));
                orderitem.setUnit_price(rs.getBigDecimal("unit_price"));
                orderedItems.add(orderitem);

            }return orderedItems;

        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public OrderItem getOrderItemById(int id) throws Exception {

        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ORDERED_ITEM_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                OrderItem orderitem = new OrderItem();
                orderitem.setId(rs.getInt("id"));
                orderitem.setOrder_id(rs.getInt("order_id"));
                orderitem.setOrder(orderService.getOrderById(rs.getInt("order_id")));
                orderitem.setProduct_id(rs.getInt("product_id"));
                orderitem.setProduct(productService.getProductById(rs.getInt("product_id")));
                orderitem.setQuantity(rs.getInt("quantity"));
                orderitem.setUnit_price(rs.getBigDecimal("unit_price"));
                return orderitem;
            }
        }finally {
            close(ps,conn);
        }return null;
    }
    @Override
    public BigDecimal totalAmount(OrderItem orderitem) throws Exception {
        if(orderitem !=null){
        if(orderitem.getUnit_price() != null && orderitem.getQuantity()!=0 ){
            return  orderitem.getUnit_price().multiply(new BigDecimal(orderitem.getQuantity()));
        }else {
            throw new TotalAmountException("Unit price or item quantity cannot be zero");
        }
        }
        return null;
    }

    @Override
    public void deleteAllOrderItemsByOrderId(List<Order>orders) throws Exception {
        try{
            conn=getConnection();
            ps=conn.prepareStatement(Sql.DELETE_ITEMS_BY_ORDER_ID);
            for(Order order:orders){
            ps.setInt(1, order.getId());
                ps.executeUpdate();}



        }finally {
            close(ps,conn);
        }
    }


    public static class Sql{
        final static String GET_ALL_ORDERED_ITEMS = "SELECT * FROM order_item";
        final static String GET_ORDERED_ITEM_ID = "SELECT * FROM order_item WHERE id = ?";
        final static String UPDATE_ORDERED_ITEMS = "UPDATE order_item SET order_id=?,product_id=?,quantity=?,unit_price=? WHERE id=?";
        final static String SAVE_ORDER_ITEMS = "INSERT INTO order_item VALUES(?,?,?,?,?)";
        final static String DELETE_ORDER_ITEMS = "DELETE FROM order_item WHERE id=?";
        final static String GET_BY_ORDER_ID= "SELECT * FROM order_item WHERE order_id = ?";
        final static String DELETE_ITEMS_BY_ORDER_ID = "DELETE FROM order_item WHERE order_id = ?";
    }


}
