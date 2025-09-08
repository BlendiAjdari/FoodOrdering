package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Product;
import org.foodordering.domain.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class StoreServiceImpl extends AbstractService implements StoreService {

    @Override
    public void addStore(Store store) throws Exception {
        String validate = store.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(Sql.INSERT_STORE);
            ps.setInt(1, store.getId());
            ps.setString(2, store.getName());
            ps.setString(3, store.getAddress());
            ps.setString(4, store.getContact());
            ps.setString(5, store.getDelivery_options());
            ps.setTime(6, store.getOpens());
            ps.setTime(7, store.getCloses());
            ps.executeUpdate();
        } finally {
            close(ps, con);
        }
    }

    @Override
    public List<Store> getAllStores() throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        try {

            con = getConnection();
            ps = con.prepareStatement(Sql.GET_STORES);
            rs = ps.executeQuery();
            List<Store> stores = new ArrayList<Store>();
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

            }
            return stores;

        }finally {
            close(rs,ps, con);
        }
    }



    @Override
    public void deleteStore(Store store) throws Exception {
        PreparedStatement ps = null;
        Connection con = null;
        try  {
            con = getConnection();
            ps = con.prepareStatement(Sql.DELETE_STORE);
            ps.setInt(1, store.getId());
            ps.executeUpdate();
        }
        finally {
            close(ps, con);
        }
    }

    @Override
    public void updateStore(Store store) throws Exception {
        String validate = store.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection con = null;
        try  {
            con = getConnection();
            ps = con.prepareStatement(Sql.UPDATE_STORE);
            ps.setString(1, store.getName());
            ps.setString(2, store.getAddress());
            ps.setString(3, store.getContact());
            ps.setString(4, store.getDelivery_options());
            ps.setTime(5, store.getOpens());
            ps.setTime(6, store.getCloses());
            ps.setInt(7, store.getId());
            ps.executeUpdate();
        }finally {
            close(ps, con);
        }
    }

    @Override
    public Store getStoreById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try  {
            con = getConnection();
            ps = con.prepareStatement(Sql.GET_STORES_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Store store = new Store();
            while (rs.next()) {
                store.setId(rs.getInt("id"));
                store.setName(rs.getString("name"));
                store.setAddress(rs.getString("address"));
                store.setContact(rs.getString("contact"));
                store.setDelivery_options(rs.getString("delivery_option"));
                store.setOpens(rs.getTime("opens"));
                store.setCloses(rs.getTime("closes"));

            }
            return store;
        }finally {
            close(rs, ps, con);
        }

    }
    @Override
    public List<Store> searchStore(String name)throws  Exception{
    final String query = "SELECT * FROM stores WHERE name LIKE '"+name+"%' ";
    ResultSet rs = null;
    PreparedStatement ps = null;
    Connection con = null;
    try {
        con=getConnection();
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        List<Store> stores = new ArrayList<>();
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
    }finally {
        close(rs, ps, con);
    }
    }

    public static class Sql {
        static final String INSERT_STORE = "INSERT INTO Stores values (?, ?, ?, ?, ?, ?,?)";
        static final String GET_STORES = "SELECT * FROM Stores";
        static final String DELETE_STORE = "DELETE FROM Stores WHERE id = ?";
        static final String UPDATE_STORE = "UPDATE stores SET name=?,address=?,contact=?,delivery_option=?,opens=?,closes=? WHERE id = ?";
        static final String GET_STORES_BY_ID = "SELECT * FROM Stores WHERE id = ?";

    }
}