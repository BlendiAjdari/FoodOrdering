package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.sql.Time;

public class Cart extends AbstractEntity {
    @SerializedName("c_id")
    private int customer_id;
    private Customer customer;
    private Time created_at;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Time getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Time created_at) {
        this.created_at = created_at;
    }

    public Cart(){

    }

    @Override
    public String validate() {
        if(customer_id ==0 ){
            return "Customer id is empty";
        }
        if(created_at == null){
            return "Created at is null";
        }
        return null;
    }
}
