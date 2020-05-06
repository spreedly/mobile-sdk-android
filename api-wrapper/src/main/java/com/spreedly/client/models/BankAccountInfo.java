package com.spreedly.client.models;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BankAccountInfo extends PaymentMethodMeta {
    @Nullable public String email;
    @NonNull public String firstName;
    @NonNull public String lastName;
    @NonNull public String fullName;
    @NonNull public String routingNumber;
    @NonNull public String accountNumber; //maybe SpreedlySecureOpaqueString?
    @NonNull public String accountType; //maybe enum
    @NonNull public String bankAccountHolderType; //maybe enum

    public BankAccountInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String routingNumber, @NonNull String accountNumber, @NonNull String accountType, @NonNull String bankAccountHolderType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
    }

    public BankAccountInfo(@NonNull String fullName, @NonNull String routingNumber, @NonNull String accountNumber, @NonNull String accountType, @NonNull String bankAccountHolderType){
        this.fullName = fullName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
    }

    public BankAccountInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String routingNumber, @NonNull String accountNumber, @NonNull String accountType, @NonNull String bankAccountHolderType, @NonNull String email, @Nullable Map<String, Object> metadata) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
        this.email = email;
        this.data = metadata;
    }

    public BankAccountInfo(@NonNull String fullName, @NonNull String routingNumber, @NonNull String accountNumber, @NonNull String accountType, @NonNull String bankAccountHolderType, @NonNull String email, @Nullable Map<String, Object> metadata) {
        this.fullName = fullName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
        this.email = email;
        this.data = metadata;
    }

    public BankAccountInfo() {
    }

    @Override
    @NonNull public String encode() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject bankAccount = new JSONObject();
        if (this.fullName != null && this.fullName != ""){
            bankAccount.put("full_name", this.fullName);
        } else {
            bankAccount.put("first_name", this.firstName);
            bankAccount.put("last_name", this.lastName);
        }
        bankAccount.put("bank_routing_number", this.routingNumber);
        bankAccount.put("bank_account_number", this.accountNumber);
        bankAccount.put("bank_account_type", this.accountType);
        bankAccount.put("bank_account_holder_type", this.bankAccountHolderType);
        paymentMethod.put("email", this.email);
        if (this.data != null) {
            paymentMethod.put("metadata", this.data);
        }
        paymentMethod.put("bank_account", bankAccount);
        wrapper.put("payment_method", paymentMethod);
        return wrapper.toString();
    }
}


