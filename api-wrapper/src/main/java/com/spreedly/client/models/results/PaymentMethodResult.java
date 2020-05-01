package com.spreedly.client.models.results;

import java.util.ArrayList;
import java.util.Date;

public class PaymentMethodResult {
    public final String token;
    public final String storageState;
    public final boolean test;
    public final String paymentMethodType; //maybe enum
    public final ArrayList errors;
    public final Date createdAt;
    public final Date updatedAt;
    public final String email;

    public PaymentMethodResult(String token, String storageState, boolean test, String paymentMethodType, Date createdAt, Date updatedAt, String email, ArrayList errors) {
        this.token = token;
        this.storageState = storageState;
        this.test = test;
        this.paymentMethodType = paymentMethodType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.email = email;
        this.errors = errors;
    }
}
