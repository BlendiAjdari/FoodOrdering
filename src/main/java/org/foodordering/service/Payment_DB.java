package org.foodordering.service;

import org.foodordering.domain.Payment;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Payment_DB {
    final static private String DB_URL = System.getenv("DB_URL");
    final static private String DB_USER = System.getenv("DB_USER");
    final static private String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static void SavePayment(Payment payment) throws SQLException {

        final String query = "INSERT INTO payments VALUES (?,?,?,?,?,?)";
        try(
                Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setInt(1, payment.getId());
            ps.setInt(2,payment.getOrder_id());
            ps.setInt(3,payment.getAmount().intValue());
            ps.setString(4,payment.getMethod());
            ps.setString(5,payment.getStatus());
            ps.setDate(6, payment.getDate());
            ps.executeUpdate();
        }

    }

    public static List<Payment> getAllPayment() throws SQLException {
        final String query = "SELECT * FROM payments";



        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<Payment> payments2 = new ArrayList<>();

            while(rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setOrder_id(rs.getInt("order_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setMethod(rs.getString("method"));
                payment.setStatus(rs.getString("status"));
                payment.setDate( rs.getDate("date"));
                payments2.add(payment);

            }
            return payments2;
        }


    }
    public static String deletePayment(int id) throws SQLException {
        final String query = "DELETE FROM payments WHERE id=?";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);

        ){
            ps.setInt(1, id);
            ps.executeUpdate();

        }
        return "Deleted";
    }
    public static String updatePaymentdb(Payment payment) throws SQLException {
        final String query = "UPDATE payments SET order_id=?,amount=?,method=?,status=?,date=? WHERE id=?";
        try(
                Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, payment.getOrder_id());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setString(3, payment.getMethod());
            ps.setString(4, payment.getStatus());
            ps.setDate(5, (Date) payment.getDate());
            ps.setInt(6, payment.getId());
            ps.executeUpdate();

        }
        return "Updated";
    }

}
