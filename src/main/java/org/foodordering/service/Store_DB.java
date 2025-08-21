package org.foodordering.service;

import org.foodordering.domain.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Store_DB {


    final static private String DB_URL = System.getenv("DB_URL");
    final static private String DB_USER = System.getenv("DB_USER");
    final static private String DB_PASSWORD = System.getenv("DB_PASSWORD");


    public static void saveStoredb(Store store) throws SQLException {
        final String query = "INSERT INTO Stores values (?, ?, ?, ?, ?, ?,?)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, store.getId());
            ps.setString(2, store.getName());
            ps.setString(3, store.getAddress());
            ps.setString(4, store.getContact());
            ps.setString(5, store.getDelivery_options());
            ps.setTime(6, store.getOpens());
            ps.setTime(7, store.getCloses());
            ps.executeUpdate();
        }
    }
    public static List<Store> getAllStores() throws SQLException {
        final String query = "SELECT * FROM Stores";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<Store>stores = new ArrayList<Store>();
            while (rs.next()) {
            Store store = new Store();
            store.setId(rs.getInt("id"));
            store.setName(rs.getString("name"));
            store.setAddress(rs.getString("address"));
            store.setContact(rs.getString("contact"));
            store.setDelivery_options(rs.getString("delivery_option"));
            store.setOpens(rs.getTime("opens"));
            store.setCloses(rs.getTime("closes"));
            stores.add(store);

            }return stores;

        }
    }
    public static void deleteStoredb(Store store) throws SQLException {
        final String query = "DELETE FROM Stores WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, store.getId());
            ps.executeUpdate();
        }
    }
    public static void updateStore(Store store) throws SQLException {
        final String query = "UPDATE stores SET name=?,address=?,contact=?,delivery_option=?,opens=?,closes=? WHERE id = ? ";
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, store.getName());
            ps.setString(2, store.getAddress());
            ps.setString(3, store.getContact());
            ps.setString(4, store.getDelivery_options());
            ps.setTime(5, store.getOpens());
            ps.setTime(6, store.getCloses());
            ps.setInt(7, store.getId());
            ps.executeUpdate();
        }
    }
    public static Store getStorebyID(int id) throws SQLException {
        final String query = "SELECT * FROM Stores WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Store store = new Store();
            while (rs.next()) {
                store.setId(rs.getInt("id"));
                store.setName(rs.getString("name"));
                store.setAddress(rs.getString("address"));
                store.setContact(rs.getString("contact"));
                store.setDelivery_options(rs.getString("delivery_option"));
                store.setOpens(rs.getTime("opens"));
                store.setCloses(rs.getTime("closes"));

            }return store;
        }
    }

}
