package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddressServiceImpl extends AbstractService implements AddressService {

    @Override
    public List<Address> getAddresses() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_ADDRESSES);
            rs = ps.executeQuery();
            List<Address> addresses = new ArrayList<Address>();
            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setCustomer_id(rs.getInt("costumer_id"));
                address.setAddress_line(rs.getString("address_line"));
                address.setCity(rs.getString("city"));
                address.setZip(rs.getInt("zip"));
                addresses.add(address);

            }return addresses;
        }finally {
            close(rs,ps,conn);
        }

    }

    @Override
    public Address getAddressById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ADDRESS_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setCustomer_id(rs.getInt("costumer_id"));
                address.setAddress_line(rs.getString("address_line"));
                address.setCity(rs.getString("city"));
                address.setZip(rs.getInt("zip"));
                return address;
            }return null;
        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public void addAddress(Address address) throws Exception {
        String validate = address.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
         try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.SAVE_ADDRESS);
            ps.setInt(1,address.getId());
            ps.setInt(2,address.getCustomer_id());
            ps.setString(3,address.getAddress_line());
            ps.setString(4,address.getCity());
            ps.setInt(5,address.getZip());
            ps.executeUpdate();
         }finally {
             close(rs,ps,conn);
         }
    }

    @Override
    public void updateAddress(Address address) throws Exception {
        String validate = address.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_ADDRESS);

            ps.setInt(1, address.getCustomer_id());
            ps.setString(2,address.getAddress_line());
            ps.setString(3,address.getCity());
            ps.setInt(4,address.getZip());
            ps.setInt(5,address.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }

    }

    @Override
    public void deleteAddress(Address address) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
      try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.DELETE_ADDRESS);
            ps.setInt(1, address.getId());
            ps.executeUpdate();
      }finally {
          close(ps,conn);
      }
    }

    @Override
    public Address getAddressByCustomerId(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ADDRESS_BY_CUSTOMER_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setCustomer_id(rs.getInt("costumer_id"));
                address.setAddress_line(rs.getString("address_line"));
                address.setCity(rs.getString("city"));
                address.setZip(rs.getInt("zip"));
                return address;
            }
        }finally {
            close(rs,ps,conn);
        }return null;
    }

    public static class Sql{
        final static String GET_ADDRESS_BY_CUSTOMER_ID = "SELECT * FROM addresses WHERE costumer_id = ?";
        final static String GET_ALL_ADDRESSES = "select * from addresses";
        final static String GET_ADDRESS_BY_ID = "select * from addresses where id = ?";
        final static String SAVE_ADDRESS="insert into addresses values (?,?,?,?,?)";
        final static String DELETE_ADDRESS="delete from addresses where id = ?";
        final static String UPDATE_ADDRESS="update addresses set costumer_id= ?, address_line=?, city=?,zip=? where id = ?";
    }
}
