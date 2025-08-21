package org.foodordering.domain;

import java.sql.Time;

public class Store {
    private int id ;
    private String name;
    private String address;
    private String contact;
    private String delivery_options;
    private Time opens;
    private Time closes;

    public Time getOpens() {
        return opens;
    }

    public void setOpens(Time opens) {
        this.opens = opens;
    }

    public Time getCloses() {
        return closes;
    }

    public void setCloses(Time closes) {
        this.closes = closes;
    }

    public Store() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDelivery_options() {
        return delivery_options;
    }

    public void setDelivery_options(String delivery_options) {
        this.delivery_options = delivery_options;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
