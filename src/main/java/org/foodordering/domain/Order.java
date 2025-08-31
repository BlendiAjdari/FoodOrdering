package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;


import java.math.BigDecimal;
import java.sql.Date;
public class Order extends AbstractEntity {
    @SerializedName("order_amount")
    private BigDecimal amount;
    @SerializedName("order_date")
    private Date date;
    @SerializedName("order_status")
    private String status;
    @SerializedName("c_id")
    private int costumer_id;
    private Customer customer;
    @SerializedName("s_id")
    private int store_id;
    private Store store ;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCostumer_id() {
        return costumer_id;
    }

    public void setCostumer_id(int costumer_id) {
        this.costumer_id = costumer_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public Order(){

    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String validate() {
        if(date == null) {
            return "Date must not be zero";
        }
        if(status == null || status.isEmpty()) {
            return "Status must not be zero";
        }
        if(costumer_id == 0) {
            return "Customer id must not be zero";
        }
        if(store_id == 0) {
            return "Store id must not be zero";
        }
        return null;
    }

    @Override
    public String toString() {
        return  "Order{ id = "+ getId() +" amount="+ amount + " status="+status+" date="+date+"store_id"+store_id+" store="+store+"customer_id"+costumer_id+" customer="+customer+" }";
    }
}
