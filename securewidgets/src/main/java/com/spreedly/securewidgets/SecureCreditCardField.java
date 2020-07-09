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
    private boolean hasError = false;
    String previous = "";
    @NonNull
    private CreditCardTransformationMethod ccTransformationMethod;

    public SecureCreditCardField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ccTransformationMethod = new CreditCardTransformationMethod();
    }

    public SecureCreditCardField(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        setIcons();
        clickListener = v -> {
            Log.i("Spreedly", "button clicked");
            int s = editText.getSelectionEnd();
            if (visible) {
                textLayout.setEndIconDrawable(R.drawable.ic_visible);
                editText.setTransformationMethod(ccTransformationMethod);
                visible = false;
            } else {
                textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
                editText.setTransformationMethod(null);
                visible = true;
            }
            editText.setSelection(s);
        };
        textLayout.setHint(getContext().getString(R.string.hint_credit_card_number));
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        setEndIcons();
        setStartIcon();

    }

    public void setError(@Nullable String error) {
        textLayout.setError(error);
    }

    void setEndIcons() {
        int s = editText.getSelectionEnd();
        textLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        if (visible) {
            textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
            editText.setTransformationMethod(null);
        } else {
            editText.setTransformationMethod(ccTransformationMethod);
            textLayout.setEndIconDrawable(R.drawable.ic_visible);
        }
        textLayout.setEndIconOnClickListener(clickListener);
        editText.setSelection(s);
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
                    textLayout.setError(getContext().getString(R.string.error_bad_card_number));
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
                textLayout.setStartIconDrawable(brand.getIcon());


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

    public void setIcons() {
        CardBrand.visa.setIcon(R.drawable.spr_card_visa);
        CardBrand.americanExpress.setIcon(R.drawable.spr_card_amex);
        CardBrand.mastercard.setIcon(R.drawable.spr_card_mastercard);
        CardBrand.dinersClub.setIcon(R.drawable.spr_card_diners);
        CardBrand.discover.setIcon(R.drawable.spr_card_discover);
        CardBrand.jcb.setIcon(R.drawable.spr_card_jcb);
        CardBrand.unknown.setIcon(R.drawable.spr_card_unknown);
        CardBrand.error.setIcon(R.drawable.spr_card_error);
        CardBrand.alelo.setIcon(R.drawable.spr_card_unknown);
        CardBrand.elo.setIcon(R.drawable.spr_card_unknown);
        CardBrand.cabal.setIcon(R.drawable.spr_card_unknown);
        CardBrand.carnet.setIcon(R.drawable.spr_card_unknown);
        CardBrand.dankort.setIcon(R.drawable.spr_card_unknown);
        CardBrand.maestro.setIcon(R.drawable.spr_card_unknown);
        CardBrand.naranja.setIcon(R.drawable.spr_card_unknown);
        CardBrand.sodexo.setIcon(R.drawable.spr_card_unknown);
        CardBrand.vr.setIcon(R.drawable.spr_card_unknown);
    }
}
