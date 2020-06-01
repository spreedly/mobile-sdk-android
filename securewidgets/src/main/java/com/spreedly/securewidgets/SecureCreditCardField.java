package com.spreedly.securewidgets;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.enums.CardBrand;

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
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
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
        editText.setSelection(editText.getText().length());
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
        editText.setSelection(editText.getText().length());

    }

    private void setStartIcon() {
        textLayout.setStartIconDrawable(R.drawable.spr_card_unknown);
        textLayout.setStartIconTintList(null);
        editText.addTextChangedListener(new TextWatcher() {
            boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int p = editText.getSelectionEnd();
                String text = editText.getText().toString();
                text = text.replace(" ", "");
                CardBrand brand;
                SpreedlySecureOpaqueString secureString = getText();
                if (text.equals(previous)) {
                    return;
                }
                previous = text;
                if (text.length() < 16) {
                    brand = secureString.softDetect();
                } else if (text.length() < 20) {
                    brand = secureString.detectCardType();
                } else {
                    brand = CardBrand.error;
                }
                if (brand == CardBrand.error) {
                    textLayout.setError("Invalid credit card number");
                } else {
                    textLayout.setError(null);
                }
                if (text.length() > 15 && text.length() < 20 && brand != CardBrand.error) {
                    visible = false;
                    setEndIcons();
                } else {
                    visible = true;
                    setEndIcons();
                }
                setIcon(brand);
                if (lock) {
                    return;
                }
                lock = true;
                String newText = "";
                for (int i = 0; i < text.length(); i++) {
                    if (i != 0 && (i % 4 == 0) && i < 16) {
                        newText += " ";
                    }
                    newText += text.charAt(i);
                }
                s.replace(0, s.length(), newText);
                lock = false;
            }
        });
    }

    void setIcon(CardBrand type) {
        switch (type) {
            case visa:
                textLayout.setStartIconDrawable(R.drawable.spr_card_visa);
                break;
            case mastercard:
                textLayout.setStartIconDrawable(R.drawable.spr_card_mastercard);
                break;
            case americanExpress:
                textLayout.setStartIconDrawable(R.drawable.spr_card_amex);
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
                textLayout.setStartIconDrawable(R.drawable.spr_card_diners);
                break;
            case discover:
                textLayout.setStartIconDrawable(R.drawable.spr_card_discover);
                break;
            case elo:
                break;
            case jcb:
                textLayout.setStartIconDrawable(R.drawable.spr_card_jcb);
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
                textLayout.setStartIconDrawable(R.drawable.spr_card_unknown);
                break;
            case error:
                textLayout.setStartIconDrawable(R.drawable.spr_card_error);
                break;
        }
    }
}
