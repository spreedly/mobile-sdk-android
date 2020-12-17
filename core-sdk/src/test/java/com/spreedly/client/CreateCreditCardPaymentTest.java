package com.spreedly.client;

import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateCreditCardPaymentTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void CreateCreditCardSucceeds() throws InterruptedException {
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 2032, 12);
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        test.assertComplete();
    }

    @Test
    public void CreateCreditCardHasToken() throws InterruptedException {
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 2032, 12);
        cc.retained = false;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);

        assertNotNull(trans.result.token);
    }

    @Test
    public void RetainedCreateCreditCardHasToken() throws InterruptedException {
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 2032, 12);
        cc.retained = true;

        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);

        assertNotNull(trans.result.token);
    }


    @Test
    public void badCreditCardFails() throws InterruptedException {
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 2032, 0);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        test.assertComplete();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertEquals("Month can't be blank", trans.message);
    }

    @Test
    public void initializationFailsWithEmptyCredentials() throws InterruptedException {
        SpreedlyClient badClient = SpreedlyClient.newInstance("", "", true);
        CreditCardInfo cc = new CreditCardInfo("Joe Jones", client.createString("5555555555554444"), client.createString("432"), 2030, 12);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        badClient.createCreditCardPaymentMethod(cc).subscribe(test);
        test.await();
        test.assertComplete();
        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);
        assertEquals("You must specify an environment_key parameter.", trans.message);
    }
}
