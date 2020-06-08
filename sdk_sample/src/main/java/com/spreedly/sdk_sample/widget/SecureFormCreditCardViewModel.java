package com.spreedly.sdk_sample.widget;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.securewidgets.SecureFormLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SecureFormCreditCardViewModel extends ViewModel {
    SecureFormLayout layout;
    public TextView token;
    TextView error;

    // TODO: Implement the ViewModel
    public void submitCreditCard() {
        token.setText("");
        error.setText("");
        layout.createCreditCardPaymentMethod().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull TransactionResult<PaymentMethodResult> paymentMethodResultTransactionResult) {
                Log.i("Spreedly", "Created Credit Card");
                try {
                    token.setText("Token: " + paymentMethodResultTransactionResult.result.token);
                } catch (Exception e) {
                    error.setText("Unexpected Error");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
}
