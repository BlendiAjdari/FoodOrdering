package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Encryption;
import org.foodordering.domain.Ewallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EwalletServiceImpl extends AbstractService implements EwalletService {

    @Override
    public List<Ewallet> getEwallets() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_ALL_EWALLETS);
            rs = ps.executeQuery();
            List<Ewallet> ewallets = new ArrayList<Ewallet>();
            while (rs.next()) {
                Ewallet ewallet = new Ewallet();
                ewallet.setId(rs.getInt("id"));
                ewallet.setUsername(rs.getString("username"));
                ewallet.setPassword(rs.getString("password"));
                ewallet.setCard_id(rs.getInt("card_id"));
                ewallets.add(ewallet);
            }return ewallets;
        }finally {
            close(rs, ps, conn);
        }
    }

    @Override
    public Ewallet getEwallet(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.GET_EWALLET_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

             if(rs.next()) {
                Ewallet ewallet = new Ewallet();
                ewallet.setId(rs.getInt("id"));
                ewallet.setUsername(rs.getString("username"));
                ewallet.setPassword(rs.getString("password"));
                ewallet.setCard_id(rs.getInt("card_id"));
                return ewallet;
            }
        }finally {
            close(rs, ps, conn);
        }return null;
    }

    @Override
    public void addEwallet(Ewallet e) throws Exception {
     PreparedStatement ps = null;
     Connection conn = null;
     String validate = e.validate();
     if(validate != null) {
         throw new Exception(validate);
     }
     try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.ADD_EWALLET);
            ps.setInt(1, e.getId());
            ps.setString(2, Encryption.encrypt(e.getUsername()));
            ps.setString(3, Encryption.encrypt(e.getPassword()));
            ps.setInt(4, e.getCard_id());
            ps.executeUpdate();

     }finally {
         close(ps, conn);
     }
    }

    @Override
    public void updateEwallet(Ewallet e) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_EWALLET);
            ps.setString(1, Encryption.encrypt(e.getUsername()));
            ps.setString(2, Encryption.encrypt(e.getPassword()));
            ps.setInt(3, e.getCard_id());
            ps.setInt(4, e.getId());
            ps.executeUpdate();

        }finally {
            close(ps, conn);
        }
    }

    @Override
    public void deleteEwallet(Ewallet e) throws Exception {
      PreparedStatement ps = null;
      Connection conn = null;
      try {
          conn = getConnection();
          ps = conn.prepareStatement(Sql.DELETE_EWALLET);
          ps.setInt(1, e.getId());
          ps.executeUpdate();
      }finally {
          close(ps, conn);
      }
    }
    public static class Sql{
        final static String GET_ALL_EWALLETS = "select * from e_wallet";
        final static String GET_EWALLET_BY_ID = "select * from e_wallet where id = ?";
        final static String ADD_EWALLET="INSERT INTO e_wallet VALUES(?,?,?,?)";
        final static String DELETE_EWALLET="DELETE FROM e_wallet WHERE id = ?";
        final static String UPDATE_EWALLET="UPDATE e_wallet SET username=?,password=?,card_id=? where id = ?";
    }
}
