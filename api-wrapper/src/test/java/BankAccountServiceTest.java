import org.junit.Before;
import org.junit.Test;

import com.spreedly.client.SpreedlyClientImpl;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.PaymentMethodResult;
import com.spreedly.client.models.TransactionResult;
import com.spreedly.client.SpreedlyClient;
import static org.junit.Assert.*;

public class BankAccountServiceTest {
    SpreedlyClient client = null;
    TransactionResult<PaymentMethodResult> result;
    @Before
    public void initialize() {
        client = new SpreedlyClientImpl(TestCredentials.getUser(), TestCredentials.getPassword());
        BankAccountInfo bankAccountInfo = new BankAccountInfo();
        bankAccountInfo.fullName = "John Doe";
        bankAccountInfo.accountNumber = "9876543210";
        bankAccountInfo.routingNumber = "021000021";
        bankAccountInfo.bankAccountHolderType = "personal";
        bankAccountInfo.accountType = "checking";
        client.createBankPaymentMethod(bankAccountInfo).subscribe((res) -> result = res);
    }

    @Test
    public void TokenizeBankAccountSucceeds(){
        assertTrue(result.isSucceeded());
    }

    @Test
    public void TokenizeReturnsPaymentToken(){
        assertNotNull(result.getResult().getToken());
    }
}
