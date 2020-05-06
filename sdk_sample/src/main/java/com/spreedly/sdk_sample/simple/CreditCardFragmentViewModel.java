package com.spreedly.sdk_sample.simple;

import android.util.Log;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class CreditCardFragmentViewModel extends ViewModel {
    MutableLiveData<String> name = new MutableLiveData<>("");
    MutableLiveData<String> cc = new MutableLiveData<>("");
    MutableLiveData<String> ccv = new MutableLiveData<>("");
    MutableLiveData<Integer> year = new MutableLiveData<>(null);
    MutableLiveData<Integer> month = new MutableLiveData<>(null);
    MutableLiveData<Boolean> inProgress = new MutableLiveData<>(false);
    MutableLiveData<String> token = new MutableLiveData<>(null);
    MutableLiveData<String> error = new MutableLiveData<>(null);

    void create() {
        final SpreedlyClient client = SpreedlyClient.newInstance("", "");
        final CreditCardInfo info = new CreditCardInfo();
        info.fullName = name.getValue();
        info.number = client.createString(cc.getValue());
        info.cvv = client.createString(ccv.getValue());
        info.year = year.getValue().toString();
        info.month = month.getValue().toString();

        inProgress.setValue(true);
        client.createCreditCardPaymentMethod(info).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
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
