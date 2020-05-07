import com.spreedly.client.models.GooglePayInfo;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GooglePlayInfoTest {
    @Test
    public void CanCreateGooglePayInfo() {
        GooglePayInfo googlePay = new GooglePayInfo("Jane", "Doe", "sample signature", "sample version", "sample signing key", false);
        assertTrue(googlePay.firstName == "Jane" && googlePay.lastName == "Doe" && googlePay.signature == "sample signature" && googlePay.protocolVersion == "sample version" && googlePay.signedMessage == "sample signing key" && googlePay.retained == false);
    }

    @Test
    public void CanEncodeGooglePay() {
        GooglePayInfo googlePay = new GooglePayInfo("Jane", "Doe", "sample signature", "sample version", "sample signing key", false);
        googlePay.testCardNumber = "111111111111111";
        String expected = "{\"payment_method\":{\"retained\":false,\"google_pay\":{\"test_card_number\":\"111111111111111\",\"last_name\":\"Doe\",\"payment_data\":{\"signature\":\"sample signature\",\"protocolVersion\":\"sample version\",\"signedMessage\":\"sample signing key\"},\"first_name\":\"Jane\"}}}";
        JSONObject actual = googlePay.toJson(null, null);
        assertEquals(expected, actual.toString());
    }
}
