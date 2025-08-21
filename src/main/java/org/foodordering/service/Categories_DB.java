package org.foodordering.service;

import org.foodordering.domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Categories_DB {
    final static private String DB_URL = System.getenv("DB_URL");
    final static private String DB_USER = System.getenv("DB_USER");
    final static private String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static void saveCategory(Category category) throws SQLException {
        final String query = "INSERT INTO category (id,name) VALUES (?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, category.getId());
            ps.setString(2, category.getName());
            ps.executeUpdate();
        }
    }
    public static List<Category> getAllCategorydb() throws SQLException {
        final String query = "SELECT * FROM category";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            List<Category> categories = new ArrayList<>();
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }return categories;
        }
    }
    public static Category updateCategorydb(Category category) throws SQLException {
        final String query = "UPDATE category SET name=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        }return category;
    }
    public static String deleteCategorydb(Category category) throws SQLException {
        final String query = "DELETE FROM category WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, category.getId());
            ps.executeUpdate();
        }
        return "Deleted";
    }
    public static Category getCategoryByID(int id) throws SQLException {
        final String query = "SELECT * FROM category WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query);)
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Category category = new Category();
            while(rs.next()) {
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }return category;
        }
    }
}
