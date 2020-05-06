import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreditCardServiceTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword());
    }

    @Test
    public void TokenizeSucceeds() throws InterruptedException {
        CreditCardInfo cc = new CreditCardInfo();
        cc.fullName = "Joe Jones";
        cc.number = client.createString("5555555555554444");
        cc.cvv = client.createString("432");
        cc.month = "3";
        cc.year = "2032";
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.recache(trans.result.token, client.createString("423")).subscribe(test);
        test.await();
        test.assertComplete();
    }

    @Test
    public void badCreditCardFails() throws InterruptedException {
        CreditCardInfo badCC = new CreditCardInfo();
        badCC.number = client.createString("5555555555554444");
        badCC.cvv = client.createString("432");
        badCC.month = "3";

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(badCC).subscribe(test);
        test.await();
        test.assertComplete();
    }
}
