package com.spreedly.securewidgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.enums.CreditCardType;

import io.reactivex.rxjava3.annotations.Nullable;

/**
 * TODO: document your custom view class.
 */
public class SecureCreditCardField extends SecureTextField {

    boolean visible = true;
    private View.OnClickListener clickListener;
    String previous = "";
    @NonNull
    private CreditCardTransformationMethod ccTransformationMethod;

    public SecureCreditCardField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ccTransformationMethod = new CreditCardTransformationMethod();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        clickListener = v -> {
            Log.i("Spreedly", "button clicked");
            setTransformation();
        };
        textLayout.setHint("Credit Card Number");
        setEndIcons();
        setStartIcon();
    }

    private void setTransformation() {
        if (visible) {
            textLayout.setEndIconDrawable(R.drawable.ic_visible);
            editText.setTransformationMethod(ccTransformationMethod);
            visible = false;
        } else {
            textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
            editText.setTransformationMethod(null);
            visible = true;
        }
    }

    void setEndIcons() {
        textLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        if (visible) {
            textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
            editText.setTransformationMethod(null);
        } else {
            editText.setTransformationMethod(ccTransformationMethod);
            textLayout.setEndIconDrawable(R.drawable.ic_visible);
        }
        textLayout.setEndIconOnClickListener(clickListener);

    }

    private void setStartIcon() {
        textLayout.setStartIconDrawable(R.drawable.stp_card_unknown);
        textLayout.setStartIconTintList(null);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editText.getText().toString();
                CreditCardType type;
                SpreedlySecureOpaqueString secureString = getText();
                if (text.equals(previous)) {
                    return;
                }
                previous = text;
                if (text.length() < 16) {
                    type = secureString.softDetect();
                } else if (text.length() < 20) {
                    type = secureString.detectCardType();
                } else {
                    type = CreditCardType.error;
                }
                if (type == CreditCardType.error) {
                    textLayout.setError("Invalid credit card number");
                } else {
                    textLayout.setError(null);
                }
                if (text.length() > 15 && text.length() < 20 && type != CreditCardType.error) {
                    visible = false;
                    setEndIcons();
                } else {
                    visible = true;
                    setEndIcons();
                }
                setIcon(type);
            }
        });
    }

    void setIcon(CreditCardType type) {
        switch (type) {
            case visa:
                textLayout.setStartIconDrawable(R.drawable.stp_card_visa);
                break;
            case mastercard:
                textLayout.setStartIconDrawable(R.drawable.stp_card_mastercard);
                break;
            case americanExpress:
                textLayout.setStartIconDrawable(R.drawable.stp_card_amex);
                break;
            case alelo:
                break;
            case cabal:
                break;
            case carnet:
                break;
            case dankort:
                break;
            case dinersClub:
                textLayout.setStartIconDrawable(R.drawable.stp_card_diners);
                break;
            case discover:
                textLayout.setStartIconDrawable(R.drawable.stp_card_discover);
                break;
            case elo:
                break;
            case jcb:
                textLayout.setStartIconDrawable(R.drawable.stp_card_jcb);
                break;
            case maestro:
                break;
            case naranja:
                break;
            case sodexo:
                break;
            case vr:
                break;
            case unknown:
                textLayout.setStartIconDrawable(R.drawable.stp_card_unknown);
                break;
            case error:
                textLayout.setStartIconDrawable(R.drawable.stp_card_error);
                break;
        }
    }
}
