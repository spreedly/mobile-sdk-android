package com.spreedly.client.models;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GooglePayInfoTest {
    @Test
    public void CanCreateGooglePayInfo() {
        GooglePayInfo googlePay = new GooglePayInfo("Jane", "Doe", "sample data", false);
        assertTrue(googlePay.firstName == "Jane" && googlePay.lastName == "Doe" && googlePay.paymentData == "sample data" && googlePay.retained == false);
    }

    @Test
    public void CanEncodeGooglePay() {
        GooglePayInfo googlePay = new GooglePayInfo("Jane", "Doe", "sample data", false);
        googlePay.testCardNumber = "111111111111111";
        String expected = "{\"payment_method\":{\"retained\":false,\"google_pay\":{\"test_card_number\":\"111111111111111\",\"last_name\":\"Doe\",\"payment_data\":\"sample data\",\"first_name\":\"Jane\"}}}";
        JSONObject actual = googlePay.toJson();
        assertEquals(expected, actual.toString());
    }
}
