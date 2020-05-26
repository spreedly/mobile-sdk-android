package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

    SecureTextField creditCardNumber;
    SecureTextField ccv;
    TextInputLayout fullName;
    TextInputLayout month;
    TextInputLayout year;

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

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod() {
        Log.i("Spreedly", "createCreditCardPaymentMethod firing");
        final CreditCardInfo info = new CreditCardInfo(getString(fullName), creditCardNumber.getText(), ccv.getText(), getNumber(year), getNumber(month));
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

    public void handleErrors(List<SpreedlyError> errors) {
        try {
            TextInputLayout v = new TextInputLayout(this.getContext());
            this.addView(v);
            int y = 7;
            v.setHint("Here's a test");
            int x = 4 + y;
            for (int i = 0; i < errors.size(); i++) {
                SpreedlyError error = errors.get(i);
                switch (error.attribute) {
                    case "name":
                        fullName.setError(error.message);
                    case "month":
                        month.setError(error.message);
                    case "year":
                        year.setError(error.message);
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Spreedly", e.getMessage());
            throw e;
        }
    }

    public Single<TransactionResult<BankAccountResult>> createBankAccountPaymentMethod() {
        return null;
    }

    private int nameTold(String name) {
        return 0;
    }

    private int getNumber(TextInputLayout textInputLayout) {
        try {
            return Integer.parseInt(textInputLayout.getEditText().getText().toString());
        } catch (NullPointerException | NumberFormatException e) {
            return 0;
        }
    }

    private String getString(TextInputLayout textInputLayout) {
        try {
            return textInputLayout.getEditText().getText().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

    private void init() {
        creditCardNumber = findViewById(R.id.credit_card_number);
        ccv = findViewById(R.id.ccv);
        fullName = findViewById(R.id.full_name);
        month = findViewById(R.id.cc_month);
        year = findViewById(R.id.cc_year);
    }



}
