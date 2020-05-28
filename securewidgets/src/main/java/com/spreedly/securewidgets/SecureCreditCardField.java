package com.spreedly.securewidgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.models.enums.CreditCardType;

/**
 * TODO: document your custom view class.
 */
public class SecureCreditCardField extends SecureTextField {

    private CreditCardTransformationMethod ccTransformationMethod;
    private View.OnClickListener clickListener;
    private boolean visible = true;
    private CharSequence previous = "";
    public SecureCreditCardField(Context context, AttributeSet attrs) {
        super(context, attrs);
        ccTransformationMethod = new CreditCardTransformationMethod();
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Spreedly", "button clicked");
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
        };
    }
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        textLayout.setHint("Credit Card Number");
        setEndIcons();
        setStartIcon();
    }

    private void setEndIcons() {
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
                if (s.toString().equals(previous.toString())) {
                    return;
                }
                if (s.length() > 19)
                    previous = s;
                int icon = R.drawable.stp_card_unknown;
                CreditCardType type;
                if (getText().length < 16) {
                    type = getText().softDetect();
                } else {
                    type = getText().detectCardType();
                }
                switch (type) {
                    case mastercard:
                        icon = R.drawable.stp_card_mastercard;
                        textLayout.setError(null);
                        break;
                    case americanExpress:
                        icon = R.drawable.stp_card_amex;
                        textLayout.setError(null);
                        break;
                    case alelo:
                        textLayout.setError(null);
                        break;
                    case cabal:
                        textLayout.setError(null);
                        break;
                    case carnet:
                        textLayout.setError(null);
                        break;
                    case dankort:
                        textLayout.setError(null);
                        break;
                    case dinersClub:
                        icon = R.drawable.stp_card_diners;
                        textLayout.setError(null);
                        break;
                    case discover:
                        icon = R.drawable.stp_card_discover;
                        textLayout.setError(null);
                        break;
                    case elo:
                        textLayout.setError(null);
                        break;
                    case jcb:
                        icon = R.drawable.stp_card_jcb;
                        textLayout.setError(null);
                        break;
                    case maestro:
                        textLayout.setError(null);
                        break;
                    case naranja:
                        textLayout.setError(null);
                        break;
                    case sodexo:
                        textLayout.setError(null);
                        break;
                    case vr:
                        textLayout.setError(null);
                        break;
                    case unknown:
                        textLayout.setError(null);
                        break;
                    case error:
                        if (getText().length < 16) {
                            icon = R.drawable.stp_card_unknown;
                        } else {
                            icon = R.drawable.stp_card_error;
                            textLayout.setError("Invalid Card Number");
                        }
                        break;
                    case visa:
                        icon = R.drawable.stp_card_visa_template;
                        textLayout.setError(null);
                        break;
                    default:
                        break;
                }

                textLayout.setStartIconDrawable(icon);
                if (getText().length > 15 && type != CreditCardType.error) {
                    textLayout.setEndIconDrawable(R.drawable.ic_visible);
                    visible = false;
                    editText.setTransformationMethod(ccTransformationMethod);
                    textLayout.setEndIconOnClickListener(clickListener);
                } else if (visible == true) {
                    textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
                    editText.setTransformationMethod(null);
                    textLayout.setEndIconOnClickListener(clickListener);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
