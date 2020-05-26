package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * TODO: document your custom view class.
 */
public class SecureFormLayout extends LinearLayout {
    SpreedlyClient spreedlyClient;

    public SecureFormLayout(Context context) {
        super(context);
    }

    public SecureFormLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSpreedlyClient(String envKey, String envSecret, boolean test) {
        spreedlyClient = SpreedlyClient.newInstance(envKey, envSecret, test);
    }

    public SpreedlyClient getClient() {
        return spreedlyClient;
    }

    public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod() {
        Log.i("Spreedly", "createCreditCardPaymentMethod firing");
        SecureTextField creditCardNumber = findViewById(R.id.credit_card_number);
        SecureTextField ccv = findViewById(R.id.ccv);
        TextInputLayout fullName = findViewById(R.id.full_name);
        TextInputLayout monthInput = findViewById(R.id.cc_month);
        TextInputLayout yearInput = findViewById(R.id.cc_year);
        int year;
        EditText editText = yearInput.getEditText();
        String yearString = editText.getText().toString();
        year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthInput.getEditText().getText().toString());

        final CreditCardInfo info = new CreditCardInfo(fullName.getEditText().getText().toString(), creditCardNumber.getText(), ccv.getText(), year, month);
        Single<TransactionResult<PaymentMethodResult>> result = spreedlyClient.createCreditCardPaymentMethod(info, null, null);
        result.subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(TransactionResult<PaymentMethodResult> trans) {
                try {
                    if (trans.succeeded) {
                        Log.i("Spreedly", "trans.result.token: " + trans.result.token);
                    } else {
                        Log.e("Spreedly", "trans.message: " + trans.message);
                        handleErrors(trans.errors);
                    }
                } finally {
                    Log.i("Spreedly", "Completed");
                }
            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Log.e("Spreedly", e.getMessage(), e);
                //error.postValue("UNEXPECTED ERROR: " + e.getMessage());
                //inProgress.postValue(false);
            }
        });
        return result;
    }

    private void handleErrors(List<SpreedlyError> errors) {
    }

    public Single<TransactionResult<BankAccountResult>> createBankAccountPaymentMethod() {
        return null;
    }

    private int nameTold(String name) {
        return 0;
    }


}
