package com.spreedly.express;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.securewidgets.SecureFormLayout;

import io.reactivex.rxjava3.core.Single;

public class PaymentView extends ScrollView {
    @Nullable
    public Single<TransactionResult<PaymentMethodResult>> result;
    @Nullable
    public OnCustomEventListener mListener;
    Boolean test;
    @Nullable
    String key;
    @Nullable
    String secret;

    boolean showBilling;
    boolean showShipping;
    String submitButtonText;
    boolean includeBackButton;

    SecureFormLayout secureFormLayout;
    Button submitButton;
    AddressFieldView billingAddress;
    AddressFieldView shippingAddress;
    CheckBox sameAddress;
    MaterialTextView paymentLabel;
    MaterialTextView billingLabel;
    MaterialTextView shippingLabel;
    TextInputLayout fullNameWrapper;
    TextInputEditText fullNameContent;

    public PaymentView(@NonNull Context context) {
        super(context);
        setAddressUse(true, true);
    }

    public PaymentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    void setBilling() {
        setLabel("Billing Address", billingLabel);
        billingAddress = new AddressFieldView(secureFormLayout.getContext(), AddressFieldView.AddressType.BILLING);
        billingAddress.onFinishInflate();
        secureFormLayout.addView(billingAddress);
    }

    void setShipping() {
        setLabel("Shipping Address", shippingLabel);
        shippingAddress = new AddressFieldView(secureFormLayout.getContext(), AddressFieldView.AddressType.SHIPPING);
        shippingAddress.onFinishInflate();
        secureFormLayout.addView(shippingAddress);
    }

    void setSameAddress() {
        sameAddress = new CheckBox(secureFormLayout.getContext());
        sameAddress.setText(R.string.use_billing_toggle);
        sameAddress.setId(R.id.same_address);
        sameAddress.setOnClickListener(b -> sameAddressClickListener());
        secureFormLayout.addView(sameAddress);
    }

    void setFullName() {
        fullNameWrapper = new TextInputLayout(secureFormLayout.getContext());
        fullNameContent = new TextInputEditText(secureFormLayout.getContext());
        fullNameWrapper.setHint("Full Name");
        fullNameWrapper.addView(fullNameContent);
        fullNameWrapper.setId(R.id.spreedly_full_name);
        secureFormLayout.addView(fullNameWrapper);
    }

    void setLabel(String labelContent, MaterialTextView label) {
        label = new MaterialTextView(this.getContext());
        label.setText(labelContent);
        label.setTextAppearance(this.getContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        label.setPadding(0, 12, 0, 0);
        secureFormLayout.addView(label);
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
        boolean shipping = true;
        boolean billing = true;
        billing = a.getBoolean(R.styleable.ExpressView_includeBillingAddress, true);
        shipping = a.getBoolean(R.styleable.ExpressView_includeShippingAddress, true);
        setAddressUse(billing, shipping);
    }

    public void setAddressUse(boolean billing, boolean shipping) {
        showBilling = billing;
        showShipping = shipping;
    }

    public void setClientInfo(@NonNull String key, @NonNull String secret, @NonNull boolean test) {
        this.key = key;
        this.test = test;
        this.secret = secret;
    }

    public void setCustomEventListener(@NonNull OnCustomEventListener eventListener) {
        mListener = eventListener;
    }
}
