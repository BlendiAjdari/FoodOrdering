package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.math.BigDecimal;


public class CartItem extends AbstractEntity {
    @SerializedName("c_id")
    private int cart_id;
    @SerializedName("p_id")
    private int product_id;
    private int quantity;
    private BigDecimal price;


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }







    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem(){

    }

    @Override
    public String validate() {
        if(cart_id == 0){
            return "c_id as cart id is required";
        }
        if(product_id == 0){
            return "p_id as product id is required";
        }
        if(quantity == 0){
            return "quantity is required";
        }
        if(price == null){
            return "price is required";
        }
        return null;
    }
}
