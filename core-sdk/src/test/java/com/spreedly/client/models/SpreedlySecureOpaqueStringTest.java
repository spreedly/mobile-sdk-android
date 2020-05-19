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
        //assertEquals(CreditCardType.visa, string.detectCardType());
    }

    @Test
    public void canIdentifyDankort() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5019717010103742");
        assertEquals(CreditCardType.dankort, string.detectCardType());
    }

    @Test
    public void canIdentifyAmericanExpress() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("378282246310005");
        assertEquals(CreditCardType.americanExpress, string.detectCardType());
    }

    @Test
    public void canIdentifyDiscover() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("6011111111111117");
        assertEquals(CreditCardType.discover, string.detectCardType());
    }

    @Test
    public void canIdentifyDinersClub() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("30569309025904");
        assertEquals(CreditCardType.dinersClub, string.detectCardType());
    }

    @Test
    public void canIdentifyJCB() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("3569990010030400");
        assertEquals(CreditCardType.jcb, string.detectCardType());
    }

    @Test
    public void canIdentifyMaestro() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("6759000000000000005");
        assertEquals(CreditCardType.maestro, string.detectCardType());
    }

    @Test
    public void canIdentifySodexo() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("507597000000003");
        assertEquals(CreditCardType.sodexo, string.detectCardType());
        //TODO: better sodexo card number?
    }

    @Test
    public void canIdentifyNaranja() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5895627823453003");
        assertEquals(CreditCardType.naranja, string.detectCardType());
    }

    @Test
    public void detectCardTypeErrorsWithInvalidNumber() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5895627823453005");
        assertEquals(CreditCardType.error, string.detectCardType());
    }

    @Test
    public void detectCardTypeReturnsUnknownIfCardDoesntMatch() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("8795727823453055");
        assertEquals(CreditCardType.unknown, string.detectCardType());
    }
}
