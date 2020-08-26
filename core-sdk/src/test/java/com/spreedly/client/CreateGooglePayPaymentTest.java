package com.spreedly.client;

import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.observers.TestObserver;

public class CreateGooglePayPaymentTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void CreateGooglePaySucceeds() throws InterruptedException {
        GooglePayInfo googlePayInfo = new GooglePayInfo("John", "Doe", "{\n" +
                "  \"protocolVersion\":\"ECv2\",\n" +
                "  \"signature\":\"MEQCIH6Q4OwQ0jAceFEkGF0JID6sJNXxOEi4r+mA7biRxqBQAiAondqoUpU/bdsrAOpZIsrHQS9nwiiNwOrr24RyPeHA0Q\\u003d\\u003d\",\n" +
                "  \"intermediateSigningKey\":{\n" +
                "    \"signedKey\": \"{\\\"keyExpiration\\\":\\\"1542323393147\\\",\\\"keyValue\\\":\\\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/1+3HBVSbdv+j7NaArdgMyoSAM43yRydzqdg1TxodSzA96Dj4Mc1EiKroxxunavVIvdxGnJeFViTzFvzFRxyCw\\\\u003d\\\\u003d\\\"}\",\n" +
                "    \"signatures\": [\"MEYCIQCO2EIi48s8VTH+ilMEpoXLFfkxAwHjfPSCVED/QDSHmQIhALLJmrUlNAY8hDQRV/y1iKZGsWpeNmIP+z+tCQHQxP0v\"]\n" +
                "  },\n" +
                "  \"signedMessage\":\"{\\\"tag\\\":\\\"jpGz1F1Bcoi/fCNxI9n7Qrsw7i7KHrGtTf3NrRclt+U\\\\u003d\\\",\\\"ephemeralPublicKey\\\":\\\"BJatyFvFPPD21l8/uLP46Ta1hsKHndf8Z+tAgk+DEPQgYTkhHy19cF3h/bXs0tWTmZtnNm+vlVrKbRU9K8+7cZs\\\\u003d\\\",\\\"encryptedMessage\\\":\\\"mKOoXwi8OavZ\\\"}\"\n" +
                "}", false);
        googlePayInfo.testCardNumber = "4111111111111111";
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createGooglePaymentMethod(googlePayInfo).subscribe(test);
        test.await();
        test.assertComplete();
    }

    @Test
    public void CreateGooglePayGetsToken() throws InterruptedException {
        GooglePayInfo googlePayInfo = new GooglePayInfo("John", "Doe", "{\n" +
                "  \"protocolVersion\":\"ECv2\",\n" +
                "  \"signature\":\"MEQCIH6Q4OwQ0jAceFEkGF0JID6sJNXxOEi4r+mA7biRxqBQAiAondqoUpU/bdsrAOpZIsrHQS9nwiiNwOrr24RyPeHA0Q\\u003d\\u003d\",\n" +
                "  \"intermediateSigningKey\":{\n" +
                "    \"signedKey\": \"{\\\"keyExpiration\\\":\\\"1542323393147\\\",\\\"keyValue\\\":\\\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/1+3HBVSbdv+j7NaArdgMyoSAM43yRydzqdg1TxodSzA96Dj4Mc1EiKroxxunavVIvdxGnJeFViTzFvzFRxyCw\\\\u003d\\\\u003d\\\"}\",\n" +
                "    \"signatures\": [\"MEYCIQCO2EIi48s8VTH+ilMEpoXLFfkxAwHjfPSCVED/QDSHmQIhALLJmrUlNAY8hDQRV/y1iKZGsWpeNmIP+z+tCQHQxP0v\"]\n" +
                "  },\n" +
                "  \"signedMessage\":\"{\\\"tag\\\":\\\"jpGz1F1Bcoi/fCNxI9n7Qrsw7i7KHrGtTf3NrRclt+U\\\\u003d\\\",\\\"ephemeralPublicKey\\\":\\\"BJatyFvFPPD21l8/uLP46Ta1hsKHndf8Z+tAgk+DEPQgYTkhHy19cF3h/bXs0tWTmZtnNm+vlVrKbRU9K8+7cZs\\\\u003d\\\",\\\"encryptedMessage\\\":\\\"mKOoXwi8OavZ\\\"}\"\n" +
                "}", false);
        googlePayInfo.testCardNumber = "4111111111111111";
        googlePayInfo.retained = false;
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createGooglePaymentMethod(googlePayInfo).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        //assertEquals("Succeeded!", trans.message);
        //assertNotNull(trans.result.token);
        // TODO: add asserts back in w/ good google info
    }

    @Test
    public void RetainedCreateGooglePayGetsToken() throws InterruptedException {
        GooglePayInfo googlePayInfo = new GooglePayInfo("John", "Doe", "{\n" +
                "  \"protocolVersion\":\"ECv2\",\n" +
                "  \"signature\":\"MEQCIH6Q4OwQ0jAceFEkGF0JID6sJNXxOEi4r+mA7biRxqBQAiAondqoUpU/bdsrAOpZIsrHQS9nwiiNwOrr24RyPeHA0Q\\u003d\\u003d\",\n" +
                "  \"intermediateSigningKey\":{\n" +
                "    \"signedKey\": \"{\\\"keyExpiration\\\":\\\"1542323393147\\\",\\\"keyValue\\\":\\\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/1+3HBVSbdv+j7NaArdgMyoSAM43yRydzqdg1TxodSzA96Dj4Mc1EiKroxxunavVIvdxGnJeFViTzFvzFRxyCw\\\\u003d\\\\u003d\\\"}\",\n" +
                "    \"signatures\": [\"MEYCIQCO2EIi48s8VTH+ilMEpoXLFfkxAwHjfPSCVED/QDSHmQIhALLJmrUlNAY8hDQRV/y1iKZGsWpeNmIP+z+tCQHQxP0v\"]\n" +
                "  },\n" +
                "  \"signedMessage\":\"{\\\"tag\\\":\\\"jpGz1F1Bcoi/fCNxI9n7Qrsw7i7KHrGtTf3NrRclt+U\\\\u003d\\\",\\\"ephemeralPublicKey\\\":\\\"BJatyFvFPPD21l8/uLP46Ta1hsKHndf8Z+tAgk+DEPQgYTkhHy19cF3h/bXs0tWTmZtnNm+vlVrKbRU9K8+7cZs\\\\u003d\\\",\\\"encryptedMessage\\\":\\\"mKOoXwi8OavZ\\\"}\"\n" +
                "}", false);
        googlePayInfo.testCardNumber = "4111111111111111";
        googlePayInfo.retained = true;
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createGooglePaymentMethod(googlePayInfo).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        //assertEquals("Succeeded!", trans.message);
        //assertNotNull(trans.result.token);
        // TODO: add asserts back in w/ good google info
    }
}
