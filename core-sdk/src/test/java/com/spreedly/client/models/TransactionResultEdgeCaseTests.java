package com.spreedly.client.models;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TransactionResultEdgeCaseTests {
    List<SpreedlyError> errors = new ArrayList<SpreedlyError>();

    @Before
    public void initialize() {
        errors.add(new SpreedlyError("attribute", "key", "error message"));
    }

    @Test
    public void messageIsEmpty() {
        TransactionResult<PaymentMethodResult> transaction = new TransactionResult<PaymentMethodResult>(null, null, null, false, null, false, null, null, "", errors, null);
        assertEquals("error message", transaction.message);
    }

    @Test
    public void messageIsNull() {
        TransactionResult<PaymentMethodResult> transaction = new TransactionResult<PaymentMethodResult>(null, null, null, false, null, false, null, null, null, errors, null);
        assertEquals("error message", transaction.message);
    }

    @Test
    public void messageHasValue() {
        TransactionResult<PaymentMethodResult> transaction = new TransactionResult<PaymentMethodResult>(null, null, null, false, null, false, null, null, "success message", errors, null);
        assertEquals("success message", transaction.message);
    }

    @Test
    public void errorsIsEmpty() {
        TransactionResult<PaymentMethodResult> transaction = new TransactionResult<PaymentMethodResult>(null, null, null, false, null, false, null, null, null, new ArrayList<SpreedlyError>(), null);
        assertNull(transaction.message);
    }

    @Test
    public void errorsIsNull() {
        TransactionResult<PaymentMethodResult> transaction = new TransactionResult<PaymentMethodResult>(null, null, null, false, null, false, null, null, null, null, null);
        assertNull(transaction.message);
    }
}
