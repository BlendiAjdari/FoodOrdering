package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemServiceImpl extends AbstractService implements OrderItemService {
    OrderServiceImpl orderService = new OrderServiceImpl();
    ProductServiceImpl  productService = new ProductServiceImpl();
    @Override
    public void addOrderItem(OrderItem orderitem) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
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
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
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
        PreparedStatement ps = null;
        Connection conn = null;
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
        PreparedStatement ps = null;
        Connection conn = null;
        orderService.deleteAmount(getOrderItemById(orderItem.getId()).getOrder_id());
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
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
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
        PreparedStatement ps = null;
        Connection conn = null;

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
    public void deleteAllOrderItemsByOrderId(List<Order>orders) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
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
    @Override
    public List<OrderItem> getOrderItemsByStoreId(int id) throws Exception{
        OrderService orderService = new OrderServiceImpl();
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        List<OrderItem>orderItems = new ArrayList<>();
        try{

        for(int i =0;i<orderService.ordersByStore(id).toArray().length;i++){
            conn=getConnection();
            ps=conn.prepareStatement(Sql.GET_BY_ORDER_ID);
            ps.setInt(1, orderService.ordersByStore(id).get(i).getId());
            rs = ps.executeQuery();
            while (rs.next()){
             OrderItem orderitem = new OrderItem();
             orderitem.setId(rs.getInt("id"));
             orderitem.setOrder_id(rs.getInt("order_id"));
             orderitem.setProduct_id(rs.getInt("product_id"));
             orderitem.setProduct(productService.getProductById(rs.getInt("product_id")));
             orderitem.setQuantity(rs.getInt("quantity"));
             orderitem.setUnit_price(rs.getBigDecimal("unit_price"));
             orderItems.add(orderitem);

            }
        }return orderItems;
        }finally {
            close(rs,ps,conn);
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
