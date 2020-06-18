package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureTextField;

/**
 * TODO: document your custom view class.
 */
public class ExpressCreditCardView extends LinearLayout {
    TextInputLayout fullNameWrapper;
    TextInputEditText fullNameContent;
    SecureCreditCardField secureCreditCardField;
    SecureTextField ccvField;
    SecureExpirationDate secureExpirationDate;
    Button submitButton;
    TextView sample;

    public ExpressCreditCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        fullNameWrapper = new TextInputLayout(this.getContext());
        fullNameContent = new TextInputEditText(this.getContext());
        fullNameWrapper.setHint("Full Name");
        fullNameWrapper.addView(fullNameContent);
        secureCreditCardField = new SecureCreditCardField(this.getContext());
        secureCreditCardField.setId(R.id.spreedly_credit_card_number);
        secureCreditCardField.onFinishInflate();
        ccvField = new SecureTextField(this.getContext());
        ccvField.setId(R.id.spreedly_ccv);
        ccvField.onFinishInflate();
        secureExpirationDate = new SecureExpirationDate(this.getContext());
        secureExpirationDate.setId(R.id.spreedly_cc_expiration_date);
        submitButton = new Button(this.getContext());
        submitButton.setText("Submit");
        this.addView(fullNameWrapper);
        this.addView(secureCreditCardField);
        this.addView(ccvField);
        this.addView(secureExpirationDate);
        this.addView(submitButton);

    }
}
