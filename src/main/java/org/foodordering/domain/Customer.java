package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

public class Customer extends AbstractEntity {
    @SerializedName("customer_name")
    private String name;
    @SerializedName("customer_email")
    private String email;
    @SerializedName("customer_phone_number")
    private String phone;
    @SerializedName("customer_password")
    private String password;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Customer(){}

    public Customer(String name,String email,String phone,String password){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
    }
    @Override
    public String validate() {
        if(name==null||name.isEmpty()){
            return "Customer name is empty";
        }
        if(email==null||email.isEmpty()){
            return "Customer email is empty";
        }
        if(phone==null||phone.isEmpty()){
            return "Customer phone number is empty";
        }
        if(password==null||password.isEmpty()){
            return "Customer password is empty";
        }
        return null;
    }
}
