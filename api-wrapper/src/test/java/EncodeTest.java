import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.enums.BankAccountType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncodeTest {
    SpreedlyClient client  =  SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);

    @Test
    public void CanEncodeCreditCard(){
        CreditCardInfo creditCard = new CreditCardInfo("Jane Doe", client.createString("sample card number"), client.createString("sample cvv"), 12, 2030);
        String expected = "{\"payment_method\":{\"retained\":false,\"credit_card\":{\"number\":\"sample card number\",\"full_name\":\"Jane Doe\",\"verification_value\":\"sample cvv\",\"month\":2030,\"year\":12},\"eligible_for_card_updater\":false,\"allow_blank_name\":false,\"allow_expired_date\":false,\"allow_blank_date\":false}}";
        String actual = creditCard.encode(null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void CanEncodeBankAccount(){
        BankAccountInfo bankAccount = new BankAccountInfo("Jane Doe", "1234567", client.createString("0000000"), BankAccountType.checking);
        String expected = "{\"payment_method\":{\"bank_account\":{\"bank_account_number\":\"0000000\",\"full_name\":\"Jane Doe\",\"bank_routing_number\":\"1234567\",\"bank_account_type\":\"checking\"}}}";
        String actual = bankAccount.encode(null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void CanEncodeGooglePay(){
        GooglePayInfo googlePay = new GooglePayInfo("Jane", "Doe", "sample signature", "sample version", "sample signing key", false);
        googlePay.testCardNumber = "111111111111111";
        String expected = "{\"payment_method\":{\"retained\":false,\"google_pay\":{\"test_card_number\":\"111111111111111\",\"last_name\":\"Doe\",\"payment_data\":{\"signature\":\"sample signature\",\"protocolVersion\":\"sample version\",\"signedMessage\":\"sample signing key\"},\"first_name\":\"Jane\"}}}";
        String actual = googlePay.encode(null, null);
        assertEquals(expected, actual);
    }
}
