package com.spreedly.sdk_sample.express;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.express.ExpressView;
import com.spreedly.express.OnCustomEventListener;
import com.spreedly.sdk_sample.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class ExpressCreditCardViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    ExpressView mainView;
    TextView token;

    @NonNull
    public OnCustomEventListener onSubmit() {
        return new OnCustomEventListener() {
            @Override
            public void onEvent() {
                Log.i("Spreedly", "On Event");
                mainView.result.subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.i("Spreedly", "On Subscribe");
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull TransactionResult<PaymentMethodResult> paymentMethodResultTransactionResult) {
                        Log.i("Spreedly", "Created Credit Card");
                        String result = "Token: " + paymentMethodResultTransactionResult.result.token;
                        try {
                            token.setText(result);
                        } catch (Exception e) {
                            token.setText(R.string.generic_error);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        token.setText(e.getMessage());
                        Log.e("Spreedly", e.getMessage());
                    }
                });
            }
        };
    }
}
