package com.spreedly.client;

import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.PaymentMethodMeta;
import com.spreedly.client.models.PaymentMethodResult;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.TransactionResult;

import io.reactivex.rxjava3.core.Single;

import java.io.Closeable;
import java.io.IOException;

public interface SpreedlyClient extends Closeable {
    SpreedlySecureOpaqueString createString();
    Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod(CreditCardInfo info);
    Single<TransactionResult<PaymentMethodResult>> createBankPaymentMethod(BankAccountInfo info);
    Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv);
}
