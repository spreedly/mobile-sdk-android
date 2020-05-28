package com.spreedly.securewidgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

/**
 * TODO: document your custom view class.
 */
public class SecureCreditCardField extends SecureTextField {

    public SecureCreditCardField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    String previousValue;
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        textLayout.setHint("Credit Card Number");
        setEndIcons();
        setStartIcon();
    }

    public void setEndIcons() {
        textLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        textLayout.setEndIconDrawable(R.drawable.ic_visible);
        editText.setTransformationMethod(new CreditCardTransformationMethod());
        final boolean[] visibile = {false};
        final Context context = this.getContext();
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Spreedly", "button clicked");
                if (visibile[0]) {
                    textLayout.setEndIconDrawable(R.drawable.ic_visible);
                    visibile[0] = false;
                    editText.setTransformationMethod(new CreditCardTransformationMethod());
                } else {
                    textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
                    editText.setTransformationMethod(null);
                    visibile[0] = true;

                }
            }
        };
        textLayout.setEndIconOnClickListener(v);

    }

    public void setStartIcon() {
        textLayout.setStartIconDrawable(R.drawable.stp_card_unknown);
        textLayout.setStartIconTintList(null);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int icon = R.drawable.stp_card_unknown;
                switch (getText().detectCardType()) {
                    case mastercard:
                        icon = R.drawable.stp_card_mastercard;
                        break;
                    case americanExpress:
                        icon = R.drawable.stp_card_amex;
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
                        icon = R.drawable.stp_card_diners;
                        break;
                    case discover:
                        icon = R.drawable.stp_card_discover;
                        break;
                    case elo:
                        break;
                    case jcb:
                        icon = R.drawable.stp_card_jcb;
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
                        break;
                    case error:
                        if (getText().length == 0) {
                            icon = R.drawable.stp_card_unknown;
                        } else {
                            icon = R.drawable.stp_card_error;
                        }
                        break;
                    case visa:
                        icon = R.drawable.stp_card_visa_template;
                        break;
                    default:
                        break;
                }
                textLayout.setStartIconDrawable(icon);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String hideNumber(String text) {
        int length = text.length();
        String lastFour = text.substring(length - 4, length);
        String xs = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".substring(0, length - 4);
        return xs + lastFour;
    }


}
