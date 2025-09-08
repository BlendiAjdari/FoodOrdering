package org.foodordering.service;

import org.foodordering.common.AbstractEntity;
import org.foodordering.common.AbstractService;
import org.foodordering.domain.Order;
import org.foodordering.domain.Payment;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImpl extends AbstractService implements PaymentService {

    OrderService orderService=new OrderServiceImpl();
    @Override
    public void addPayment(Payment payment) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        String validate = payment.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.SAVE_PAYMENT);
            ps.setInt(1, payment.getId());
            ps.setInt(2,payment.getCustomerId());
            ps.setInt(3,payment.getCard_id());
            ps.setInt(4,payment.getE_walletId());
            ps.setBigDecimal(5,payment.getAmount());
            ps.setString(6,payment.getMethod());
            ps.setString(7,payment.getStatus());
            ps.setDate(8, payment.getDate());
            if(payment.getAmount().equals(new BigDecimal("0.00"))){
                throw new Exception("Payment cant be done , if no Orders!");
            }else{
            ps.executeUpdate();}
        }finally {
            close(ps,conn);
        }

    }
    @Override
    public List<Payment> getAllPayments() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn=getConnection();
            ps=conn.prepareStatement(Sql.GET_ALL_PAYMENTS);
            rs = ps.executeQuery();
            List<Payment> payments2 = new ArrayList<>();

            while(rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setCustomerId(rs.getInt("customer_id"));
                payment.setCard_id(rs.getInt("card_id"));
                payment.setE_walletId(rs.getInt("e_wallet_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setMethod(rs.getString("method"));
                payment.setStatus(rs.getString("status"));
                payment.setOrders(orderService.getOrdersByCustomerId(rs.getInt("customer_id")));
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
        PreparedStatement ps = null;
        Connection conn = null;
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
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_PAYMENT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setCustomerId(rs.getInt("customer_id"));
                payment.setCard_id(rs.getInt("card_id"));
                payment.setE_walletId(rs.getInt("e_wallet_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setMethod(rs.getString("method"));
                payment.setStatus(rs.getString("status"));
                payment.setOrders(orderService.getOrdersByCustomerId(rs.getInt("customer_id")));
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
        PreparedStatement ps = null;
        Connection conn = null;
        String validate = payment.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_PAYMENT);
            ps.setInt(1, payment.getCustomerId());
            ps.setInt(2, payment.getCard_id());
            ps.setInt(3, payment.getE_walletId());
            ps.setBigDecimal(4, payment.getAmount());
            ps.setString(5, payment.getMethod());
            ps.setString(6, payment.getStatus());
            ps.setDate(7, (Date) payment.getDate());
            ps.setInt(8, payment.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }

    }
    public static class Sql{
        final static String GET_ALL_PAYMENTS = "SELECT * FROM payments";
        final static String GET_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id=?";
        final static String SAVE_PAYMENT = "INSERT INTO payments VALUES (?,?,?,?,?,?,?,?)";
        final static String DELETE_PAYMENT = "DELETE FROM payments WHERE id=?";
        final static String UPDATE_PAYMENT = "UPDATE payments SET customer_id=?,card_id=?,e_wallet_id=?,amount=?,method=?,status=?,date=? WHERE id=?";
    }

}
