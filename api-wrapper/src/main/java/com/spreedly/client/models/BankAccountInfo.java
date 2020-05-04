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
    @Nullable public Map<String, Object> additionalInformation;

    public BankAccountInfo(String firstName, String lastName, String routingNumber, String accountNumber, String accountType, String bankAccountHolderType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
    }

    public BankAccountInfo(String fullName, String routingNumber, String accountNumber, String accountType, String bankAccountHolderType){
        this.fullName = fullName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
    }

    public BankAccountInfo(String firstName, String lastName, String routingNumber, String accountNumber, String accountType, String bankAccountHolderType, String email, Map<String, Object> metadata, Map<String, Object> additionalInformation){
        this.firstName = firstName;
        this.lastName = lastName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
        this.email = email;
        this.data = metadata;
        this.additionalInformation = additionalInformation;
    }

    public BankAccountInfo(String fullName, String routingNumber, String accountNumber, String accountType, String bankAccountHolderType, String email, Map<String, Object> metadata, Map<String, Object> additionalInformation){
        this.fullName = fullName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.bankAccountHolderType = bankAccountHolderType;
        this.email = email;
        this.data = metadata;
        this.additionalInformation = additionalInformation;
    }
    public BankAccountInfo(){};
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
        paymentMethod.put("metadata", this.data);
        paymentMethod.put("data", additionalInformation);
        paymentMethod.put("bank_account", bankAccount);
        wrapper.put("payment_method", paymentMethod);
        return wrapper.toString();
    }
}


