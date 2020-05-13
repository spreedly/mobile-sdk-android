package com.spreedly.client.models;

import org.json.JSONObject;

public class RecacheInfo {
    public final JSONObject json;

    public RecacheInfo(SpreedlySecureOpaqueString cvv) {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject creditCard = new JSONObject();
        creditCard.put("verification_value", cvv._encode());
        paymentMethod.put("credit_card", creditCard);
        wrapper.put("payment_method", paymentMethod);
        this.json = wrapper;
    }
}
