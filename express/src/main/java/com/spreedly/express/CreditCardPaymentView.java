package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

public class CreditCardPaymentView extends PaymentView {
    SecureCreditCardField secureCreditCardField;
    SecureTextField ccvField;
    SecureExpirationDate secureExpirationDate;

    public CreditCardPaymentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CreditCardPaymentView(@NonNull Context context, boolean showBilling, boolean showShipping, @Nullable String submitButtonText, boolean includeBackButton) {
        super(context);
        this.showBilling = showBilling;
        this.showShipping = showShipping;
        this.submitButtonText = submitButtonText;
        this.includeBackButton = true;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        secureFormLayout = new SecureFormLayout(this.getContext());
        secureFormLayout.setOrientation(LinearLayout.VERTICAL);
        setLabel("Payment Method", paymentLabel);
        setFullName();
        setCC();
        if (includeBackButton) {
            Button backButton = new Button(secureFormLayout.getContext());
            backButton.setText(R.string.back);
            backButton.setOnClickListener((l) -> backButtonListener.onEvent());
            secureFormLayout.addView(backButton);
        }
        submitButton = new Button(secureFormLayout.getContext());
        submitButton.setText(submitButtonText);
        submitButton.setOnClickListener(b -> submit());
        secureFormLayout.addView(submitButton);
        secureFormLayout.onFinishInflate();
        this.addView(secureFormLayout);
    }


    private void setCC() {
        secureCreditCardField = new SecureCreditCardField(secureFormLayout.getContext());
        secureCreditCardField.setId(R.id.spreedly_credit_card_number);
        secureCreditCardField.onFinishInflate();
        ccvField = new SecureTextField(secureFormLayout.getContext());
        ccvField.setId(R.id.spreedly_ccv);
        ccvField.onFinishInflate();
        secureExpirationDate = new SecureExpirationDate(secureFormLayout.getContext());
        secureExpirationDate.setId(R.id.spreedly_cc_expiration_date);
        secureFormLayout.addView(secureCreditCardField);
        secureFormLayout.addView(ccvField);
        secureFormLayout.addView(secureExpirationDate);
    }

    private void submit() {
        secureFormLayout.setSpreedlyClient(key, secret, test);
        result = secureFormLayout.createCreditCardPaymentMethod();
        mListener.onEvent();
    }
}
