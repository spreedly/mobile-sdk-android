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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;


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
        return result.subscribeOn(AndroidSchedulers.mainThread()).map((transaction) -> {
            if (!transaction.succeeded) {
                handleErrors(transaction.errors);
            }
            return transaction;
        });
    }

    public void handleErrors(List<SpreedlyError> errors) {
        try {
            for (int i = 0; i < errors.size(); i++) {
                SpreedlyError error = errors.get(i);
                switch (error.attribute) {
                    case "number":
                        creditCardNumber.setError(error.message);
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
        creditCardNumber = findViewById(R.id.spreedly_credit_card_number);
        ccv = findViewById(R.id.spreedly_ccv);
        fullName = findViewById(R.id.spreedly_full_name);
        month = findViewById(R.id.spreedly_cc_month);
        year = findViewById(R.id.spreedly_cc_year);
    }
}
