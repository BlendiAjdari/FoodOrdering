package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Category;
import org.foodordering.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesServiceImpl extends AbstractService implements CategoriesService {
    PreparedStatement ps =null;
    ResultSet rs = null;
    Connection conn = null;

    @Override
    public void addCategory(Category category) throws Exception {
        String validate = category.validate();
        if(validate != null){
            throw new Exception(validate);
        }

        try  {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_CATEGORY);
            ps.setInt(1, category.getId());
            ps.setString(2, category.getName());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }
    }
    @Override
    public List<Category> getAllCategories() throws Exception {

        try  {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_CATEGORIES);
            ResultSet rs = ps.executeQuery();
            List<Category> categories = new ArrayList<>();
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }return categories;
        }finally {
            close(rs,ps,conn);
        }
    }
    @Override
    public void updateCategory(Category category) throws Exception {
        String validate = category.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_CATEGORY);
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }
    }
    @Override
    public void deleteCategory(Category category) throws Exception {
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_CATEGORY);
            ps.setInt(1, category.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }
    }
    @Override
    public Category getCategoryById(int id) throws Exception {

        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CATEGORY_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Category category = new Category();
            while(rs.next()) {
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }return category;
        }finally {
            close(rs,ps,conn);
        }
    }
    public List<Category> getByName(String name) throws SQLException {
        final String query = "SELECT * FROM category WHERE name LIKE '"+name+"%' ";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<Category> categories = new ArrayList<>();
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }return categories;
        }finally {
            close(rs,ps,conn);
        }
    }

    public static class Sql{
        final static  String SAVE_CATEGORY = "INSERT INTO category (id,name) VALUES (?,?)";
        final static    String UPDATE_CATEGORY = "UPDATE category SET name=? WHERE id=?";
        final static    String DELETE_CATEGORY = "DELETE FROM category WHERE id=?";
        final static    String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";
        final static    String GET_ALL_CATEGORIES = "SELECT * FROM category";
    }
}
