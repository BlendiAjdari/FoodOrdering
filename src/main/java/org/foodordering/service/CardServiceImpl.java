package org.foodordering.service;

import org.foodordering.common.AbstractService;
import org.foodordering.domain.Card;
import org.foodordering.domain.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CardServiceImpl extends AbstractService implements CardService {
    @Override
    public List<Card> getCards() throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn=getConnection();
            ps=conn.prepareStatement(Sql.GET_CARDS);
            rs=ps.executeQuery();
            List<Card> cards=new ArrayList<Card>();
            while(rs.next()){
                Card card=new Card();
                card.setId(rs.getInt("id"));
                card.setCustomer_id(Integer.parseInt(rs.getString("customer_id")));
                card.setCardNumber(rs.getString("card_number"));
                card.setCardName(rs.getString("name"));
                card.setCardVerificationValue(rs.getString("cvv"));
                card.setExpiryDate(rs.getString("expiry_date"));
                cards.add(card);
            }return cards;

        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public Card getCardById(int id) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn=getConnection();
            ps=conn.prepareStatement(Sql.GET_CARD_BY_ID);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            if(rs.next()){
                Card card=new Card();
                card.setId(rs.getInt("id"));
                card.setCustomer_id(rs.getInt("customer_id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setCardName(rs.getString("name"));
                card.setCardVerificationValue(rs.getString("cvv"));
                card.setExpiryDate(rs.getString("expiry_date"));
                return card;
            }
        }finally {
            close(rs,ps,conn);
        }
        return null;
    }

    @Override
    public void addCard(Card card) throws Exception {
        String validate = card.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
     try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.SAVE_CARD);
            ps.setInt(1,card.getId());
            ps.setInt(2,card.getCustomer_id());
            ps.setString(3,Encryption.encrypt(card.getCardNumber()));
            ps.setString(4,card.getCardName());
            ps.setString(5,Encryption.encrypt(card.getCardVerificationValue()));
            ps.setString(6,card.getExpiryDate());
            ps.executeUpdate();

     }finally {
         close(rs,ps,conn);
     }
    }

    @Override
    public void updateCard(Card card) throws Exception {
        String validate = card.validate();
        if(validate != null){
            throw new Exception(validate);
        }
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.UPDATE_CARD);
            ps.setInt(1,card.getCustomer_id());
            ps.setString(2,Encryption.encrypt(card.getCardNumber()));
            ps.setString(3,card.getCardName());
            ps.setString(4,Encryption.encrypt(card.getCardVerificationValue()));
            ps.setString(5,card.getExpiryDate());
            ps.setInt(6,card.getId());
            ps.executeUpdate();

        }finally {
            close(rs,ps,conn);
        }
    }

    @Override
    public void deleteCard(Card card) throws Exception {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn=getConnection();
            ps = conn.prepareStatement(Sql.DELETE_CARD);
            ps.setInt(1,card.getId());
            ps.executeUpdate();

        }finally {
            close(ps,conn);
        }
    }




    public static class Sql{
        final static String GET_CARDS="select * from card";
        final static String GET_CARD_BY_ID="select * from card where id=?";
        final static String SAVE_CARD="insert into card values(?,?,?,?,?,?)";
        final static String DELETE_CARD="delete from card where id=?";
        final static String UPDATE_CARD="update card set customer_id=?,card_number=?,name=?,cvv=?,expiry_date=? where id=?";
    }
}
