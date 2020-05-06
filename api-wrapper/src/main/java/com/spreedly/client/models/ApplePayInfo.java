package com.spreedly.client.models;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class ApplePayInfo extends PaymentMethodMeta {
    @NonNull public String firstName;
    @NonNull public String lastName;
    @NonNull public String email;
    @Nullable public Address address;
    @NonNull public String paymentData;
    @NonNull public String signature;
    @NonNull public String version;
    @NonNull public Map<String, String> header;
    @Nullable public String testCardNumber;


    @Override
    @NonNull public String encode() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject applePay = new JSONObject();
        JSONObject paymentData = new JSONObject();
        paymentData.put("signature", this.signature);
        paymentData.put("version", this.version);
        paymentData.put("header", this.header);
        paymentData.put("data", this.paymentData);
        applePay.put("payment_data", paymentData);
        applePay.put("test_card_number", this.testCardNumber);
        paymentMethod.put("apple_pay", applePay);
        paymentMethod.put("first_name", this.firstName);
        paymentMethod.put("last_name", this.lastName);
        if (this.address != null){
            paymentMethod.put("address1", this.address.address1);
            paymentMethod.put("address2", this.address.address2);
            paymentMethod.put("city", this.address.city);
            paymentMethod.put("state", this.address.state);
            paymentMethod.put("zip", this.address.zip);
            paymentMethod.put("country", this.address.country);
        }
        paymentMethod.put("email", this.email);
        paymentMethod.put("retained", this.retained);
        paymentMethod.put("metadata", this.data);
        wrapper.put("payment_method", paymentMethod);
        return wrapper.toString();
    }
}
