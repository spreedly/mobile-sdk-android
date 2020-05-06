package com.spreedly.sdk_sample.simple;

import android.util.Log;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class BankAccountFragmentViewModel extends ViewModel {
    MutableLiveData<String> name = new MutableLiveData<>("");
    MutableLiveData<String> account = new MutableLiveData<>("");
    MutableLiveData<String> routing = new MutableLiveData<>("");
    MutableLiveData<String> type = new MutableLiveData<>("checking");
    MutableLiveData<Boolean> inProgress = new MutableLiveData<>(false);
    MutableLiveData<String> token = new MutableLiveData<>(null);
    MutableLiveData<String> error = new MutableLiveData<>(null);

    void create() {
        final SpreedlyClient client = SpreedlyClient.newInstance("", "");
        final BankAccountInfo info = new BankAccountInfo();
        info.fullName = name.getValue();
        info.accountNumber = account.getValue();
        info.accountType = type.getValue();
        info.routingNumber = routing.getValue();

        inProgress.setValue(true);
        client.createBankPaymentMethod(info).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
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
