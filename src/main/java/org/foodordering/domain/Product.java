package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;
import java.math.BigDecimal;


public class Product extends AbstractEntity {
    @SerializedName("product_name")
    private String name;
    @SerializedName("product_description")
    private String description;
    @SerializedName("product_price")
    private BigDecimal price;
    @SerializedName("product_stock_quantity")
    private int stock_quantity;
    @SerializedName("s_id")
    private int store_id;
    private Store store;
    @SerializedName("ctgr_id")
    private int category_id;
    private Category category;
    @SerializedName("product_image")
    private String image;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public Product() {
    }

    @Override
    public String validate() {
        if (name == null || name.isEmpty()) {
            return "Name is required";
        }
        if (description == null || description.isEmpty()) {
            return "Description is required";
        }
        if (price == null) {
            return "Price is required";
        }
        if (stock_quantity <= 0) {
            return "Stock quantity is required";
        }
        if (category_id == 0) {
            return "Category id is required";
        }
        if(store_id == 0) {
            return "Store id is required";
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product ={ id= " + getId() + " name=" + name + " desciption= " + description + " store_id= " + store_id + " category_id= " + category_id + " image= " + image + " }";
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
}
