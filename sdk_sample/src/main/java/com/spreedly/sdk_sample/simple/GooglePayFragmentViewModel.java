package com.spreedly.sdk_sample.simple;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;


public class GooglePayFragmentViewModel extends ViewModel {
    MutableLiveData<String> name = new MutableLiveData<>("");
    MutableLiveData<String> token = new MutableLiveData<>(null);
    MutableLiveData<String> error = new MutableLiveData<>(null);
    MutableLiveData<Boolean> inProgress = new MutableLiveData<>(false);

    void create() {
        final SpreedlyClient client = SpreedlyClient.newInstance("", "", true);
        final GooglePayInfo info = new GooglePayInfo(name.getValue(), "", "", "", "", false);
        info.testCardNumber = "411111111111111";
        inProgress.setValue(true);
        token.postValue("");
        error.postValue("");
        client.createGooglePaymentMethod(info, null, null).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(TransactionResult<PaymentMethodResult> trans) {
                try {
                    if (trans.succeeded) {
                        Log.i("Spreedly", "trans.result.token: " + trans.result.token);
                        token.postValue(trans.result.token);
                    } else {
                        Log.e("Spreedly", "trans.message: " + trans.message);
                        error.postValue(trans.message);
                    }
                } finally {
                    inProgress.postValue(false);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Spreedly", e.getMessage(), e);
                error.postValue("UNEXPECTED ERROR: " + e.getMessage());
                inProgress.postValue(false);
            }
        });
    }
}
