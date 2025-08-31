package org.foodordering.domain;

import org.foodordering.common.AbstractEntity;

import java.sql.Date;

public class Card extends AbstractEntity {
    private String cardNumber;
    private String cardName;
    private String cardVerificationValue;
    private String expiryDate;
    private int customer_id;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardVerificationValue() {
        return cardVerificationValue;
    }

    public void setCardVerificationValue(String cardVerificationValue) {
        this.cardVerificationValue = cardVerificationValue;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String validate() {
        if(cardName.equals(null) || cardName.isEmpty()){
            return "Card name is required";
        }
        if(expiryDate == null){
            return "Expiry Date is required";
        }
        if (cardNumber.length()<14||cardNumber.length()>19){
            return "Card number is incorrect";
        }
        if(cardVerificationValue.length()!=3){
            return "Card verification value is incorrect";
        }
        return null;
    }
}
