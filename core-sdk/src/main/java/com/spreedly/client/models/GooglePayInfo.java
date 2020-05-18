package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class GooglePayInfo extends PaymentMethodMeta {
    @NonNull
    public String paymentData;

    @Nullable
    public String testCardNumber;

    public GooglePayInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String paymentData, boolean retained) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.retained = retained;
        this.paymentData = paymentData;
    }

    @Override
    @NonNull
    public JSONObject toJson(@Nullable String email, @Nullable JSONObject metadata) {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject googlePay = new JSONObject();

        addCommonJsonFields(paymentMethod, googlePay, email, metadata);
        googlePay.put("payment_data", this.paymentData);
        googlePay.put("test_card_number", this.testCardNumber);

        paymentMethod.put("google_pay", googlePay);
        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }
}
