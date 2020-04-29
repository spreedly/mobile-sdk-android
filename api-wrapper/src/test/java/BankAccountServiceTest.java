import org.junit.Before;
import org.junit.Test;

import spreedlyclient.classes.BankAccountInfo;
import spreedlyclient.classes.CreditCardInfo;
import spreedlyclient.classes.PaymentMethodResult;
import spreedlyclient.classes.TransactionResult;
import spreedlyclient.services.BankAccountService;
import spreedlyclient.services.SpreedlyClient;

import static org.junit.Assert.*;

public class BankAccountServiceTest {
    SpreedlyClient<BankAccountInfo> client = null;
    TransactionResult<PaymentMethodResult> result;
    @Before
    public void initialize() {
        client = new BankAccountService(TestCredentials.getUser(), TestCredentials.getPassword());
        BankAccountInfo bankAccountInfo = new BankAccountInfo();
        bankAccountInfo.fullName = "John Doe";
        bankAccountInfo.accountNumber = "9876543210";
        bankAccountInfo.routingNumber = "021000021";
        bankAccountInfo.bankAccountHolderType = "personal";
        bankAccountInfo.accountType = "checking";
        client.tokenize(bankAccountInfo).subscribe((res) -> result = res);
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
