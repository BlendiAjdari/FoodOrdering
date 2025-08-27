package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.util.List;

public class Checkout extends AbstractEntity {
    @SerializedName("o_id")
    private int order_id;
    private Order order;
    private List<OrderItem> orderItems;
    @SerializedName("a_id")
    private int Address_id;
    private Address address;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Checkout() {}

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public int getAddress_id() {
        return Address_id;
    }

    public void setAddress_id(int address_id) {
        Address_id = address_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String validate() {
        if(order_id == 0){
            return "o_id is required";
        }
        if(Address_id == 0){
            return "a_id is required";
        }
    return null;
    }
}
