package com.spreedly.client.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecacheInfoTest {
    @Test
    public void canCreateRecacheInfo() {
        SpreedlySecureOpaqueString cvv = new SpreedlySecureOpaqueString();
        cvv.append("444");
        RecacheInfo recacheInfo = new RecacheInfo(cvv);
        String expected = "{\"payment_method\":{\"credit_card\":{\"verification_value\":\"444\"}}}";
        assertEquals(expected, recacheInfo.json.toString());
    }
}
