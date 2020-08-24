package com.spreedly.client;

import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.enums.AccountType;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateBankAccountPaymentTest {
    SpreedlyClient client = null;

    @Before
    public void initialize() {
        client = SpreedlyClient.newInstance(TestCredentials.getUser(), TestCredentials.getPassword(), true);
    }

    @Test
    public void CreateBankAccountSucceeds() throws InterruptedException {
        BankAccountInfo bankAccountInfo = new BankAccountInfo("John Doe", "021000021", client.createString("9876543210"), AccountType.checking);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createBankPaymentMethod(bankAccountInfo).subscribe(test);
        test.await();
        test.assertComplete();
    }

    @Test
    public void CreateBankAccountGetsToken() throws InterruptedException {
        BankAccountInfo bankAccountInfo = new BankAccountInfo("John Doe", "021000021", client.createString("9876543210"), AccountType.checking);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createBankPaymentMethod(bankAccountInfo).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);

        assertNotNull(trans.result.token);
    }

    @Test
    public void BadInfoReturnsErrors() throws InterruptedException {
        BankAccountInfo bankAccountInfo = new BankAccountInfo("", "021000021", client.createString("9876543210"), AccountType.checking);
        TestObserver test = new TestObserver<TransactionResult<PaymentMethodResult>>();
        client.createBankPaymentMethod(bankAccountInfo).subscribe(test);
        test.await();
        test.assertComplete();

        TransactionResult<PaymentMethodResult> trans = (TransactionResult<PaymentMethodResult>) test.values().get(0);

        assertEquals("Full name can't be blank", trans.errors.get(0).message);
    }
}
