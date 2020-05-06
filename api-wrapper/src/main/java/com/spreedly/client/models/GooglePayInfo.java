package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class GooglePayInfo extends PaymentMethodMeta {
    @NonNull
    public String signature;
    @NonNull
    public String protocolVersion;
    @NonNull
    public String signedMessage;

    @Nullable
    public String testCardNumber;

    @Nullable
    public IntermediateSigningKey intermediateSigningKey;

    public GooglePayInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String signature, @NonNull String protocolVersion, @NonNull String signedMessage, boolean retained) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.signedMessage = signedMessage;
        this.signature = signature;
        this.protocolVersion = protocolVersion;
        this.retained = retained;
    }

    @Override
    @NonNull
    public JSONObject toJson(@Nullable String email, @Nullable JSONObject metadata) {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject googlePay = new JSONObject();
        JSONObject paymentData = new JSONObject();

        addCommonJsonFields(paymentMethod, googlePay, email, metadata);

        if (intermediateSigningKey != null) {
            JSONObject signedKey = new JSONObject();
            signedKey.put("signedKey", intermediateSigningKey.signedKey);
            signedKey.put("signatures", intermediateSigningKey.signatures);
            paymentData.put("intermediateSigningKeys", signedKey);
        }
        paymentData.put("signature", this.signature);
        paymentData.put("protocolVersion", this.protocolVersion);
        paymentData.put("signedMessage", this.signedMessage);
        googlePay.put("payment_data", paymentData);
        googlePay.put("test_card_number", this.testCardNumber);

        paymentMethod.put("google_pay", googlePay);
        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }
}
