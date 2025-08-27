package org.foodordering.domain;

import org.foodordering.common.AbstractEntity;

import java.sql.Time;

public class Delivery extends AbstractEntity {
    private int order_id;
    private Order order;
    private int courier_id;
    private Courier courier;
    private String status;
    private Time pickup_time;
    private Time delivery_time;

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

    public int getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(int courier_id) {
        this.courier_id = courier_id;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(Time pickup_time) {
        this.pickup_time = pickup_time;
    }

    public Time getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Time delivery_time) {
        this.delivery_time = delivery_time;
    }

    @Override
    public String validate() {
        if(order_id ==0){
            return "order_id is null";
        }
        if(courier_id ==0){
            return "courier_id is null";
        }
        if(status == null || status.isEmpty()){
            return "Status is empty";
        }
        if(pickup_time == null){
            return "pickup_time is null";
        }
        if(delivery_time == null){
            return "delivery_time is null";
        }
        return null;
    }
}
