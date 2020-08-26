package com.spreedly.client.models;

import com.spreedly.client.models.enums.CardBrand;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SpreedlySecureOpaqueStringTest {

    @Test
    public void CanCreateString(){
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        assertNotNull(string);
    }

    @Test
    public void canCreateStringWithContent() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("text");
        assertEquals("text", string._encode());
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
        assertEquals(CardBrand.mastercard, string.detectCardType());
        assertEquals(CardBrand.mastercard, string.softDetect());
    }

    @Test
    public void canIdentifyMastercardAlt() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("2223003122003222");
        assertEquals(CardBrand.mastercard, string.detectCardType());
        assertEquals(CardBrand.mastercard, string.softDetect());
    }

    @Test
    public void canIdentifyVisaCard() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("4111111111111111");
        assertEquals(CardBrand.visa, string.detectCardType());
        assertEquals(CardBrand.visa, string.softDetect());
    }

    @Test
    public void canIdentifyLongVisaCard() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("4444333322221111455");
        assertEquals(CardBrand.visa, string.detectCardType());
        assertEquals(CardBrand.visa, string.softDetect());
    }

    @Test
    public void canIdentifyDankort() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5019717010103742");
        assertEquals(CardBrand.dankort, string.detectCardType());
        assertEquals(CardBrand.dankort, string.softDetect());
    }

    @Test
    public void canIdentifyAmericanExpress() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("378282246310005");
        assertEquals(CardBrand.americanExpress, string.detectCardType());
        assertEquals(CardBrand.americanExpress, string.softDetect());
    }

    @Test
    public void canIdentifyDiscover() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("6011111111111117");
        assertEquals(CardBrand.discover, string.detectCardType());
        assertEquals(CardBrand.discover, string.softDetect());
    }

    @Test
    public void canIdentifyDinersClub() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("30569309025904");
        assertEquals(CardBrand.dinersClub, string.detectCardType());
        assertEquals(CardBrand.dinersClub, string.softDetect());
    }

    @Test
    public void canIdentifyJCB() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("3569990010030400");
        assertEquals(CardBrand.jcb, string.detectCardType());
        assertEquals(CardBrand.jcb, string.softDetect());
    }

    @Test
    public void canIdentifyMaestro() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("6759000000000000005");
        assertEquals(CardBrand.maestro, string.detectCardType());
        assertEquals(CardBrand.maestro, string.softDetect());
    }

    @Test
    public void canIdentifySodexo() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("6060690000000002");
        assertEquals(CardBrand.sodexo, string.detectCardType());
        assertEquals(CardBrand.sodexo, string.softDetect());
    }

    @Test
    public void canIdentifyNaranja() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5895627823453003");
        assertEquals(CardBrand.naranja, string.detectCardType());
        assertEquals(CardBrand.naranja, string.softDetect());
    }

    @Test
    public void canIdentifyElo() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5067310000000010");
        assertEquals(CardBrand.elo, string.detectCardType());
        assertEquals(CardBrand.elo, string.softDetect());
    }

    @Test
    public void canIdentifyAlelo() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5067705232092752");
        assertEquals(CardBrand.alelo, string.detectCardType());
        assertEquals(CardBrand.alelo, string.softDetect());
    }

    @Test
    public void canIdentifyCabal() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("6035227716427021");
        assertEquals(CardBrand.cabal, string.detectCardType());
        assertEquals(CardBrand.cabal, string.softDetect());
    }

    @Test
    public void canIdentifyCarnet() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5062280000000002");
        assertEquals(CardBrand.carnet, string.detectCardType());
        assertEquals(CardBrand.carnet, string.softDetect());
    }

    @Test
    public void detectCardTypeErrorsWithInvalidNumber() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("5895627823453005");
        assertEquals(CardBrand.error, string.detectCardType());
    }

    @Test
    public void detectCardTypeReturnsUnknownIfCardDoesntMatch() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        string.append("8795727823453055");
        assertEquals(CardBrand.unknown, string.detectCardType());
        assertEquals(CardBrand.unknown, string.softDetect());
    }

    @Test
    public void softDetectLongCCReturnsUnknown() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("87957278888888823453055");
        assertEquals(CardBrand.unknown, string.softDetect());
    }

    @Test
    public void nonvalidInputReturnsError() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("notnumbers");
        assertEquals(CardBrand.error, string.detectCardType());
    }

    @Test
    public void canIdentifyCarnetAlt() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("5022750000000003");
        assertEquals(CardBrand.carnet, string.softDetect());
        assertEquals(CardBrand.carnet, string.detectCardType());
    }

    @Test
    public void canIdentifyMaestroAlt() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("5000330000000000");
        assertEquals(CardBrand.maestro, string.softDetect());
        assertEquals(CardBrand.maestro, string.detectCardType());
    }

    @Test
    public void canIdentifyVR() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("6274160000000008");
        assertEquals(CardBrand.vr, string.softDetect());
        assertEquals(CardBrand.vr, string.detectCardType());
    }

    @Test
    public void canIdentifyNumber() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("12345");
        assertTrue(string.isNumber());
    }

    @Test
    public void canIdentifyBadNumber() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("1234-5");
        assertFalse(string.isNumber());
    }

    @Test
    public void inRangeReturnsFalseIfLengthIsGreaterThanNumberLength() {
        Range[] ranges = CardBrand.maestro.range;
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString();
        assertFalse(string.inRanges(ranges, "123456", 8));
    }

    @Test
    public void shortCardNumberReturnsUnknown() {
        SpreedlySecureOpaqueString string = new SpreedlySecureOpaqueString("79927398713");
        assertEquals(CardBrand.unknown, string.detectCardType());
    }

}
