package com.spreedly.client.models;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplePayInfoTests {

    @Test
    public void CanCreateApplePayInfo() {
        JSONObject headers = new JSONObject();
        headers.put("key", "value");
        String headerString = headers.toString();
        ApplePayInfo applePay = new ApplePayInfo("Jane", "Doe", "sample data");
        assertTrue(applePay.firstName == "Jane" && applePay.lastName == "Doe" && applePay.paymentData == "sample data");
    }

    @Test
    public void CanEncodeApplePay() {
        ApplePayInfo applePay = new ApplePayInfo("Jane", "Doe", "sample data");
        applePay.testCardNumber = "111111111111111";
        String expected = "{\"payment_method\":{\"apple_pay\":{\"test_card_number\":\"111111111111111\",\"last_name\":\"Doe\",\"payment_data\":\"sample data\",\"first_name\":\"Jane\"}}}";
        JSONObject actual = applePay.toJson(null, null);
        assertEquals(expected, actual.toString());
    }
}