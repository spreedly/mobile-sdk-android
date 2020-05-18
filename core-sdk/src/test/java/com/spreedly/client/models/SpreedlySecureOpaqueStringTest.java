package com.spreedly.client.models;

import com.spreedly.client.models.enums.CreditCardType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SpreedlySecureOpaqueStringTest {

    @Test
    public void CanCreateString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        assertNotNull(string);
    }

    @Test
    public void CanClearString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        string.clear();
        assertEquals(0, string.length);
    }

    @Test
    public void CanAppendString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        assertEquals(4, string.length);
    }

    @Test
    public void CanRemoveChar(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("test");
        string.removeLastCharacter();
        assertEquals(3, string.length);
    }

    @Test
    public void CanRemoveCharIfEmpty(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.removeLastCharacter();
        assertEquals(0, string.length);
    }

    @Test
    public void canIdentifyMasterCard() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5555555555554444");
        assertEquals(CreditCardType.mastercard, string.detectCardType());
    }

    @Test
    public void canIdentifyVisaCard() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("4111111111111111");
        assertEquals(CreditCardType.visa, string.detectCardType());
    }

    @Test
    public void canIdentifyLongVisaCard() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("4444333322221111455");
        assertEquals(CreditCardType.visa, string.detectCardType());
    }
}
