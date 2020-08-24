package com.spreedly.client.models;

import com.spreedly.client.models.enums.AccountHolderType;
import com.spreedly.client.models.enums.AccountType;

import org.json.JSONObject;

import java.util.Locale;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BankAccountInfo extends PaymentMethodInfo {
    @Nullable
    public String routingNumber;
    @Nullable
    public SpreedlySecureOpaqueString accountNumber;
    @Nullable
    public AccountType accountType;
    @Nullable
    public AccountHolderType accountHolderType;

    public BankAccountInfo() {
    }

    public BankAccountInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String routingNumber, @NonNull SpreedlySecureOpaqueString accountNumber, @NonNull AccountType accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public BankAccountInfo(@NonNull String fullName, @NonNull String routingNumber, @NonNull SpreedlySecureOpaqueString accountNumber, @NonNull AccountType accountType) {
        this.fullName = fullName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    @Override
    @NonNull
    public JSONObject toJson() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject bankAccount = new JSONObject();

        addCommonJsonFields(paymentMethod, bankAccount);

        bankAccount.put("bank_routing_number", this.routingNumber);
        bankAccount.put("bank_account_number", this.accountNumber._encode());
        try {
            bankAccount.put("bank_account_type", this.accountType.toString().toLowerCase(Locale.US));
        } catch (NullPointerException e) {
            bankAccount.put("bank_account_type", "");
        }
        try {
            bankAccount.put("bank_account_holder_type", this.accountHolderType.toString().toLowerCase(Locale.US));
        } catch (NullPointerException e) {
        }
        paymentMethod.put("bank_account", bankAccount);

        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }
}


