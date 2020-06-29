package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.models.enums.BankAccountHolderType;
import com.spreedly.client.models.enums.BankAccountType;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

public class BankAccountPaymentView extends PaymentView {

    TextInputLayout routingNumberWrapper;
    SecureTextField accountNumberField;
    TextInputEditText routingNumberContent;
    Spinner accountType;
    Spinner holderType;

    public BankAccountPaymentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BankAccountPaymentView(Context context, boolean showBilling, boolean showShipping, String submitButtonText) {
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
        setBankAccount();
        if (showBilling)
            setBilling();
        if (showBilling && showShipping)
            setSameAddress();
        if (showShipping) {
            setShipping();
        }
        if (includeBackButton) {
            Button backButton = new Button(this.getContext());
            backButton.setText(R.string.back);
            backButton.setOnClickListener((l) -> {
                //update back button
            });
            secureFormLayout.addView(backButton);
        }
        submitButton = new Button(secureFormLayout.getContext());
        submitButton.setText(submitButtonText);
        submitButton.setOnClickListener(b -> submit());
        secureFormLayout.addView(submitButton);
        secureFormLayout.onFinishInflate();
        this.addView(secureFormLayout);
    }

    private void setBankAccount() {
        accountNumberField = new SecureTextField(secureFormLayout.getContext());
        accountNumberField.setId(R.id.spreedly_ba_account_number);
        accountNumberField.onFinishInflate();
        routingNumberWrapper = new TextInputLayout(secureFormLayout.getContext());
        routingNumberWrapper.setId(R.id.spreedly_ba_routing_number);
        routingNumberWrapper.setHint("Routing Number");
        routingNumberContent = new TextInputEditText(secureFormLayout.getContext());
        routingNumberWrapper.addView(routingNumberContent);
        accountType = new Spinner(secureFormLayout.getContext());
        accountType.setAdapter(new ArrayAdapter<BankAccountType>(secureFormLayout.getContext(), android.R.layout.simple_list_item_1, BankAccountType.values()));
        accountType.setId(R.id.spreedly_ba_account_type);
        holderType = new Spinner(secureFormLayout.getContext());
        holderType.setAdapter(new ArrayAdapter<BankAccountHolderType>(secureFormLayout.getContext(), android.R.layout.simple_spinner_dropdown_item, BankAccountHolderType.values()));
        holderType.setId(R.id.spreedly_ba_account_holder_type);
        secureFormLayout.addView(accountNumberField);
        secureFormLayout.addView(routingNumberWrapper);
        secureFormLayout.addView(accountType);
        secureFormLayout.addView(holderType);
    }

    private void submit() {
        secureFormLayout.setSpreedlyClient(key, secret, test);
        result = secureFormLayout.createBankAccountPaymentMethod();
        mListener.onEvent();
    }
}
