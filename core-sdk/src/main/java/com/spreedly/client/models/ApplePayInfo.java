package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class ApplePayInfo extends PaymentMethodInfo {
    @Nullable public String testCardNumber;
    @NonNull
    public String paymentData;

    public ApplePayInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String paymentData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.paymentData = paymentData;
    }

    @NonNull
    public JSONObject toJson(@Nullable String email, @Nullable JSONObject metadata) {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject applePay = new JSONObject();

        addCommonJsonFields(paymentMethod, applePay, email, metadata);
        applePay.put("payment_data", this.paymentData);
        applePay.put("test_card_number", this.testCardNumber);
        paymentMethod.put("apple_pay", applePay);
        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }
}
