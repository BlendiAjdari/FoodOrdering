package org.foodordering.domain;

import org.foodordering.common.AbstractEntity;

public class Ewallet extends AbstractEntity {
    private String username;
    private String password;
    private int card_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    @Override
    public String validate() {
        if(username == null || username.isEmpty()){
            return "Username cannot be empty";
        }
        if(password == null || password.isEmpty()){
            return "Password cannot be empty";
        }
        if(card_id==0){
            return "Card ID cannot be zero";
        }

        return null;
    }

    @Override
    public String toString() {
        return "E-wallet " +"id "+getId()+" username "+getUsername()+" password "+getPassword()+" card_id "+getCard_id();
    }
}
