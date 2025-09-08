package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Payment extends AbstractEntity {
    @SerializedName("customer_id")
    private int customerId;
    @SerializedName("payment_amount")
    private BigDecimal amount;
    @SerializedName("payment_method")
    private String method;
    @SerializedName("payment_status")
    private String status;
    @SerializedName("payment_date")
    private Date date;
    @SerializedName("card_id")
    private int card_id;
    @SerializedName("e_wallet_id")
    private int e_walletId;

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getE_walletId() {
        return e_walletId;
    }

    public void setE_walletId(int e_walletId) {
        this.e_walletId = e_walletId;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String validate() {
        if(customerId == 0){
            return "customer id is required";
        }
        if (amount == null){
            return "Amount cannot be null";
        }
        if (date == null){
            return "Date cannot be null";
        }
        if ( method==null||method.isEmpty()){
            return "Method can't be empty";
        }
        else if(method.equals("Card")||method.equals("E-Wallet")||method.equals("Cash")) {

        }else{
            return "Method not allowed";
        }
        if (status == null){
            return "Status cannot be null";
        }
        return null;
    }



    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Payment() {}


}
