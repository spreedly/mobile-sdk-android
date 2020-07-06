package com.spreedly.express;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
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

    String submitButtonText;
    boolean includeBackButton;
    @Nullable
    public OnCustomEventListener backButtonListener;

    SecureFormLayout secureFormLayout;
    Button submitButton;
    MaterialTextView paymentLabel;
    TextInputLayout fullNameWrapper;
    TextInputEditText fullNameContent;

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }
    public PaymentView(@NonNull Context context) {
        super(context);
    }

    public PaymentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        final float pixelDensity = getResources().getDisplayMetrics().density;
        int padding = (int) (16 * pixelDensity);
        int labelPadding = (int) (12 * pixelDensity);
        label = new MaterialTextView(this.getContext());
        label.setText(labelContent);
        label.setTextAppearance(this.getContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        label.setPadding(0, labelPadding, 0, 0);
        secureFormLayout.addView(label);
    }


    public void setClientInfo(@NonNull String key, @NonNull String secret, @NonNull boolean test) {
        this.key = key;
        this.test = test;
        this.secret = secret;
    }

    public void setCustomEventListener(@NonNull OnCustomEventListener eventListener) {
        mListener = eventListener;
    }

    public void setBackButtonListener(@NonNull OnCustomEventListener eventListener) {
        backButtonListener = eventListener;
    }
}
