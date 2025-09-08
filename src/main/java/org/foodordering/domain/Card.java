package org.foodordering.domain;

import com.google.gson.annotations.SerializedName;
import org.foodordering.common.AbstractEntity;

import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.LocalDate;

public class Card extends AbstractEntity {
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("card_name")
    private String cardName;
    @SerializedName("cvv")
    private String cardVerificationValue;
    @SerializedName("expiry_date")
    private String expiryDate;
    @SerializedName("c_id")
    private int customer_id;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");


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
        if(cardName == null || cardName.isEmpty()){
            return "Card name is required";
        }
        if(expiryDate == null){
            return "Expiry Date is required";
        }
        if(LocalDate.now().isAfter(YearMonth.parse(expiryDate, formatter).atEndOfMonth())){
            return "Expiry Date is after date";
        }
        if(cardNumber.length()<14){
            return "Card number in too short";
        }
        if(cardNumber.length()>19){
            return "Card number is too long";
        }
        for (char c : cardNumber.toCharArray()){
            if(!Character.isDigit(c) && !Character.isDigit(' ')){
                return "Card number can't have letters";
            }
        }
        if(cardVerificationValue.length()!=3){
            return "Card verification value is incorrect";
        }
        return null;
    }
}
