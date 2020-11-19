package com.spreedly.sdk_sample.ui.main;


import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.sdk_sample.R;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.threedssecure.SpreedlyThreeDS;
import com.spreedly.threedssecure.SpreedlyThreeDSError;
import com.spreedly.threedssecure.SpreedlyThreeDSTransactionRequest;
import com.spreedly.threedssecure.SpreedlyThreeDSTransactionRequestListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThreeDSViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    @NonNull
    public TextView token;
    @NonNull
    SpreedlyThreeDS spreedlyThreeDS;
    @NonNull
    SpreedlyClient client;
    @SuppressLint("StaticFieldLeak")
    @NonNull
    TextView errorView;
    @SuppressLint("StaticFieldLeak")
    @NonNull
    TextInputLayout amount;
    @SuppressLint("StaticFieldLeak")
    @NonNull
    SecureCreditCardField secureCreditCardField;
    @NonNull
    Spinner challengeType;
    CreditCardInfo info;
    SpreedlyThreeDSTransactionRequest threeDSTransactionRequest;

    SpreedlyThreeDSTransactionRequestListener listener = new SpreedlyThreeDSTransactionRequestListener() {
        public void success(@NonNull String status) {
            errorView.setText("success: " + status);
            Log.i("Spreedly", status);
        }

        public void cancelled() {
            errorView.setText("cancelled");
            Log.i("Spreedly", "Cancelled");
        }

        public void timeout() {
            errorView.setText("timeout");
            Log.i("Spreedly", "Timeout");
        }

        public void error(@androidx.annotation.NonNull SpreedlyThreeDSError error) {
            Log.i("Spreedly", error.type + " " + error.message);
            errorView.setText("error: " + error.type + " " + error.message);
        }
    };

    public void submitCreditCard() {
        tokenize().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull TransactionResult<PaymentMethodResult> tokenized) {
                threeDSTransactionRequest = spreedlyThreeDS.createTransactionRequest();
                String serialized = threeDSTransactionRequest.serialize();
                serversidePurchase(client, serialized, tokenized.result.token, "M8k0FisOKdAmDgcQeIKlHE7R1Nf", new SuccessOrFailure() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        threeDSTransactionRequest.doChallenge(result, listener);
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.i("Spreedly", "Message: " + message);
                        errorView.setText(message);
                    }
                }).subscribe(new SingleObserver<Object>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Object o) {
                        Log.i("Spreedly", o.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("Spreedly", "Something");
                    }
                });
            }

            @Override
            public void onError(@NonNull Throwable e) {
                errorView.setText(R.string.generic_error);
            }
        });
    }


    public Single<TransactionResult<PaymentMethodResult>> tokenize() {
        info = new CreditCardInfo("Dolly Dog", null, null, secureCreditCardField.getText(), client.createString("919"), 2029, challengeType.getSelectedItemPosition() + 1);
        return client.createCreditCardPaymentMethod(info);
    }

    public @NonNull
    Single<Object> serversidePurchase(SpreedlyClient client, String deviceInfo, String token, String scaProviderKey, SuccessOrFailure successOrFailure) {
        String baseUrl = "https://core.spreedly.com/v1";
        String gateway = "BkXcmxRDv8gtMUwu5Buzb4ZbqGe";
        String completeUrl = baseUrl + "/gateways/" + gateway + "/purchase.json";
        JSONObject requestBody = new JSONObject();
        JSONObject transactionBody = new JSONObject();
        int centAmount;
        try {
            centAmount = (int) (Double.parseDouble(amount.getEditText().getText().toString()) * 100);
        } catch (Exception e) {
            Log.e("Spreedly", "Cent amount error" + e.getMessage());
            centAmount = 4;
        }
        Log.i("Spreedly", "cent amount" + centAmount);
        try {
            transactionBody.put("payment_method_token", token);
            transactionBody.put("amount", centAmount);
            transactionBody.put("redirect_url", "http://test.com/");
            transactionBody.put("callback_url", "http://test.com/");
            transactionBody.put("device_info", deviceInfo);
            transactionBody.put("channel", "app");
            transactionBody.put("sca_provider_key", scaProviderKey);
            transactionBody.put("currency_code", "EUR");
            requestBody.put("transaction", transactionBody);
        } catch (Exception e) {
            //Do something with error
        }
        Call call;
        call = new OkHttpClient().newCall(new Request.Builder()
                .url(completeUrl)
                .method("POST", RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .header("Authorization", client.getCredentials())
                .build());

        return Single.create(emitter -> call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                successOrFailure.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String responseString = response.body().string();
                    JSONObject responseObject = new JSONObject(responseString);
                    JSONObject transactionObject = responseObject.getJSONObject("transaction");
                    String successObject = transactionObject.getString("state");
                    if (successObject.equals("succeeded")) {
                        errorView.setText("frictionless success");
                        return;
                    }
                    JSONObject scaAuthentication = transactionObject.getJSONObject("sca_authentication");
                    if (scaAuthentication != null) {
                        successOrFailure.onSuccess(scaAuthentication);
                        return;
                    }
                    successOrFailure.onFailure("Bad Result");

                } catch (Exception e) {
                    successOrFailure.onFailure(e.getMessage());
                }
            }
        }));
    }

    interface SuccessOrFailure {
        void onSuccess(JSONObject result);

        void onFailure(String message);
    }
}
