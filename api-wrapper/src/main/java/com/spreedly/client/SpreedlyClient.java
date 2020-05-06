package com.spreedly.client;

import com.spreedly.client.models.ApplePayInfo;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.results.TransactionResult;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Single;

import java.io.Closeable;

public interface SpreedlyClient extends Closeable {
    static SpreedlyClient newInstance(String envKey, String envSecret, boolean test) {
        return new SpreedlyClientImpl(envKey, envSecret, test);
    }

    @NonNull SpreedlySecureOpaqueString createString(@NonNull String string);
    @NonNull Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod(@NonNull CreditCardInfo info, @Nullable String email, @Nullable JSONObject metadata);
    @NonNull Single<TransactionResult<PaymentMethodResult>> createBankPaymentMethod(@NonNull BankAccountInfo info, @Nullable String email, @Nullable JSONObject metadata);
    @NonNull Single<TransactionResult<PaymentMethodResult>> createGooglePaymentMethod(@NonNull GooglePayInfo info, @Nullable String email, @Nullable JSONObject metadata);
    @NonNull Single<TransactionResult<PaymentMethodResult>> createApplePaymentMethod(@NonNull ApplePayInfo info, @Nullable String email, @Nullable JSONObject metadata);
    @NonNull Single<TransactionResult<PaymentMethodResult>> recache(@NonNull String token, @NonNull SpreedlySecureOpaqueString cvv);
}
