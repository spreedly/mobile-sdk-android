package com.spreedly.client.models.results;

import com.spreedly.client.models.results.PaymentMethodResult;

import java.util.ArrayList;
import java.util.Date;

public class BankAccountResult extends PaymentMethodResult {
    public final String bankName;
    public final String accountType;
    public final String accountHolderType;
    public final String routingNumberDisplayDigits;
    public final String accountNumberDisplayDigits;
    public final String firstName;
    public final String lastName;
    public final String fullName;
    public final String routingNumber;
    public final String accountNumber;

    public BankAccountResult(
            String token,
            String storageState,
            boolean test,
            String paymentMethodType,
            Date createdAt,
            Date updatedAt,
            String email,
            ArrayList errors,
            String bankName,
            String accountType,
            String accountHolderType,
            String routingNumberDisplayDigits,
            String accountNumberDisplayDigits,
            String routingNumber,
            String accountNumber,
            String firstName,
            String lastName,
            String fullName) {
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
