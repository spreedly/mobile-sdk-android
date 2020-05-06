import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.spec.OAEPParameterSpec;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.*;

public class CreditCardServiceTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void TokenizeSucceeds() throws InterruptedException {
        CreditCardInfo cc =  new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 3, 2032);
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc, null, null).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);

        assertTrue(trans.succeeded);
    }

    @Test
    public void CanRecache() throws InterruptedException {
        CreditCardInfo cc =  new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 3, 2032);
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc, null, null).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        if (trans == null || trans.result == null ) { return; }
        client.recache(trans.result.token, client.createString("423")).subscribe(test);
        test.await();
        test.assertComplete();
        trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertTrue(trans.succeeded);

    }

    @Test
    public void badCreditCardFails() throws InterruptedException {
        CreditCardInfo cc =  new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 0, 2032);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc, null, null).subscribe(test);
        test.await();
        test.assertComplete();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertEquals("Month can't be blank", trans.message);
    }

    @Test
    public void initializationFailsWithEmptyCredentials() throws InterruptedException {
        SpreedlyClient badClient = SpreedlyClient.newInstance("", "", true);
        CreditCardInfo cc =  new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 0, 2032);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        badClient.createCreditCardPaymentMethod(cc, null, null).subscribe(test);
        test.await();
        test.assertComplete();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertEquals("Unable to authenticate using the given environment_key and access_token.  Please check your credentials.", trans.message);
    }
}
