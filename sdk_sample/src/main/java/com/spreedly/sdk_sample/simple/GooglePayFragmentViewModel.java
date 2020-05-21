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
        final SpreedlyClient client = SpreedlyClient.newInstance("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
        final String[] names = name.getValue().split(" ", 2);
        final String paymentData = "{\n" +
                "  \"protocolVersion\":\"ECv2\",\n" +
                "  \"signature\":\"MEQCIH6Q4OwQ0jAceFEkGF0JID6sJNXxOEi4r+mA7biRxqBQAiAondqoUpU/bdsrAOpZIsrHQS9nwiiNwOrr24RyPeHA0Q\\u003d\\u003d\",\n" +
                "  \"intermediateSigningKey\":{\n" +
                "    \"signedKey\": \"{\\\"keyExpiration\\\":\\\"1542323393147\\\",\\\"keyValue\\\":\\\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/1+3HBVSbdv+j7NaArdgMyoSAM43yRydzqdg1TxodSzA96Dj4Mc1EiKroxxunavVIvdxGnJeFViTzFvzFRxyCw\\\\u003d\\\\u003d\\\"}\",\n" +
                "    \"signatures\": [\"MEYCIQCO2EIi48s8VTH+ilMEpoXLFfkxAwHjfPSCVED/QDSHmQIhALLJmrUlNAY8hDQRV/y1iKZGsWpeNmIP+z+tCQHQxP0v\"]\n" +
                "  },\n" +
                "  \"signedMessage\":\"{\\\"tag\\\":\\\"jpGz1F1Bcoi/fCNxI9n7Qrsw7i7KHrGtTf3NrRclt+U\\\\u003d\\\",\\\"ephemeralPublicKey\\\":\\\"BJatyFvFPPD21l8/uLP46Ta1hsKHndf8Z+tAgk+DEPQgYTkhHy19cF3h/bXs0tWTmZtnNm+vlVrKbRU9K8+7cZs\\\\u003d\\\",\\\"encryptedMessage\\\":\\\"mKOoXwi8OavZ\\\"}\"\n" +
                "}";
        final GooglePayInfo info = new GooglePayInfo(names[0], names[1], paymentData, false);
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
