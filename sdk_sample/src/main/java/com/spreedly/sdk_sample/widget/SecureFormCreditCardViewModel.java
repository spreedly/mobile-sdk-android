package com.spreedly.sdk_sample.widget;

import android.util.Log;
import android.widget.TextView;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.sdk_sample.R;
import com.spreedly.securewidgets.SecureFormLayout;

import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SecureFormCreditCardViewModel extends ViewModel {
    @NonNull
    public TextView token;
    @NonNull SecureFormLayout layout;
    @NonNull TextView error;

    // TODO: Implement the ViewModel
    public void submitCreditCard() {
        token.setText("");
        error.setText("");

        layout.createCreditCardPaymentMethod().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<TransactionResult<? extends PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull TransactionResult<? extends PaymentMethodResult> paymentMethodResultTransactionResult) {
                Log.i("Spreedly", "Created Credit Card");
                String result = "Token: " + paymentMethodResultTransactionResult.result.token;
                try {
                    token.setText(result);
                } catch (Exception e) {
                    error.setText(R.string.generic_error);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                error.setText(R.string.generic_error);
            }
        });
    }

    public void setDefaults() {

    }
}
