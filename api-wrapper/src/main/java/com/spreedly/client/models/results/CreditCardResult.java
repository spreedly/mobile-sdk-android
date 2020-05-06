package com.spreedly.client.models.results;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class CreditCardResult extends PaymentMethodResult {
    public final @Nullable String lastFourDigits;
    public final @Nullable String firstSixDigits;
    public final @Nullable String cvv;
    public final @Nullable String number;
    public final @Nullable String month;
    public final @Nullable String year;

    public CreditCardResult(
            @NonNull String token,
            @Nullable String storageState,
            boolean test,
            @Nullable String paymentMethodType,
            @Nullable List<SpreedlyError> errors,
            @Nullable Date createdAt,
            @Nullable Date updatedAt,
            @Nullable String email,
            @Nullable String lastFourDigits,
            @Nullable String firstSixDigits,
            @Nullable String cvv,
            @Nullable String number,
            @Nullable String month,
            @Nullable String year
    ) {
        super(token, storageState, test, paymentMethodType, createdAt, updatedAt, email, errors);
        this.lastFourDigits = lastFourDigits;
        this.firstSixDigits = firstSixDigits;
        this.cvv = cvv;
        this.number = number;
        this.month = month;
        this.year = year;
    }
}
