package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Courier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourierServiceImpl extends AbstractService implements CourierService{
    PreparedStatement ps=null;
    ResultSet rs=null;
    Connection conn=null;

    @Override
    public List<Courier> getAllCouriers() throws Exception {
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_COURIERS);
            rs=ps.executeQuery();
            List<Courier> couriers=new ArrayList<Courier>();
            while (rs.next()){
                Courier courier = new Courier();
                courier.setId(rs.getInt("id"));
                courier.setName(rs.getString("name"));
                courier.setCurrent_location(rs.getString("current_location"));
                courier.setStatus(rs.getString("status"));
                couriers.add(courier);
            }return couriers;


        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public void addCourier(Courier courier) throws Exception {
        String validate = courier.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.SAVE_COURIER);
            ps.setInt(1, courier.getId());
            ps.setString(2, courier.getName());
            ps.setString(3, courier.getCurrent_location());
            ps.setString(4, courier.getStatus());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }

    @Override
    public void updateCourier(Courier courier) throws Exception {
        String validate = courier.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        try{
            conn=getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_COURIER);
            ps.setString(1, courier.getName());
            ps.setString(2, courier.getCurrent_location());
            ps.setString(3, courier.getStatus());
            ps.setInt(4, courier.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }

    @Override
    public void deleteCourier(Courier courier) throws Exception {
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.DELETE_COURIER);
            ps.setInt(1, courier.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }

    }

    @Override
    public Courier getCourierById(int id) throws Exception {
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.GET_COURIER_BY_ID);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            if(rs.next()){
                Courier courier = new Courier();
                courier.setId(rs.getInt("id"));
                courier.setName(rs.getString("name"));
                courier.setCurrent_location(rs.getString("current_location"));
                courier.setStatus(rs.getString("status"));
                return courier;
            }
        }finally {
            close(rs,ps,conn);
        }return null;
    }

    public static class Sql{
        final static String GET_ALL_COURIERS = "SELECT * FROM courier";
        final static String GET_COURIER_BY_ID = "SELECT * FROM courier WHERE id = ?";
        final static String SAVE_COURIER = "INSERT INTO courier VALUES (?, ?,?,?)";
        final static String DELETE_COURIER = "DELETE FROM courier WHERE id = ?";
        final static String UPDATE_COURIER = "UPDATE courier SET name = ?,current_location=?,status=? WHERE id = ?";
    }
}
