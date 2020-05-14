package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;

public class RecacheInfo {
    @NonNull
    public final SpreedlySecureOpaqueString cvv;

    public RecacheInfo(@NonNull SpreedlySecureOpaqueString cvv) {
        this.cvv = cvv;
    }

    @NonNull
    public JSONObject toJson() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject creditCard = new JSONObject();
        creditCard.put("verification_value", this.cvv._encode());
        paymentMethod.put("credit_card", creditCard);
        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }
}
