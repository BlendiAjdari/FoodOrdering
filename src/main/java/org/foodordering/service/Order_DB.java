package org.foodordering.service;

import org.foodordering.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Order_DB {
    final static String DB_URL = System.getenv("DB_URL");
    final static String DB_USER = System.getenv("DB_USER");
    final static String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static List<Order> getAllOrders() throws SQLException {
        final String query = "Select * from orders";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while(rs.next()){
                Order order1 = new Order();
                order1.setId(rs.getInt("id"));
                order1.setAmount(rs.getInt("amount"));
                order1.setDate(rs.getDate("date"));
                order1.setStatus(rs.getString("status"));
                order1.setStore_id(rs.getInt("store_id"));
                order1.setCostumer_id(rs.getInt("costumers_id"));
                order1.setCostumer(Costumer_DB.getCostumerbyID(rs.getInt("costumers_id")));
                order1.setStore(Store_DB.getStorebyID(rs.getInt("store_id")));


                orders.add(order1);

            }return orders;
        }

    }
    public static void saveOrder(Order order) throws SQLException {
        final String query="Insert into Orders (id,amount,date,status,Costumers_id,Store_id) values (?,?,?,?,?,?);";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, order.getId());
            ps.setInt(2, order.getAmount());
            ps.setDate(3,order.getDate());
            ps.setString(4, order.getStatus());
            ps.setInt(5, order.getCostumer_id());
            ps.setInt(6, order.getStore_id());
            ps.executeUpdate();
        }
    }
    public static void updateOrder(Order order) throws SQLException {
        final String query="UPDATE orders SET amount=?,date=?,status=?,Costumers_id=?,Store_id=? WHERE id=?";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);){

            ps.setInt(1, order.getAmount());
            ps.setDate(2,order.getDate());
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getCostumer_id());
            ps.setInt(5, order.getStore_id());
            ps.setInt(6, order.getId());
            ps.executeUpdate();
        }
    }
    public static void deleteOrderdb(Order order) throws SQLException {
        final String query="Delete from Orders where id=?";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, order.getId());
            ps.executeUpdate();
        }
    }
    public static Order getOrderdbyID(int id) throws SQLException {
        final String query = "Select * from Orders where id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Order order = new Order();
           if(rs.next()){
           order.setId(rs.getInt("id"));
           order.setAmount(rs.getInt("amount"));
           order.setDate(rs.getDate("date"));
           order.setStatus(rs.getString("status"));
           order.setCostumer_id(rs.getInt("costumers_id"));
           order.setStore_id(rs.getInt("store_id"));
           order.setCostumer(Costumer_DB.getCostumerbyID(rs.getInt("costumers_id")));
           order.setStore(Store_DB.getStorebyID(rs.getInt("store_id")));}
           return order;

        }
    }
}
