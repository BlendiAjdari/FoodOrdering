package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

public class Address extends AbstractEntity {
    @SerializedName("c_id")
    private int customer_id;
    private String address_line;
    private String city;
    private int zip;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public Address(){}

    @Override
    public String validate() {
        if(customer_id == 0){
            return "Customer ID cannot be empty";
        }
        if(address_line == null){
            return "Address line cannot be empty";
        }
        if(city == null){
            return "City cannot be empty";
        }
        if(zip == 0){
            return "Zip code cannot be empty";
        }
        return null;
    }
}
