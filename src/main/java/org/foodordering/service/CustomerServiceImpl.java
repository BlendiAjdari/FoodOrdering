package org.foodordering.service;


import org.foodordering.common.AbstractService;
import org.foodordering.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerServiceImpl extends AbstractService implements CustomerService{

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> costumers=new ArrayList<>();
        PreparedStatement ps =null;
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CUSTOMERS);
            rs=ps.executeQuery();
            while(rs.next()){
                Customer costumer=new Customer();
                costumer.setId(rs.getInt("id"));
                costumer.setName(rs.getString("name"));
                costumer.setEmail(rs.getString("email"));
                costumer.setPhone(rs.getString("phone"));
                costumer.setPassword(String.valueOf(rs.getInt("password")));
                costumers.add(costumer);
            }
        }finally {
            close(rs,ps,conn);
        }
        return costumers;
    }
    @Override
    public void addCustomer(Customer customer) throws Exception{
        String validate = customer.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps =null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_CUSTOMER);
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getPassword().hashCode());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }
    }
    @Override
    public void deleteCustomer(Customer customer) throws Exception{
        PreparedStatement ps =null;
        Connection conn = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_CUSTOMER);
            ps.setInt(1, customer.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }
    }
    @Override
    public void updateCustomer(Customer customer) throws Exception{
        String validate = customer.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;

        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_CUSTOMER);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setInt(4, customer.getPassword().hashCode());
            ps.setInt(5, customer.getId());
            ps.executeUpdate();
        }finally {
            close(ps,conn);
        }
    }
    @Override
    public Customer getCustomerById(int id) throws Exception{
        PreparedStatement ps=null;
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_CUSTOMER_BY_ID);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            Customer customer=new Customer();
            while(rs.next()){
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setPassword(rs.getString("password"));

            }return customer;
        }finally {
            close(rs,ps,conn);
        }
    }
    public static class Sql{
        final static String GET_CUSTOMERS = "SELECT * FROM costumers";
        final static String DELETE_CUSTOMER ="DELETE FROM costumers WHERE id=?";
        final static String SAVE_CUSTOMER="INSERT INTO costumers VALUES (?,?,?,?,?)";
        final static String UPDATE_CUSTOMER = "UPDATE costumers SET name=?,email=?,phone=?,password=? WHERE id=?";
        final static String GET_CUSTOMER_BY_ID="SELECT * FROM costumers WHERE id=?";
    }
}
