package com.spreedly.client.models;

import java.util.ArrayList;

public class CreditCardResult extends PaymentMethodResult {
    String lastFourDigits;
    String firstSixDigits;
    String cvv;
    String number;
    String month;
    String year;

    public CreditCardResult(String token, String storageState, boolean test, String paymentMethodType, ArrayList errors, String lastFourDigits, String firstSixDigits, String cvv, String number, String month, String year) {
        super();
        this.token = token;
        this.storageState = storageState;
        this.test = test;
        this.paymentMethodType = paymentMethodType;
        this.errors = errors;
        this.lastFourDigits = lastFourDigits;
        this.firstSixDigits = firstSixDigits;
        this.cvv = cvv;
        this.number = number;
        this.month = month;
        this.year = year;


    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public String getFirstSixDigits() {
        return firstSixDigits;
    }

    public String getCvv() {
        return cvv;
    }

    public String getNumber() {
        return number;
    }


    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
