package com.spreedly.client.models;

import java.util.ArrayList;

public class BankAccountResult extends PaymentMethodResult {
    String bankName;
    String accountType;
    String accountHolderType;
    String routingNumberDisplayDigits;
    String accountNumberDisplayDigits;
    String firstName;
    String lastName;
    String fullName;
    String routingNumber;
    String accountNumber;

    public BankAccountResult(
            String token,
            String storageState,
            boolean test,
            String paymentMethodType,
            ArrayList errors,
            String bankName,
            String accountType,
            String accountHolderType,
            String routingNumberDisplayDigits,
            String accountNumberDisplayDigits,
            String routingNumber,
            String accountNumber,
            String firstName,
            String lastName) {
        this.token = token;
        this.storageState = storageState;
        this.test = test;
        this.errors = errors;
        this.bankName = bankName;
        this.accountType = accountType;
        this.accountHolderType = accountHolderType;
        this.routingNumberDisplayDigits = routingNumberDisplayDigits;
        this.accountNumberDisplayDigits = accountNumberDisplayDigits;
        this.paymentMethodType = paymentMethodType;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public BankAccountResult(
            String token, String storageState, boolean test, String paymentMethodType, ArrayList errors,
            String bankName,
            String accountType,
            String accountHolderType,
            String routingNumberDisplayDigits,
            String accountNumberDisplayDigits,
            String routingNumber,
            String accountNumber, String fullName) {
        this.token = token;
        this.storageState = storageState;
        this.test = test;
        this.errors = errors;
        this.bankName = bankName;
        this.accountType = accountType;
        this.accountHolderType = accountHolderType;
        this.routingNumberDisplayDigits = routingNumberDisplayDigits;
        this.accountNumberDisplayDigits = accountNumberDisplayDigits;
        this.paymentMethodType = paymentMethodType;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.fullName = fullName;
    }
}
