import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.Address;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.enums.BankAccountType;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankAccountInfoTest {
    SpreedlyClient client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);

    @Test
    public void CanCreateBankAccountWithFullName() {
        BankAccountInfo bankAccount = new BankAccountInfo("Jane Doe", "1234567", client.createString("0000000"), BankAccountType.checking);
        assertTrue(bankAccount.fullName == "Jane Doe" && bankAccount.routingNumber == "1234567" && bankAccount.accountNumber.length == 7 && bankAccount.accountType == BankAccountType.checking);
    }

    @Test
    public void CanCreateBankAccountWithFirstAndLast() {
        BankAccountInfo bankAccount = new BankAccountInfo("Jane", "Doe", "1234567", client.createString("0000000"), BankAccountType.checking);
        assertTrue(bankAccount.firstName == "Jane" && bankAccount.lastName == "Doe" && bankAccount.routingNumber == "1234567" && bankAccount.accountNumber.length == 7 && bankAccount.accountType == BankAccountType.checking);
    }

    @Test
    public void CanEncodeBankAccount() {
        BankAccountInfo bankAccount = new BankAccountInfo("Jane Doe", "1234567", client.createString("0000000"), BankAccountType.checking);
        String expected = "{\"payment_method\":{\"bank_account\":{\"bank_account_number\":\"0000000\",\"full_name\":\"Jane Doe\",\"bank_routing_number\":\"1234567\",\"bank_account_type\":\"checking\"}}}";
        JSONObject actual = bankAccount.toJson(null, null);
        assertEquals(expected, actual.toString());
    }

    @Test
    public void CanEncodeFullBankAccount() {
        BankAccountInfo bankAccount = new BankAccountInfo("Jane Doe", "1234567", client.createString("0000000"), BankAccountType.checking);
        bankAccount.retained = true;
        bankAccount.shippingAddress = new Address("555 Main St", "Apt 33", "Anytown", "WA", "98000", "USA", "5555555555");
        bankAccount.address = new Address("555 Main St", "", "Anytown", "WA", "98000", "USA", "5555555555");
        bankAccount.bankAccountHolderType = "personal";
        bankAccount.company = "Company inc.";
        String expected = "{\"payment_method\":{\"retained\":true,\"email\":\"sample@sample.com\",\"bank_account\":{\"zip\":\"98000\",\"country\":\"USA\",\"bank_account_number\":\"0000000\",\"shipping_state\":\"WA\",\"address2\":\"\",\"city\":\"Anytown\",\"address1\":\"555 Main St\",\"shipping_city\":\"Anytown\",\"bank_routing_number\":\"1234567\",\"full_name\":\"Jane Doe\",\"shipping_zip\":\"98000\",\"shipping_address2\":\"Apt 33\",\"shipping_address1\":\"555 Main St\",\"shipping_phone_number\":\"5555555555\",\"company\":\"Company inc.\",\"phone_number\":\"5555555555\",\"state\":\"WA\",\"bank_account_type\":\"checking\",\"bank_account_holder_type\":\"personal\",\"shipping_country\":\"USA\"}}}";
        JSONObject actual = bankAccount.toJson("sample@sample.com", null);
        assertEquals(expected, actual.toString());
    }
}
