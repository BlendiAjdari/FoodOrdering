package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

public class Category extends AbstractEntity {
    @SerializedName("Category_name")
    private String name;


    public String getName() {
        return name;
    }

    public Category() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String validate() {
        if (name == null) {
            return "Name is required";
        }
        return null;
    }
}
