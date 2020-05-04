package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class CreditCardInfo extends PaymentMethodMeta {
    @Nullable public String company;
    @Nullable public String email;
    @Nullable public String firstName;
    @Nullable public String lastName;
    @Nullable public String fullName;
    @Nullable public SpreedlySecureOpaqueString number;
    @NonNull public String month;
    @NonNull public String year;
    @NonNull public SpreedlySecureOpaqueString cvv;
    @Nullable public Address billingAddress;
    @Nullable public Address shippingAddress;
    @Nullable public String phoneNumber;
    @Nullable boolean allowBlankName;
    @Nullable boolean allowExpiredDate;
    @Nullable boolean allowBlankDate;
    @Nullable boolean eligibleForCardUpdate;

    @Override
    @NonNull public String encode() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject creditCard = new JSONObject();
        if (this.fullName != null && this.fullName != ""){
            creditCard.put("full_name", this.fullName);
        } else {
            creditCard.put("first_name", this.firstName);
            creditCard.put("last_name", this.lastName);
        }
        creditCard = this.cvv.encode(creditCard, "verification_value");
        creditCard = this.number.encode(creditCard, "number");
        creditCard.put("month", this.month);
        creditCard.put("year", this.year);
        if (this.billingAddress != null){
            creditCard.put("address1", this.billingAddress.address1);
            creditCard.put("address2", this.billingAddress.address2);
            creditCard.put("city", this.billingAddress.city);
            creditCard.put("state", this.billingAddress.state);
            creditCard.put("zip", this.billingAddress.zip);
            creditCard.put("country", this.billingAddress.country);
        }
        if (shippingAddress != null){
            creditCard.put("shipping_address1", this.shippingAddress.address1);
            creditCard.put("shipping_address2", this.shippingAddress.address2);
            creditCard.put("shipping_city", this.shippingAddress.city);
            creditCard.put("shipping_state", this.shippingAddress.state);
            creditCard.put("shipping_zip", this.shippingAddress.zip);
            creditCard.put("shipping_country", this.shippingAddress.country);
        }
        creditCard.put("phone_number", this.phoneNumber);
        paymentMethod.put("credit_card", creditCard);
        paymentMethod.put("email", this.email);
        paymentMethod.put("metadata", this.data);
        paymentMethod.put("retained", this.retained);
        wrapper.put("payment_method", paymentMethod);
        return wrapper.toString();
    }
}
