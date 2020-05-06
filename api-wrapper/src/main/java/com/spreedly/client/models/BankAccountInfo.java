package com.spreedly.client.models;

import com.spreedly.client.models.enums.BankAccountType;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BankAccountInfo extends PaymentMethodMeta {
    @NonNull public String firstName;
    @NonNull public String lastName;
    @NonNull public String fullName;
    @NonNull public String routingNumber;
    @NonNull public SpreedlySecureOpaqueString accountNumber;
    @NonNull public BankAccountType accountType;
    @NonNull public String bankAccountHolderType;

    public BankAccountInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String routingNumber, @NonNull SpreedlySecureOpaqueString accountNumber, @NonNull BankAccountType accountType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public BankAccountInfo(@NonNull String fullName, @NonNull String routingNumber, @NonNull SpreedlySecureOpaqueString accountNumber, @NonNull BankAccountType accountType){
        this.fullName = fullName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }


    @Override
    @NonNull public String encode(@Nullable String email, @Nullable JSONObject metadata) {
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
        paymentMethod.put("bank_account", bankAccount);
        if (metadata != null) {
            paymentMethod.put("metadata", metadata);
        }
        if (email != null) {
            paymentMethod.put("email", email);
        }
        wrapper.put("payment_method", paymentMethod);
        return wrapper.toString();
    }
}


