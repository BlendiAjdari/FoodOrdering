package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

public class Courier extends AbstractEntity {
    @SerializedName("courier_name")
    private String name;
    @SerializedName("courier_current_location")
    private String current_location;
    @SerializedName("delivery_status")
    private String status;

    public Courier() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String validate() {
        if(name == null || name.isEmpty()){
            return "Name is required";}
        if(current_location == null || current_location.isEmpty()){
            return "Current location is required";}
        if(status == null || status.isEmpty()){
            return "Status is required";
        }
        return null;
    }
}
