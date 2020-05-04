package com.spreedly.client.models;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BankAccountInfo extends PaymentMethodMeta {
    @Nullable public String email;
    @NonNull public String firstName;
    @NonNull public String lastName;
    @NonNull public String fullName;
    @NonNull String routingNumber;
    @NonNull String accountNumber; //maybe SpreedlySecureOpaqueString?
    @NonNull String accountType; //maybe enum
    @NonNull public String bankAccountHolderType; //maybe enum
    @Nullable public Map<String, Object> additionalInformation;
}


