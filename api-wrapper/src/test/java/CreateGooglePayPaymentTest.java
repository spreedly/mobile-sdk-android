import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.IntermediateSigningKey;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.assertNotNull;

public class CreateGooglePayPaymentTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void CreateGooglePaySucceeds() throws InterruptedException {
        GooglePayInfo googlePayInfo = new GooglePayInfo("John", "Doe", "MEUCIA6SGVRwhOyeYRkeDUUNwB/kGtyfQAlOsg7NZydT17u/AiEA48BhWGQEF1EbEU0J+m8eSK3rTfhok9QqpiFVbME+Ky0=", "ECv1", "{\"encryptedMessage\":\"3v4IcT/eovIDP2WF8iRUy4qWQnE9Cx0vQxIZ5f9i3Emv3Tzs1AzvB7cxXhxrjp9FVIzdOwsZAPAsm03gvoYq8Xdr70XvrVRd2MFwQhMC7IV/uEsthw4JsR8oCkbI5v/zqhu2B+JodFgavNliHcpKBgijy2D6bpx7jXEkM39M/L4oBObFxFrhVSLA1GjOV6A5gLAXNXt0ffkCYekihqAyJrWlk3sCBDCF5SUiAKEIOIZtzZLgusxjVp6ufZHOHm/53uhAi6JWSJ1E6G5aaYGtULYdwgURHtxN5OIzQPYlEGctaQd5tgfCsBFfGkYyN1GRNgclbaLzAfk/Jn7/6IVKuV0ol3xubTcnjGTZXwtTjiEyYDoz1yVqB9ViMmJa55L6nBtbbAkcNEgAi7dPnrbvBGEP7QWsNT9D71g8SWrlRTCYUAOyuamaQhofG4ul1IVjmltdAy2BHBWpqgJnR9kczydQyE7uDiqhSC1/0eG8GCGIqoi8XfOioGXfMyLZ1p2ZcNK9ECjzUrH/edrwgtShxgWuWMwQTM4DQlVTAA/R4DVs192YWZcc7jm5wLqZ0+XEaPuighJM1Ps1Egeccg\\u003d\\u003d\",\"ephemeralPublicKey\":\"BA2SvF9BdCX7Tl1wwRkyLzTfqhctobhZgSugC9Cz77XNUCBOBMfFyJQt506PUs89D6IJZZfOkZopy0shRF9Uph4\\u003d\",\"tag\":\"Uhin1BE7KAuuiam7eEQFimRUDd9Xn6tZc2fClTpfrXQ\\u003d\"}", false);
        googlePayInfo.testCardNumber = "4111111111111111";
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createGooglePaymentMethod(googlePayInfo, null, null).subscribe(test);
        test.await();
        test.assertComplete();
    }

    @Test
    public void CreateGooglePayGetsToken() throws InterruptedException {
        GooglePayInfo googlePayInfo = new GooglePayInfo("John", "Doe", "MEQCIH6Q4OwQ0jAceFEkGF0JID6sJNXxOEi4r+mA7biRxqBQAiAondqoUpU/bdsrAOpZIsrHQS9nwiiNwOrr24RyPeHA0Q\\u003d\\u003d", "ECv2", "{\"tag\":\"jpGz1F1Bcoi/fCNxI9n7Qrsw7i7KHrGtTf3NrRclt+U\\u003d\",\"ephemeralPublicKey\":\"BJatyFvFPPD21l8/uLP46Ta1hsKHndf8Z+tAgk+DEPQgYTkhHy19cF3h/bXs0tWTmZtnNm+vlVrKbRU9K8+7cZs\\u003d\",\"encryptedMessage\":\"mKOoXwi8OavZ\"}", false);
        googlePayInfo.intermediateSigningKey = new IntermediateSigningKey("{\"keyExpiration\":\"1542323393147\",\"keyValue\":\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/1+3HBVSbdv+j7NaArdgMyoSAM43yRydzqdg1TxodSzA96Dj4Mc1EiKroxxunavVIvdxGnJeFViTzFvzFRxyCw\\u003d\\u003d\"}", new String[]{"MEYCIQCO2EIi48s8VTH+ilMEpoXLFfkxAwHjfPSCVED/QDSHmQIhALLJmrUlNAY8hDQRV/y1iKZGsWpeNmIP+z+tCQHQxP0v"});
        googlePayInfo.testCardNumber = "4111111111111111";
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        JSONObject json = googlePayInfo.toJson(null, null);
        client.createGooglePaymentMethod(googlePayInfo, null, null).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);

        assertNotNull(trans.result.token);
    }
}
