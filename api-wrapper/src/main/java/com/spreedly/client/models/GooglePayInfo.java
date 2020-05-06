package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class GooglePayInfo extends PaymentMethodMeta{
    @NonNull public String signature;
    @NonNull public String protocolVersion;
    @NonNull public String signedMessage;
    @NonNull public String firstName;
    @NonNull public String lastName;
    @Nullable public String testCardNumber;
    @Nullable public Address address;
    @Nullable public IntermediateSigningKey intermediateSigningKey;

    public GooglePayInfo(){};

    public GooglePayInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String signature, @NonNull String protocolVersion, @NonNull String signedMessage, boolean retained){
        this.firstName = firstName;
        this.lastName = lastName;
        this.signedMessage = signedMessage;
        this.signature = signature;
        this.protocolVersion = protocolVersion;
        this.retained = retained;
    }

    @Override
    @NonNull
    public String encode(@Nullable String email, @Nullable JSONObject metadata) {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject googlePay = new JSONObject();
        JSONObject paymentData = new JSONObject();
        if (intermediateSigningKey != null){
            JSONObject signedKey = new JSONObject();
            signedKey.put("signedKey", intermediateSigningKey.signedKey);
            signedKey.put("signatures", intermediateSigningKey.signatures);
            paymentData.put("intermediateSigningKeys", signedKey);
        }
        paymentData.put("signature", this.signature);
        paymentData.put("protocolVersion", this.protocolVersion);
        paymentData.put("signedMessage", this.signedMessage);
        googlePay.put("payment_data", paymentData);
        googlePay.put("last_name", this.lastName);
        googlePay.put("first_name", this.firstName);
        googlePay.put("test_card_number", this.testCardNumber);
        if (this.address != null){
            googlePay.put("address1", this.address.address1);
            googlePay.put("address2", this.address.address2);
            googlePay.put("city", this.address.city);
            googlePay.put("state", this.address.state);
            googlePay.put("zip", this.address.zip);
            googlePay.put("country", this.address.country);
        }

        paymentMethod.put("google_pay", googlePay);
        paymentMethod.put("retained", this.retained);
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
