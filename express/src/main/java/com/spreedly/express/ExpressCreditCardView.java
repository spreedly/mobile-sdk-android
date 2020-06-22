package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

/**
 * TODO: document your custom view class.
 */
public class ExpressCreditCardView extends ScrollView {
    SecureFormLayout layoutWrapper;
    TextInputLayout fullNameWrapper;
    TextInputEditText fullNameContent;
    SecureCreditCardField secureCreditCardField;
    SecureTextField ccvField;
    SecureExpirationDate secureExpirationDate;
    Button submitButton;
    AddressFieldView billingAddress;
    AddressFieldView shippingAddress;
    CheckBox sameAddress;

    public ExpressCreditCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        layoutWrapper = new SecureFormLayout(this.getContext());
        layoutWrapper.setOrientation(LinearLayout.VERTICAL);
        fullNameWrapper = new TextInputLayout(layoutWrapper.getContext());
        fullNameContent = new TextInputEditText(layoutWrapper.getContext());
        fullNameWrapper.setHint("Full Name");
        fullNameWrapper.addView(fullNameContent);

        secureCreditCardField = new SecureCreditCardField(layoutWrapper.getContext());
        secureCreditCardField.setId(R.id.spreedly_credit_card_number);
        secureCreditCardField.onFinishInflate();

        ccvField = new SecureTextField(layoutWrapper.getContext());
        ccvField.setId(R.id.spreedly_ccv);
        ccvField.onFinishInflate();

        secureExpirationDate = new SecureExpirationDate(layoutWrapper.getContext());
        secureExpirationDate.setId(R.id.spreedly_cc_expiration_date);

        billingAddress = new AddressFieldView(layoutWrapper.getContext(), AddressFieldView.AddressType.BILLING);
        billingAddress.onFinishInflate();

        shippingAddress = new AddressFieldView(layoutWrapper.getContext(), AddressFieldView.AddressType.SHIPPING);
        shippingAddress.onFinishInflate();
        sameAddress = new CheckBox(layoutWrapper.getContext());

        sameAddress.setText("Use billing address for shipping");
        sameAddress.setId(R.id.same_address);
        sameAddress.setOnClickListener(b -> sameAddressClickListener());

        submitButton = new Button(layoutWrapper.getContext());
        submitButton.setText("Submit");
        layoutWrapper.addView(fullNameWrapper);
        layoutWrapper.addView(secureCreditCardField);
        layoutWrapper.addView(ccvField);
        layoutWrapper.addView(secureExpirationDate);
        layoutWrapper.addView(billingAddress);
        layoutWrapper.addView(sameAddress);
        layoutWrapper.addView(shippingAddress);
        layoutWrapper.addView(submitButton);
        layoutWrapper.onFinishInflate();
        this.addView(layoutWrapper);

    }

    void sameAddressClickListener() {
        if (sameAddress.isChecked()) {
            //hide shipping address
            layoutWrapper.removeView(shippingAddress);
        } else {
            //show shipping address
            layoutWrapper.addView(shippingAddress);
        }
    }
}
