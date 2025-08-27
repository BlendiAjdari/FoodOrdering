package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.sql.Time;

public class Store extends AbstractEntity {
    @SerializedName("store_name")
    private String name;
    @SerializedName("store_address")
    private String address;
    @SerializedName("store_contact_number")
    private String contact;
    @SerializedName("store_delivery_options")
    private String delivery_options;
    @SerializedName("store_opening_time")
    private Time opens;
    @SerializedName("store_closing_time")
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String validate() {
        if(name == null || name.isEmpty()){
            return "Store name is required";
        }
        if(address == null || address.isEmpty()){
            return "Store address is required";
        }
        if(contact==null || contact.isEmpty()){
            return "Store contact is required";
        }
        if(delivery_options==null || delivery_options.isEmpty()){
            return "Store delivery_options is required";
        }
        if (opens == null){
            return "Store opening time is required";
        }
        if (closes == null){
            return "Store closing time is required";
        }
        return null;
    }
}
