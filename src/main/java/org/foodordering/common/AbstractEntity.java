package org.foodordering.common;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractEntity {
    @SerializedName("id") //since each POJO has id, we can abstract it away in this class
    private int id;

    public abstract String validate(); // used to validate json request payload for missing/invalid fields

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        return !(this.id == 0 || other.id == 0 || !(this.id ==(other.id)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
