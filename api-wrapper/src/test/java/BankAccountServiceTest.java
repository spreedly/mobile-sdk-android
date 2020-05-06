import org.junit.Before;
import org.junit.Test;

import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.enums.BankAccountType;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.client.SpreedlyClient;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.*;

public class BankAccountServiceTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void TokenizeBankAccountSucceeds() throws InterruptedException {
        BankAccountInfo bankAccountInfo = new BankAccountInfo("John Doe", "021000021", client.createString("9876543210"), BankAccountType.checking);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createBankPaymentMethod(bankAccountInfo, null, null).subscribe(test);
        test.await();
        test.assertComplete();
    }
}
