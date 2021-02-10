package com.spreedly.client.models;

import com.spreedly.client.SpreedlyClient;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaymentMethodMetaTest {
    SpreedlyClient client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);

    @Test
    public void addCommonJsonFields() {
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", null, null, client.createString("5555555555554444"), client.createString("432"), 3, 2032);
        cc.address = new Address("555 Sample Rd", "Unit 2", "Anytown", "WA", "55555", "USA", "444-444-4444");
        JSONObject wrapper = new JSONObject();
        JSONObject subType = new JSONObject();
        JSONObject metaData = new JSONObject();
        metaData.put("some", "data");
        cc.metadata = metaData;
        cc.email = "email@sample.com";
        cc.addCommonJsonFields(wrapper, subType);
        assertEquals("{\"zip\":\"55555\",\"country\":\"USA\",\"full_name\":\"Joe Jones\",\"address2\":\"Unit 2\",\"city\":\"Anytown\",\"address1\":\"555 Sample Rd\",\"phone_number\":\"444-444-4444\",\"state\":\"WA\"}", subType.toString());
        assertEquals("{\"metadata\":{\"some\":\"data\"},\"email\":\"email@sample.com\"}", wrapper.toString());
    }
}