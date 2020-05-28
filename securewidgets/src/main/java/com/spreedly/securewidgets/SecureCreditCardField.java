package com.spreedly.securewidgets;

import android.content.Context;
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
    }

    public void setEndIcons() {
        textLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        textLayout.setEndIconDrawable(R.drawable.ic_visible);
        final boolean[] visibile = {false};
        final Context context = this.getContext();
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Spreedly", "button clicked");
                if (visibile[0]) {
                    textLayout.setEndIconDrawable(R.drawable.ic_visible);
                    visibile[0] = false;
                    editText.setTextAppearance(context, R.style.sample_style);
                } else {
                    textLayout.setEndIconDrawable(R.drawable.ic_visibilityoff);
                    visibile[0] = true;

                }
            }
        };
        textLayout.setEndIconOnClickListener(v);

    }

    public String hideNumber(String text) {
        int length = text.length();
        String lastFour = text.substring(length - 4, length);
        String xs = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".substring(0, length - 4);
        return xs + lastFour;
    }


}
