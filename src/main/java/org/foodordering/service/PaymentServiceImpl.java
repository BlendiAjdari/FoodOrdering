package org.foodordering.service;

import org.foodordering.common.AbstractEntity;
import org.foodordering.common.AbstractService;
import org.foodordering.domain.Payment;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImpl extends AbstractService implements PaymentService {

    private PreparedStatement ps=null;
    private ResultSet rs=null;
    private Connection conn=null;
    @Override
    public void addPayment(Payment payment) throws Exception {
        String validate = payment.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.SAVE_PAYMENT);
            ps.setInt(1, payment.getId());
            ps.setInt(2,payment.getOrder_id());
            ps.setBigDecimal(3,payment.getAmount());
            ps.setString(4,payment.getMethod());
            ps.setString(5,payment.getStatus());
            ps.setDate(6, payment.getDate());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }
    @Override
    public List<Payment> getAllPayments() throws Exception {
        try {
            conn=getConnection();
            ps=conn.prepareStatement(Sql.GET_ALL_PAYMENTS);
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
        }finally {
            close(rs,ps,conn);
        }


    }
    @Override
    public void deletePayment(Payment payment) throws Exception {

        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.DELETE_PAYMENT);
            ps.setInt(1, payment.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }
    }

    @Override
    public Payment getPaymentById(int id) throws Exception {
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_PAYMENT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setOrder_id(rs.getInt("order_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setMethod(rs.getString("method"));
                payment.setStatus(rs.getString("status"));
                payment.setDate( rs.getDate("date"));
                return payment;
            }
            }finally {
            close(rs,ps,conn);
        }
        return null;
    }

    @Override
    public void updatePayment(Payment payment) throws Exception {
        String validate = payment.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_PAYMENT);
            ps.setInt(1, payment.getOrder_id());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setString(3, payment.getMethod());
            ps.setString(4, payment.getStatus());
            ps.setDate(5, (Date) payment.getDate());
            ps.setInt(6, payment.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }

    }
    public static class Sql{
        final static String GET_ALL_PAYMENTS = "SELECT * FROM payments";
        final static String GET_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id=?";
        final static String SAVE_PAYMENT = "INSERT INTO payments VALUES (?,?,?,?,?,?)";
        final static String DELETE_PAYMENT = "DELETE FROM payments WHERE id=?";
        final static String UPDATE_PAYMENT = "UPDATE payments SET order_id=?,amount=?,method=?,status=?,date=? WHERE id=?";
    }

}
