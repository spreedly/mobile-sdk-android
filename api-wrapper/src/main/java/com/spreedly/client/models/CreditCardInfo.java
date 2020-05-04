package com.spreedly.client.models;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class CreditCardInfo extends PaymentMethodMeta {
    @Nullable public String company;
    @Nullable public String email;
    @Nullable public String firstName;
    @Nullable public String lastName;
    @Nullable public String fullName;
    // public SpreedlySecureOpaqueString number;
    @NonNull public String number;
    @NonNull public String month;
    @NonNull public String year;
    // public SpreedlySecureOpaqueString cvv;
    @NonNull public String cvv;
    @Nullable public Address billingAddress;
    @Nullable public Address shippingAddress;
    @Nullable public String phoneNumber;
    @Nullable boolean allowBlankName;
    @Nullable boolean allowExpiredDate;
    @Nullable boolean allowBlankDate;
    @Nullable boolean eligibleForCardUpdate;
}
