package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.math.BigDecimal;

public class OrderItem extends AbstractEntity {
    @SerializedName("o_id")
    private int order_id;
    private Order order;
    @SerializedName("p_id")
    private int product_id;
    private Product product;

    private int quantity;
    @SerializedName("item_price")
    private BigDecimal unit_price;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public OrderItem() {

    }

    @Override
    public String validate() {
        if (order_id == 0){
            return "o_id is missing";
        }
        if (product_id == 0){
            return "p_id is missing";
        }
        if (quantity == 0){
            return "quantity is missing";
        }
        if (unit_price == null){
            return "item_price is missing";
        }
        return null;
    }
    @Override
    public String toString() {
        return "OrderItem{ id"+ getId()+" order_id"+ getOrder_id()+" product_id"+ getProduct_id() + "unit_price"+ getUnit_price() +" quantity"+ getQuantity() +" }";
    }
}
