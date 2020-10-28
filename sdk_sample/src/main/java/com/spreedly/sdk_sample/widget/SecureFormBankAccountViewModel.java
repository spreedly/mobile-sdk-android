package com.spreedly.sdk_sample.widget;

import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.sdk_sample.R;
import com.spreedly.securewidgets.SecureFormLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SecureFormBankAccountViewModel extends ViewModel {
    @NonNull
    public TextView token;
    @NonNull Spinner spinner;

    @NonNull SecureFormLayout layout;
    @NonNull TextView error;

    // TODO: Implement the ViewModel
    public void submitBankAccount() {
        token.setText("");
        error.setText("");
        layout.createBankAccountPaymentMethod().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull TransactionResult<PaymentMethodResult> paymentMethodResultTransactionResult) {
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

            }
        });
    }
}
