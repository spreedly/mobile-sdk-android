package com.spreedly.client.models.results;

import com.spreedly.client.models.results.PaymentMethodResult;

import java.util.ArrayList;
import java.util.Date;

public class CreditCardResult extends PaymentMethodResult {
    public final String lastFourDigits;
    public final String firstSixDigits;
    public final String cvv;
    public final String number;
    public final String month;
    public final String year;

    public CreditCardResult(String token, String storageState, boolean test, String paymentMethodType, ArrayList errors, Date createdAt, Date updatedAt, String email, String lastFourDigits, String firstSixDigits, String cvv, String number, String month, String year) {
        super(token, storageState, test, paymentMethodType, createdAt, updatedAt, email, errors);
        this.lastFourDigits = lastFourDigits;
        this.firstSixDigits = firstSixDigits;
        this.cvv = cvv;
        this.number = number;
        this.month = month;
        this.year = year;
    }
}
