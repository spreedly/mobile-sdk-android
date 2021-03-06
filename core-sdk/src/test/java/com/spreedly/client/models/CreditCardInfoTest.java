package com.spreedly.client.models;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.enums.AccountType;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreditCardInfoTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void CanCreateCreditCardWithFullName() {
        CreditCardInfo creditCard = new CreditCardInfo("Jane Doe", client.createString("sample card number"), client.createString("sample cvv"), 12, 2030);
        assertTrue(creditCard.fullName == "Jane Doe" && creditCard.number.length == 18 && creditCard.verificationValue.length == 10 && creditCard.year == 12 && creditCard.month == 2030);
    }

    @Test
    public void CanCreateCreditCardWithFirstAndLastName() {
        CreditCardInfo creditCard = new CreditCardInfo("Jane", "Doe", client.createString("sample card number"), client.createString("sample cvv"), 12, 2030);
        assertTrue(creditCard.firstName == "Jane" && creditCard.lastName == "Doe" && creditCard.number.length == 18 && creditCard.verificationValue.length == 10 && creditCard.year == 12 && creditCard.month == 2030);
    }

    @Test
    public void CanEncodeCreditCard() {
        CreditCardInfo creditCard = new CreditCardInfo("Jane Doe", client.createString("sample card number"), client.createString("sample cvv"), 12, 2030);
        String expected = "{\"payment_method\":{\"credit_card\":{\"number\":\"samplecardnumber\",\"full_name\":\"Jane Doe\",\"verification_value\":\"samplecvv\",\"month\":2030,\"year\":12}}}";
        JSONObject actual = creditCard.toJson();
        assertEquals(expected, actual.toString());
    }

    @Test
    public void CanEncodeFullCreditCard() {
        CreditCardInfo creditCard = new CreditCardInfo("Jane Doe", client.createString("sample card number"), client.createString("sample cvv"), 12, 2030);
        creditCard.shippingAddress = new Address("555 Main St", "Apt 33", "Anytown", "WA", "98000", "USA", "5555555555");
        creditCard.address = new Address("555 Main St", "", "Anytown", "WA", "98000", "USA", "5555555555");
        creditCard.company = "Company inc.";
        creditCard.eligibleForCardUpdate = false;
        creditCard.retained = true;
        creditCard.allowExpiredDate = false;
        creditCard.allowBlankName = true;
        creditCard.allowBlankDate = true;
        creditCard.email = "sample@sample.com";
        String expected = "{\"payment_method\":{\"retained\":true,\"credit_card\":{\"zip\":\"98000\",\"country\":\"USA\",\"shipping_state\":\"WA\",\"address2\":\"\",\"city\":\"Anytown\",\"year\":12,\"address1\":\"555 Main St\",\"shipping_city\":\"Anytown\",\"number\":\"samplecardnumber\",\"full_name\":\"Jane Doe\",\"verification_value\":\"samplecvv\",\"month\":2030,\"shipping_zip\":\"98000\",\"shipping_address2\":\"Apt 33\",\"shipping_address1\":\"555 Main St\",\"shipping_phone_number\":\"5555555555\",\"company\":\"Company inc.\",\"phone_number\":\"5555555555\",\"state\":\"WA\",\"shipping_country\":\"USA\"},\"eligible_for_card_updater\":false,\"allow_blank_name\":true,\"allow_expired_date\":false,\"allow_blank_date\":true,\"email\":\"sample@sample.com\"}}";
        JSONObject actual = creditCard.toJson();
        assertEquals(expected, actual.toString());
    }

    @Test
    public void CanCreateCreditCardFromCopy() {
        CreditCardInfo copy = new CreditCardInfo("Jane Doe", client.createString("sample card number"), client.createString("sample cvv"), 12, 2030);
        CreditCardInfo creditCard = new CreditCardInfo(copy);
        assertTrue(creditCard.fullName == "Jane Doe" && creditCard.number == null && creditCard.verificationValue == null && creditCard.year == 12 && creditCard.month == 2030);
    }

    @Test
    public void CanCreateCreditCardFromBankAccountCopy() {
        BankAccountInfo copy = new BankAccountInfo("Jane Doe", "1234567", client.createString("0000000"), AccountType.checking);
        CreditCardInfo creditCard = new CreditCardInfo(copy);
        assertTrue(creditCard.fullName == "Jane Doe" && creditCard.number == null && creditCard.verificationValue == null);
    }

    @Test
    public void CanCreateEmptyCard() {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        assertNotNull(creditCardInfo);
    }
}
