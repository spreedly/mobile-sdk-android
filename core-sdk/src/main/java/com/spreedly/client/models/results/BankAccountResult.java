package com.spreedly.client.models.results;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;

/// Bank Account Result
public class BankAccountResult extends PaymentMethodResult {
    public final @Nullable String bankName;
    public final @Nullable String accountType;
    public final @Nullable String accountHolderType;
    public final @Nullable String routingNumberDisplayDigits;
    public final @Nullable String accountNumberDisplayDigits;
    public final @Nullable String firstName;
    public final @Nullable String lastName;
    public final @Nullable String fullName;
    public final @Nullable String routingNumber;
    public final @Nullable String accountNumber;

    public BankAccountResult(
            @Nullable String token,
            @Nullable String storageState,
            boolean test,
            @Nullable String paymentMethodType,
            @Nullable Date createdAt,
            @Nullable Date updatedAt,
            @Nullable String email,
            @Nullable List<SpreedlyError> errors,
            @Nullable String bankName,
            @Nullable String accountType,
            @Nullable String accountHolderType,
            @Nullable String routingNumberDisplayDigits,
            @Nullable String accountNumberDisplayDigits,
            @Nullable String routingNumber,
            @Nullable String accountNumber,
            @Nullable String firstName,
            @Nullable String lastName,
            @Nullable String fullName) {
        super(token, storageState, test, paymentMethodType, createdAt, updatedAt, email, errors);
        this.bankName = bankName;
        this.accountType = accountType;
        this.accountHolderType = accountHolderType;
        this.routingNumberDisplayDigits = routingNumberDisplayDigits;
        this.accountNumberDisplayDigits = accountNumberDisplayDigits;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
    }

}
