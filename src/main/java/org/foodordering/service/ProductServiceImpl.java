package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.common.OutOfStockException;
import org.foodordering.domain.OrderItem;
import org.foodordering.domain.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductServiceImpl extends AbstractService  implements ProductService {
    StoreServiceImpl storeService = new StoreServiceImpl();
    CategoriesServiceImpl categoriesService = new CategoriesServiceImpl();
    @Override
    public List<Product> getAllProducts() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn=getConnection();
            ps=conn.prepareStatement(Sql.GET_ALL_PRODUCTS);
            List<Product> products = new ArrayList<Product>();
            rs = ps.executeQuery();


            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock_quantity(rs.getInt("stock_quantity"));
                product.setStore_id(rs.getInt("store_id"));
                product.setStore(storeService.getStoreById(rs.getInt("store_id")));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(categoriesService.getCategoryById(rs.getInt("category_id")));
                product.setImage(rs.getString("image"));
                products.add(product);
            }
            return products;
        }finally {
            close(rs,ps,conn);
        }
    }
    @Override
    public Product getProductById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps =conn.prepareStatement(Sql.GET_PRODUCT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Product product = new Product();
            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setStore_id(rs.getInt("store_id"));
                product.setStore(storeService.getStoreById(rs.getInt("store_id")));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(categoriesService.getCategoryById(rs.getInt("category_id")));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock_quantity(rs.getInt("stock_quantity"));
                product.setImage(rs.getString("image"));

            }return product;
        }finally {
            close(rs,ps,conn);
        }

    }
    @Override
    public void addProduct(Product product) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        String validate = product.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_PRODUCT);
            ps.setInt(1, product.getId());
            ps.setInt(2, product.getStore_id());
            ps.setInt(3, product.getCategory_id());
            ps.setString(4, product.getName());
            ps.setString(5, product.getDescription());
            ps.setBigDecimal(6, product.getPrice());
            ps.setInt(7,product.getStock_quantity());
            ps.setString(8, product.getImage());
            ps.executeUpdate();
        }finally {
            close(rs,ps,conn);
        }
    }
    @Override
    public void updateProduct(Product product) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        String validate = product.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_PRODUCT);
            ps.setInt(1, product.getStore_id());
                ps.setInt(2, product.getCategory_id());
                ps.setString(3, product.getName());
                ps.setString(4, product.getDescription());
                ps.setBigDecimal(5, product.getPrice());
                ps.setInt(6, product.getStock_quantity());
                ps.setString(7, product.getImage());
                ps.setInt(8, product.getId());
                ps.executeUpdate();
           }finally {
            close(ps,conn);
        }
    }

    @Override
    public void deleteProduct(Product product) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_PRODUCT);
            ps.setInt(1, product.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }
    }
    @Override
    public Set<Product> getProductByName(String name) throws Exception {
        final String query = "SELECT * FROM Products WHERE name LIKE '"+name+"%' ";
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            Set<Product> products = new HashSet<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock_quantity(rs.getInt("stock_quantity"));
                product.setStore_id(rs.getInt("store_id"));
                product.setStore(storeService.getStoreById(rs.getInt("store_id")));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(categoriesService.getCategoryById(rs.getInt("category_id")));
                product.setImage(rs.getString("image"));
                products.add(product);
            }return products;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public BigDecimal returnProductPrice(Product product) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_PRICE);
            ps.setInt(1, product.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getBigDecimal("price");
            }
        }finally {
            close(rs,ps,conn);
        }return null;
    }

    @Override
    public List<Product> productsByStoreId(int storeId) throws Exception {
        PreparedStatement ps =null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_PRODUCTS_BY_STORE_ID);
            ps.setInt(1, storeId);
            rs =ps.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock_quantity(rs.getInt("stock_quantity"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory(categoriesService.getCategoryById(rs.getInt("category_id")));
                product.setStore_id(rs.getInt("store_id"));
                products.add(product);
            }return products;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public Set<Product>getProductByCategory(String category) throws Exception {
        final String query = "SELECT * FROM products WHERE category_id IN (SELECT id FROM category WHERE name LIKE '" + category + "%') ";
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            Set<Product> products = new HashSet<>();
            while (rs.next()) {
               Product product = new Product();
               product.setId(rs.getInt("id"));
               product.setName(rs.getString("name"));
               product.setDescription(rs.getString("description"));
               product.setPrice(rs.getBigDecimal("price"));
               product.setStock_quantity(rs.getInt("stock_quantity"));
               product.setStore_id(rs.getInt("store_id"));
               product.setStore(storeService.getStoreById(rs.getInt("store_id")));
               product.setCategory_id(rs.getInt("category_id"));
               product.setCategory(categoriesService.getCategoryById(rs.getInt("category_id")));
               product.setImage(rs.getString("image"));
               products.add(product);
            }
            return products;
        } finally {
            close(rs, ps, conn);
        }
    }
    @Override
    public void stockChanges(Product product, OrderItem orderitem) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_STOCK);
            if ((product.getStock_quantity() - orderitem.getQuantity()) < 0) {
                throw new OutOfStockException("Out of Stock");
            } else {

                ps.setInt(1, product.getStock_quantity() - orderitem.getQuantity());
                ps.setInt(2, product.getId());
                ps.executeUpdate();
            }
        }
        finally {
            close(ps,conn);
        }
    }

    public static class Sql{
        final static String GET_PRICE ="SELECT price FROM Products WHERE id = ?";
        final static String GET_ALL_PRODUCTS = "SELECT * FROM Products";
        final static String GET_PRODUCT_BY_ID = "SELECT * FROM Products WHERE id = ?";
        final static String SAVE_PRODUCT = "INSERT INTO Products VALUES(?,?,?,?,?,?,?,?)";
        final static String UPDATE_PRODUCT = "UPDATE Products SET store_id=?,category_id=?,name = ?, description = ?, price = ?,stock_quantity=?, image = ? WHERE id = ?";
        final static String DELETE_PRODUCT = "DELETE FROM Products WHERE id = ?";
        final static String UPDATE_STOCK =  "UPDATE Products SET stock_quantity=? WHERE id = ?";
        final static String GET_PRODUCTS_BY_STORE_ID="SELECT * FROM Products WHERE store_id=?";
    }
}
