package com.spreedly.client;

import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.Test;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecacheTest {

    @Test
    public void CanRecache() throws InterruptedException {
        SpreedlyClient client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", null, null, client.createString("5555555555554444"), client.createString("432"), 3, 2032);
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        if (trans == null || trans.result == null) {
            return;
        }
        client.recache(trans.result.token, client.createString("423")).subscribe(test);
        test.await();
        test.assertComplete();
    }

    @Test
    public void RecacheReturnsToken() throws InterruptedException {
        SpreedlyClient client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", null, null, client.createString("5555555555554444"), client.createString("432"), 3, 2032);
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        if (trans == null || trans.result == null) {
            return;
        }
        client.recache(trans.result.token, client.createString("423")).subscribe(test);
        test.await();
        trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertNotNull(trans.result.token);
    }

    @Test
    public void BadTokenReturnsErrorMessage() throws InterruptedException {
        SpreedlyClient client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.recache("notatoken", client.createString("423")).subscribe(test);
        test.await();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertEquals("Unable to find the specified payment method.", trans.message);
    }
}
