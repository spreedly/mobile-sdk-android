package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

public class CreditCardPaymentView extends PaymentView {
    SecureCreditCardField secureCreditCardField;
    SecureTextField ccvField;
    SecureExpirationDate secureExpirationDate;

    public CreditCardPaymentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CreditCardPaymentView(Context context, boolean showBilling, boolean showShipping, String submitButtonText) {
        super(context);
        this.showBilling = showBilling;
        this.showShipping = showShipping;
        this.submitButtonText = submitButtonText;
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
        if (showBilling)
            setBilling();
        if (showBilling && showShipping)
            setSameAddress();
        if (showShipping) {
            setShipping();
        }
        if (includeBackButton) {
            //TODO: update back button
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
