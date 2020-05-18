package com.spreedly.client.models;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AddressTest {
    JSONObject jsonAddress = new JSONObject();
    JSONObject jsonAddressWithPrefix = new JSONObject();
    Address address = new Address("555 Sample Rd", "Unit 2", "Anytown", "WA", "55555", "USA", "444-444-4444");

    @Before
    public void initialize() {
        jsonAddress.put("address1", "555 Sample Rd");
        jsonAddress.put("address2", "Unit 2");
        jsonAddress.put("city", "Anytown");
        jsonAddress.put("state", "WA");
        jsonAddress.put("zip", "55555");
        jsonAddress.put("country", "USA");
        jsonAddress.put("phone_number", "444-444-4444");
        jsonAddressWithPrefix.put("shipping_address_address1", "555 Sample Rd");
        jsonAddressWithPrefix.put("shipping_address_address2", "Unit 2");
        jsonAddressWithPrefix.put("shipping_address_city", "Anytown");
        jsonAddressWithPrefix.put("shipping_address_state", "WA");
        jsonAddressWithPrefix.put("shipping_address_zip", "55555");
        jsonAddressWithPrefix.put("shipping_address_country", "USA");
        jsonAddressWithPrefix.put("shipping_address_phone_number", "444-444-4444");
    }

    @Test
    public void fromJsonJsonObjectNonNull() {
        Address actualAddress = null;
        actualAddress = Address.fromJson(jsonAddress, "");
        assertEquals(address.address1, actualAddress.address1);
        assertEquals(address.address2, actualAddress.address2);
        assertEquals(address.city, actualAddress.city);
        assertEquals(address.state, actualAddress.state);
        assertEquals(address.zip, actualAddress.zip);
        assertEquals(address.phoneNumber, actualAddress.phoneNumber);
        assertEquals(address.country, actualAddress.country);
    }

    @Test
    public void toJsonJsonObjectNonNull() {
        JSONObject actualJson = address.toJson(new JSONObject(), "");
        assertEquals(actualJson.toString(), jsonAddress.toString());
    }

    @Test
    public void fromJsonJsonObjectNull() {
        Address actualAddress = null;
        actualAddress = Address.fromJson(jsonAddressWithPrefix, "shipping_address_");
        assertEquals(address.address1, actualAddress.address1);
        assertEquals(address.address2, actualAddress.address2);
        assertEquals(address.city, actualAddress.city);
        assertEquals(address.state, actualAddress.state);
        assertEquals(address.zip, actualAddress.zip);
        assertEquals(address.phoneNumber, actualAddress.phoneNumber);
        assertEquals(address.country, actualAddress.country);
    }

    @Test
    public void toJsonJsonObjectNull() {
        JSONObject actualJson = address.toJson(null, "");
        assertEquals(actualJson.toString(), jsonAddress.toString());
    }

    @Test
    public void fromJsonReturnsNullWithBadPrefix() {
        Address actualAddress = null;
        actualAddress = Address.fromJson(jsonAddress, "bad_prefix");
        assertNull(actualAddress);
    }
}