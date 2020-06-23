package com.spreedly.express;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

/**
 * TODO: document your custom view class.
 */
public class ExpressView extends ScrollView {
    int paymentType;
    boolean showBilling;
    boolean showShipping;

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
    TextView paymentLabel;
    TextView billingLabel;
    TextView shippingLabel;

    public ExpressView(@NonNull Context context) {
        super(context);
        setPaymentType(0);
        setAddressUse(true, true);
    }

    public ExpressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
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
        submitButton.setOnClickListener(b -> submit());

        paymentLabel = new TextView(this.getContext());
        paymentLabel.setText("Payment Information");
        paymentLabel.setTextAppearance(this.getContext(), android.R.style.TextAppearance_Material_Subhead);
        paymentLabel.setPadding(0, 12, 0, 0);

        billingLabel = new MaterialTextView(this.getContext());
        billingLabel.setText("Billing Address");
        billingLabel.setTextAppearance(this.getContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        billingLabel.setPadding(0, 12, 0, 0);

        shippingLabel = new MaterialTextView(this.getContext());
        shippingLabel.setText("Shipping Address");
        shippingLabel.setTextAppearance(this.getContext(), android.R.style.TextAppearance_Holo_Large);
        shippingLabel.setPadding(0, 12, 0, 0);

        layoutWrapper.addView(paymentLabel);
        layoutWrapper.addView(fullNameWrapper);
        layoutWrapper.addView(secureCreditCardField);
        layoutWrapper.addView(ccvField);
        layoutWrapper.addView(secureExpirationDate);
        if (showBilling == true) {
            layoutWrapper.addView(billingLabel);
            layoutWrapper.addView(billingAddress);
        }
        if (showBilling && showShipping) {
            layoutWrapper.addView(sameAddress);
        }
        if (showShipping) {
            layoutWrapper.addView(shippingLabel);
            layoutWrapper.addView(shippingAddress);
        }
        layoutWrapper.addView(submitButton);
        layoutWrapper.onFinishInflate();
        this.addView(layoutWrapper);

    }

    private void submit() {
    }

    void sameAddressClickListener() {
        if (sameAddress.isChecked()) {
            //hide shipping address
            shippingAddress.setVisibility(View.GONE);
            shippingLabel.setVisibility(View.GONE);
        } else {
            //show shipping address
            shippingLabel.setVisibility(View.VISIBLE);
            shippingAddress.setVisibility(View.VISIBLE);
        }
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpressView,
                0, 0);
        int paymentType = 0;
        paymentType = a.getInteger(R.styleable.ExpressView_paymentType, 0);
        setPaymentType(paymentType);
        boolean shipping = true;
        boolean billing = true;
        billing = a.getBoolean(R.styleable.ExpressView_includeBillingAddress, true);
        shipping = a.getBoolean(R.styleable.ExpressView_includeShippingAddress, true);
        setAddressUse(billing, shipping);
    }

    public void setPaymentType(int value) {
        paymentType = value;
    }

    public void setAddressUse(boolean billing, boolean shipping) {
        showBilling = billing;
        showShipping = shipping;
    }
}
