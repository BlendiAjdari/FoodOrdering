package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.Base;
import org.foodordering.common.AbstractEntity;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Date;

public class Payment extends AbstractEntity {
    @SerializedName("o_id")
    private int order_id;
    @SerializedName("payment_amount")
    private BigDecimal amount;
    @SerializedName("payment_method")
    private String method;
    @SerializedName("payment_status")
    private String status;
    @SerializedName("payment_date")
    private Date date;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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
        if (order_id ==0){
            return "Order id cannot be zero";
        }
        if (amount == null){
            return "Amount cannot be null";
        }
        if (date == null){
            return "Date cannot be null";
        }
        if (method == null || !method.equals("Cash") || !method.equals("Card")  || !method.equals("E-wallet")){
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
