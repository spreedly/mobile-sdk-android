package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.results.CreditCardResult;
import com.spreedly.client.models.results.TransactionResult;

import io.reactivex.rxjava3.core.Single;

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

    public Single<TransactionResult<CreditCardResult>> createCreditCardPaymentMethod() {
        return null;
    }

    public Single<TransactionResult<BankAccountResult>> createBankAccountPaymentMethod() {
        return null;
    }

    private int nameTold(String name) {
        return 0;
    }


}
