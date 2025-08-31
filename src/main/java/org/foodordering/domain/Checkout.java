package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.math.BigDecimal;
import java.util.List;

public class Checkout extends AbstractEntity {
    @SerializedName("c_id")
    private int customer_id;
    private List<Order> orders;
    private List<OrderItem> orderItems;
    @SerializedName("a_id")
    private int Address_id;
    private Address address;
    private BigDecimal totalAmount;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Checkout() {}




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
        if(customer_id == 0){
            return "customer_id is required";
        }
        if(Address_id == 0){
            return "a_id is required";
        }
    return null;
    }
}
