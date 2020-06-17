package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

/**
 * TODO: document your custom view class.
 */
public class ExpressCreditCardView extends FrameLayout {
    SecureFormLayout secureFormLayout;
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
        secureFormLayout = new SecureFormLayout(this.getContext());
        secureCreditCardField = new SecureCreditCardField(this.getContext(), null);
        ccvField = new SecureTextField(this.getContext(), null);
        ccvField.setId(R.id.spreedly_ccv);
        secureExpirationDate = new SecureExpirationDate(this.getContext(), null);
        submitButton = new Button(this.getContext());

        secureFormLayout.addView(secureCreditCardField);
        secureFormLayout.addView(ccvField);
        secureFormLayout.addView(secureExpirationDate);

        this.addView(secureFormLayout);
    }
}
