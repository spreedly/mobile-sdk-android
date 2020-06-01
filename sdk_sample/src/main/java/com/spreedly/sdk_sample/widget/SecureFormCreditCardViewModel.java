package com.spreedly.sdk_sample.widget;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.securewidgets.SecureFormLayout;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SecureFormCreditCardViewModel extends ViewModel {
    SecureFormLayout layout;
    // TODO: Implement the ViewModel
    public void submitCreditCard() {
        layout.createCreditCardPaymentMethod().subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull TransactionResult<PaymentMethodResult> paymentMethodResultTransactionResult) {
                Log.i("Spreedly", "Created Credit Card");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
}
