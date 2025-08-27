package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Courier;
import org.foodordering.domain.Delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeliveryServiceImpl extends AbstractService implements DeliveryService{
    private PreparedStatement ps = null;
    private Connection conn = null;
    private  ResultSet rs = null;
    CourierService courierService = new CourierServiceImpl();
    OrderService orderService = new OrderServiceImpl();
    @Override
    public List<Delivery> getAllDelivery() throws Exception {
        try{
            conn =  getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_DELIVERIES);
            rs = ps.executeQuery();
            List<Delivery> deliveries = new ArrayList<>();
            while(rs.next()){
               Delivery delivery = new Delivery();
               delivery.setId(rs.getInt(1));
               delivery.setOrder_id(rs.getInt(2));
               delivery.setOrder(orderService.getOrderById(rs.getInt(2)));
               delivery.setCourier_id(rs.getInt(3));
               delivery.setCourier(courierService.getCourierById(rs.getInt(3)));
               delivery.setStatus(rs.getString(4));
               delivery.setPickup_time(rs.getTime(5));
               delivery.setDelivery_time(rs.getTime(6));
               deliveries.add(delivery);
            }return deliveries;
            }finally {
            close(rs,ps,conn);
        }
        }

    @Override
    public Delivery getDeliveryById(int id) throws Exception {
        try{
            conn =  getConnection();
            ps = conn.prepareStatement(Sql.GET_DELIVERY_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
              Delivery delivery = new Delivery();
              delivery.setId(rs.getInt(1));
              delivery.setOrder_id(rs.getInt(2));
              delivery.setOrder(orderService.getOrderById(rs.getInt(3)));
              delivery.setCourier_id(rs.getInt(3));
              delivery.setCourier(courierService.getCourierById(rs.getInt(4)));
              delivery.setStatus(rs.getString(4));
              delivery.setPickup_time(rs.getTime(5));
              delivery.setDelivery_time(rs.getTime(6));
              return delivery;
            }return null;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public void addDelivery(Delivery delivery) throws Exception {
        String validate = delivery.validate();
        if(validate != null){
            throw new Exception(validate);
        }
     try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_DELIVERY);
            ps.setInt(1, delivery.getId());
            ps.setInt(2, delivery.getOrder_id());
            ps.setInt(3, delivery.getCourier_id());
            ps.setString(4, delivery.getStatus());
            ps.setTime(5, delivery.getPickup_time());
            ps.setTime(6, delivery.getDelivery_time());
            ps.executeUpdate();
     }finally {
         close(rs,ps,conn);
     }
    }

    @Override
    public void updateDelivery(Delivery delivery) throws Exception {
        String validate = delivery.validate();
        if(validate != null){
            throw new Exception(validate);
        }
    try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_DELIVERY);
        ps.setInt(1, delivery.getId());
        ps.setInt(2, delivery.getOrder_id());
        ps.setInt(3, delivery.getCourier_id());
        ps.setString(4, delivery.getStatus());
        ps.setTime(5, delivery.getPickup_time());
        ps.setTime(6, delivery.getDelivery_time());

            ps.executeUpdate();
    }finally {
        close(ps,conn);
    }
    }

    @Override
    public void deleteDelivery(Delivery delivery) throws Exception {
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_DELIVERY);
            ps.setInt(1, delivery.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }
    public static class Sql{
        final static String GET_ALL_DELIVERIES = "SELECT * FROM delivery";
        final static String GET_DELIVERY_BY_ID = "SELECT * FROM delivery WHERE id = ?";
        final static String SAVE_DELIVERY="INSERT INTO delivery VALUES(?,?,?,?,?,?)";
        final static String DELETE_DELIVERY = "DELETE FROM delivery WHERE id = ?";
        final static String UPDATE_DELIVERY = "UPDATE delivery SET order_id = ?,courier_id=?,status=?,pickup_time=?,delivery_time=? WHERE id = ?";
    }
}
