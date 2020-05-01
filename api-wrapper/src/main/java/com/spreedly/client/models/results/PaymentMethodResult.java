package com.spreedly.client.models.results;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.annotations.Nullable;

public class PaymentMethodResult {
    public final @Nullable String token;
    public final @Nullable String storageState;
    public final boolean test;
    public final @Nullable String paymentMethodType; //maybe enum
    public final @Nullable ArrayList errors;
    public final @Nullable Date createdAt;
    public final @Nullable Date updatedAt;
    public final @Nullable String email;

    public PaymentMethodResult(
            @Nullable String token,
            @Nullable String storageState,
            boolean test,
            @Nullable String paymentMethodType,
            @Nullable Date createdAt,
            @Nullable Date updatedAt,
            @Nullable String email,
            @Nullable ArrayList errors) {
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
