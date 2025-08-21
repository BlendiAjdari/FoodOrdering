package org.foodordering.service;

import org.foodordering.domain.Costumer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Costumer_DB {
    final static private String DB_URL = System.getenv("DB_URL");
    final static private String DB_USER = System.getenv("DB_USER");
    final static private String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static List<Costumer> getAllcostumers() throws SQLException {
        List<Costumer> costumers=new ArrayList<>();
        final String query="select * from costumers";
        try(Connection connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement ps = connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Costumer costumer=new Costumer();
                costumer.setId(rs.getInt("id"));
                costumer.setName(rs.getString("name"));
                costumer.setEmail(rs.getString("email"));
                costumer.setPhone(rs.getString("phone"));
                costumer.setPassword(String.valueOf(rs.getInt("password")));
                costumers.add(costumer);
            }
        }return costumers;
    }
    public static Costumer saveCostumer(Costumer costumer) throws SQLException{
        final String query ="INSERT INTO costumers VALUES (?,?,?,?,?)";
        try(Connection connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, costumer.getId());
            ps.setString(2, costumer.getName());
            ps.setString(3, costumer.getEmail());
            ps.setString(4, costumer.getPhone());
            ps.setInt(5, costumer.getPassword().hashCode());
            ps.executeUpdate();

        }return costumer;
    }
    public static void deleteCostumer(Costumer costumer) throws SQLException{
        final String query ="DELETE FROM costumers WHERE id=?";
        try(Connection connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, costumer.getId());
            ps.executeUpdate();
        }
    }
    public static void updateCostumer(Costumer costumer) throws SQLException{
        final String query = "UPDATE costumers SET name=?,email=?,phone=?,password=? WHERE id=?";
        try(Connection connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, costumer.getName());
            ps.setString(2, costumer.getEmail());
            ps.setString(3, costumer.getPhone());
            ps.setInt(4, costumer.getPassword().hashCode());
            ps.setInt(5, costumer.getId());
            ps.executeUpdate();
        }
    }
    public static Costumer getCostumerbyID(int id) throws SQLException{
        final String query="select * from costumers where id=?";
        try(Connection connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            Costumer costumer=new Costumer();
            while(rs.next()){
                costumer.setId(rs.getInt("id"));
                costumer.setName(rs.getString("name"));
                costumer.setEmail(rs.getString("email"));
                costumer.setPhone(rs.getString("phone"));
                costumer.setPassword(rs.getString("password"));

            }return costumer;
        }
    }
}
