package com.spreedly.client.models;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class ApplePayInfo extends PaymentMethodMeta {
    @NonNull public String paymentData;
    @NonNull public String signature;
    @NonNull public String version;
    @NonNull public Map<String, String> header;
    @Nullable public String testCardNumber;


    @NonNull
    public JSONObject toJson(@Nullable String email, @Nullable JSONObject metadata) {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject applePay = new JSONObject();
        JSONObject paymentData = new JSONObject();

        addCommonJsonFields(paymentMethod, applePay, email, metadata);

        paymentData.put("signature", this.signature);
        paymentData.put("version", this.version);
        paymentData.put("header", this.header);
        paymentData.put("data", this.paymentData);
        applePay.put("payment_data", paymentData);
        applePay.put("test_card_number", this.testCardNumber);
        paymentMethod.put("apple_pay", applePay);
        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }
}
