package com.spreedly.securewidgets;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

/**
 * TODO: document your custom view class.
 */
public class SecureCreditCardField extends SecureTextField {

    public SecureCreditCardField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        textLayout.setHint("Credit Card Number");
        setEndIcons();
    }

    public void setEndIcons() {
        textLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        textLayout.setStartIconDrawable(R.drawable.ic_visible);
        View.OnClickListener v = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editText.getInputType() == InputType.TYPE_CLASS_NUMBER) {
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
        };
        textLayout.setEndIconOnClickListener(v);

    }



}
