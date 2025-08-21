package org.foodordering.service;

import org.foodordering.domain.Addresses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Address_DB {
    final static String DB_URL = System.getenv("DB_URL");
    final static String DB_USER = System.getenv("DB_USER");
    final static String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static List<Addresses> getAllAddresses() throws SQLException {
        final String query ="Select * from addresses";
        try(Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query);){
            ResultSet rs = ps.executeQuery();
            List<Addresses> addresses = new ArrayList<Addresses>();
            while(rs.next()){
                Addresses address = new Addresses();
                address.setId(rs.getInt("id"));
                address.setAddress_line(rs.getString("address_line"));
                address.setCostumer_id(rs.getInt("costumer_id"));
                address.setCity(rs.getString("city"));
                address.setZip(rs.getInt("zip"));
                addresses.add(address);



            }return addresses;

        }


    }
    public static void saveAddress(Addresses address) throws SQLException{
        final String query = "INSERT INTO addresses (id,costumer_id,address_line,city,zip) VALUES (?,?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, address.getId());
            ps.setInt(2, address.getCostumer_id());
            ps.setString(3, address.getAddress_line());
            ps.setString(4, address.getCity());
            ps.setInt(5, address.getZip());
            ps.executeUpdate();

        }
    }
    public static void updateAddress(Addresses address) throws SQLException{
        final String query="UPDATE addresses SET costumer_id=?,address_line=?,city=?,zip=? WHERE id=?";
        try(Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
         PreparedStatement ps = conn.prepareStatement(query);){
            ps.setInt(1, address.getCostumer_id());
            ps.setString(2, address.getAddress_line());
            ps.setString(3, address.getCity());
            ps.setInt(4, address.getZip());
            ps.setInt(5, address.getId());
            ps.executeUpdate();
        }
    }
    public static void deleteAddress(Addresses address) throws SQLException{
        final String query = "DELETE FROM addresses WHERE id=?";
        try(Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, address.getId());
            ps.executeUpdate();
        }
    }
    public static List<Addresses> getAddressByCity(String city) throws SQLException{
        final String query = "SELECT * FROM addresses WHERE city=?";
        try(Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query);){
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            List<Addresses> addresses = new ArrayList<>();
            while(rs.next()){
                Addresses address = new Addresses();
                address.setId(rs.getInt("id"));
                address.setCostumer_id(rs.getInt("costumer_id"));
                address.setAddress_line(rs.getString("address_line"));
                address.setCity(rs.getString("city"));
                address.setZip(rs.getInt("zip"));
                addresses.add(address);
            }return addresses;
        }
    }




}
