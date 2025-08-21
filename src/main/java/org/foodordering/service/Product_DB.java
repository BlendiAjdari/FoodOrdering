package org.foodordering.service;

import org.foodordering.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product_DB {
    final static private String DB_URL = System.getenv("DB_URL");
    final static private String DB_USER = System.getenv("DB_USER");
    final static private String DB_PASSWORD = System.getenv("DB_PASSWORD");
    public static List<Product> getAllProducts() throws SQLException {

        final String query = "select * from Products";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            List<Product> products = new ArrayList<Product>();
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock_quantity(rs.getInt("stock_quantity"));
                product.setStore_id(rs.getInt("store_id"));
                product.setStore(Store_DB.getStorebyID(rs.getInt("store_id")));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(Categories_DB.getCategoryByID(rs.getInt("category_id")));
                product.setImage(rs.getString("image"));
                products.add(product);
            }
            return products;
        }
    }
    public static Product getProduct(int id) throws SQLException {
        final String query = "select * from Products where id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStore_id(rs.getInt("store_id"));
                product.setStore(Store_DB.getStorebyID(rs.getInt("store_id")));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(Categories_DB.getCategoryByID(rs.getInt("category_id")));
                product.setImage(rs.getString("image"));
                return product;
            }
        }
        return null;
    }
    public static void postProducts(Product product) throws SQLException {
        final String query = "INSERT INTO products VALUES (?, ?, ?, ?, ?, ? ,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, product.getId());
            ps.setInt(2, product.getStore_id());
            ps.setInt(3, product.getCategory_id());
            ps.setString(4, product.getName());
            ps.setString(5, product.getDescription());
            ps.setBigDecimal(6, product.getPrice());
            ps.setInt(7,product.getStock_quantity());
            ps.setString(8, product.getImage());
            ps.executeUpdate();
        }
    }
    public static Product updateProduct(Product product) throws SQLException {
        final String query = "UPDATE Products SET store_id=?,category_id=?,name = ?, description = ?, price = ?, image = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);){
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               Product product1 = new Product();
               product1.setId(rs.getInt("id"));
               product1.setStore_id(rs.getInt("store_id"));
               product1.setCategory_id(rs.getInt("category_id"));
               product1.setName(rs.getString("name"));
               product1.setDescription(rs.getString("description"));
               product1.setPrice(rs.getBigDecimal("price"));
               product1.setStock_quantity(rs.getInt("stock_quantity"));


               return product1;
           }
        }
        return null;
    }
    public static void deleteProduct(int id) throws SQLException {
        final String query = "DELETE FROM Products WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, id);
            ps.executeUpdate();

        }
    }
}
